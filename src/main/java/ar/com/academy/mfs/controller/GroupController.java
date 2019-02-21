package ar.com.academy.mfs.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/* Crear un grupo */
	@PostMapping("/group")
	@ResponseBody ResponseEntity<?> postGroup(@Valid @RequestBody GroupRequest groupRequest) {
		User supervisor = userRepository.findById(groupRequest.getLeader()).get();
		if (supervisor == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid supervisor");
		
		Zone zone = zoneRepository.findById(groupRequest.getZone()).get();
		if(zone == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid zone");
		
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
			Group g = new Group(zone.getZoneId(),
								supervisor.getUser_id(),
								sensibilizador.getUser_id(),
								numberGroup,
								groupRequest.getturn());
			sensibilizador.setGroup_number(g.getGroup_number());
			supervisor.setGroup_number(g.getGroup_number());
			groupRepository.save(g);
		}
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}
	
	/* Dado el número de grupo, se obtiene el grupo */
	@GetMapping("/group/{group_number}")
	public Group getGroupByGroupNumber(@PathVariable int group_number) {
		return groupService.getGroupByGroupNumber(group_number);
	}
	
	/* Dada la zona, se obtienen todos los grupos */
	@GetMapping("/group-by-zone/{zone_id}")
	public List<Group> getGroupsByZone(@PathVariable int zone_id) {
		 return groupService.getGroupsByZone(zone_id);
	}
	
	/* Dada la zona y turno, se obtiene el grupo */
	@GetMapping("/get-group/{zone_id}/{turn}")
	public List<Group> getGroupsByZoneAndTurn(@PathVariable String turn, @PathVariable int zone_id) {
		 return groupService.getGroupsByZoneAndTurn(zone_id, turn);
	}
	
	/* Dado el número de grupo, se obtiene el líder */
	@GetMapping("get-leader/{group_number}")
	public User getGroupLeader(@PathVariable int group_number) {
		int lider = groupService.getGroupLeader(group_number);
		return userRepository.findById(lider).get();
	}
	
	@PutMapping("update-group")
	public ResponseEntity<?> updateGroup(@RequestBody GroupRequest groupRequest) {
		Group groupToUpdate = groupService.findGroup(groupRequest.getGroupNumber());
		if (groupToUpdate != null) {
			
		}
	}
}
