package ar.com.academy.mfs.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Area;
import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.service.GroupService;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.AreaRepository;
import ar.com.academy.mfs.repository.GroupRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.GroupRequest;

@RestController
public class GroupController {
	@Autowired
	GroupService groupService;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ZoneRepository zoneRepository;
	@Autowired
	AreaRepository areaRepository;
	
	@PostMapping("/group")
	@ResponseBody ResponseEntity<?> postGroup(@Valid @RequestBody GroupRequest groupRequest) {
		User supervisor = userRepository.findById(groupRequest.getLeader()).get();
		if (supervisor == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid supervisor");
		
		Zone zone = zoneRepository.findById(groupRequest.getZone()).get();
		if(zone == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid zone");
		
		Area area = areaRepository.findById(groupRequest.getArea()).get();
		if(area == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid area");
		
		Integer numberGroup = groupRepository.findLastGroupNumber();
		if (numberGroup == null) {
			numberGroup = 1;
		}
		else {
			numberGroup++;
		}
		
		List<Integer> sens = groupRequest.getSens();
		for (int i = 0; i < sens.size(); i++) {
			User sensibilizador = userRepository.findById(sens.get(i)).get();
			Group g = new Group(zone.getZoneId(), supervisor.getUser_id(), sensibilizador.getUser_id(), numberGroup);
			sensibilizador.setGroup_number(g.getGroup_number());
			supervisor.setGroup_number(g.getGroup_number());
			groupRepository.save(g);
		}
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}
	
	@GetMapping("/group/{group_number}")
	public Group getGroupByGroupNumber(@PathVariable int group_number) {
		return groupService.getGroupByGroupNumber(group_number);
	}
//	
//	@GetMapping("/groups/{zone_id}")
//	public List<Group> getGroupsByZone(@PathVariable int zone_id) {
//		 return groupService.getGroupsByZone(zone_id);
//	}
	
//	@GetMapping("/group/{supervisor_id}")
//	public List<Group> getSupervisedBySupervisorId(@PathVariable long supervisor_id) {
//		return groupService.getSupervisedBySupervisorId(supervisor_id);
//	}
}
