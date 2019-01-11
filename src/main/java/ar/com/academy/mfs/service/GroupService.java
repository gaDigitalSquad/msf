package ar.com.academy.mfs.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.repository.GroupRepository;
import ar.com.academy.mfs.repository.ZoneRepository;

@Service("groupService")
public class GroupService {
	
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	ZoneRepository zoneRepository;
	
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
	
	public Group findGroup(int supervisor_id) {
		return groupRepository.findUserGroup(supervisor_id);
	}

//	public List<Group> getGroupsByZone(int zone_id) {
//		return groupRepository.findByZone(zone_id);
//	}
	
}
