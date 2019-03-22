package ar.com.academy.mfs.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.State;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.repository.GroupRepository;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.StateRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.UserStateRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.response.UserGroupResponse;

@Service("groupService")
public class GroupService {
	
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	ZoneRepository zoneRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserStateRepository userStateRepository;
	@Autowired
	StateRepository stateRepository;
	
	public Group createRegisterForGroup(Group inputGroup) {
		Group group = groupRepository.save(inputGroup);
		return group;
	}

//	public List<Group> getSupervisedBySupervisorId(int supervisor_id) {
//		return groupRepository.findBySupervisor_id(supervisor_id);
//	}

	public Group getGroupByGroupNumber(int group_number) {
		return groupRepository.findByGroupNumber(group_number);
	}
	
	/**
	 * Dado el supervisor_id se obtiene el grupo del que es líder
	 * @param supervisor_id
	 * @return
	 */
	public Group findGroup(int supervisor_id) {
		return groupRepository.findUserGroup(supervisor_id);
	}

	public List<Group> getGroupsByZone(int zone_id) {
		return groupRepository.findByZone(zone_id);
	}

	public List<Group> getGroupsByZoneAndTurn(int zone_id, String turn) {
		return groupRepository.findByZoneAndTurn(zone_id, turn);
	}

	public int getGroupLeader(int group_number) {
		return groupRepository.findLeaderByGroupNumber(group_number);
	}
	public List<UserGroupResponse> getUserByGroup (int group_number) 
	{
		List<UserGroupResponse> listUserGroupResponse = new ArrayList<UserGroupResponse>();
		
		groupRepository.findAllByGroup_number(group_number).stream().filter(p->p.isActive()).forEach(group->
		{
			
			userRepository.findSensFromGroup(group.getGroup_number()).stream().forEach(m->
			{
				UserGroupResponse ugr = new UserGroupResponse();
				ugr.setDocumentNumber(String.valueOf(m.getDocumentNumber()));
				ugr.setFirstName(m.getFirstname());
				ugr.setLastname(m.getLastname());
				ugr.setFromDate(m.getFrom_date());
				Role role = roleRepository.findById(m.getRole_id()).get();
				ugr.setRole(role.getRoleName());
				UserState us = userStateRepository.findByUserStateId(m.getUserStateId());
				State state = stateRepository.findByStateId(Long.valueOf(us.getStateId()));
				ugr.setState(state.getDescription());
				if(!listUserGroupResponse.stream().anyMatch(p -> p.getDocumentNumber().
						equals(ugr.getDocumentNumber())))
					listUserGroupResponse.add(ugr);

			});	
		});
		return listUserGroupResponse;
		
	}

	public void updateLeader(Group groupToUpdate, int newLeader) {
		List<Group> groups = groupRepository.getGroupsByLider(groupToUpdate.getSupervisor());
		int groupNumber = groups.get(0).getGroup_number();
		for (Group g: groups) {
			g.setSupervisor(newLeader);
			groupRepository.save(g);
		}
		/* Se establece el group_number en la tabla user */
		User user = userRepository.findById(newLeader).get();
		user.setGroup_number(groupNumber);
	}

	public void addSensToGroup(int user_id, Group groupToUpdate) {
		Group group = groupRepository.getSens(user_id);
		if (group == null) { // agregamos al nuevo sensibilizador
			Group g = new Group(groupToUpdate.getZone_id(),
								groupToUpdate.getSupervisor(),
								user_id,
								groupToUpdate.getGroup_number(),
								groupToUpdate.getTurn());
			groupRepository.save(g);
		} else return;
	}

	public void deleteSensFromGroup(int user_id) {
		Group registro = groupRepository.getSensByGroupNumber(user_id);
		if (registro != null) {
			registro.setActive(false);
			registro.setTo_date(new Date());
			groupRepository.save(registro);
		} else return;
	}	
}
