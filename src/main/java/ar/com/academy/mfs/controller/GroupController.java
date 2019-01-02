package ar.com.academy.mfs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.service.GroupService;

@RestController
public class GroupController {
	@Autowired
	GroupService groupService;
	
	@PostMapping("/group")
	public Group createRegisterForGroup(@RequestBody Group inputGroup) {
		Group group = groupService.createRegisterForGroup(inputGroup);
		return group;
	}
	
	@GetMapping("/group/{supervisor_id}")
	public List<Group> getSupervisedBySupervisorId(@PathVariable long supervisor_id) {
		return groupService.getSupervisedBySupervisorId(supervisor_id);
	}
}
