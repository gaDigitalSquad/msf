package ar.com.academy.mfs.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.repository.GroupRepository;

@Service("groupService")
public class GroupService {
	
	@Autowired
	GroupRepository groupRepository;
	
	public Group createRegisterForGroup(Group inputGroup) {
		Group group = groupRepository.save(inputGroup);
		return group;
	}

	public List<Group> getSupervisedBySupervisorId(long supervisor_id) {
		return groupRepository.findBySupervisor(supervisor_id);
	}

	
}
