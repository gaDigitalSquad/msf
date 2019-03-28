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

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.AreaRepository;
import ar.com.academy.mfs.repository.GroupRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.GroupRequest;
import ar.com.academy.mfs.request.UpdateGroupRequest;
import ar.com.academy.mfs.response.UserGroupResponse;
import ar.com.academy.mfs.service.GroupService;
import ar.com.academy.mfs.service.UserService;
import ar.com.academy.mfs.utils.EntityUtils;

@RestController
public class GroupController {
	@Autowired
	GroupService groupService;
	@Autowired
	UserService userService;
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
		if (supervisor == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El líder seleccionado no existe");
		
		Zone zone = zoneRepository.findById(groupRequest.getZone()).get();
		if(zone == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La zona seleccionada no es válida");
		
		Integer numberGroup = groupRepository.findLastGroupNumber();
		if (numberGroup == null) {
			numberGroup = 1;
		}
		else {
			numberGroup++;
		}
		
		List<Integer> sens = groupRequest.getSens();
		
		if(!EntityUtils.checkDuplicateUsingAdd(sens)) {
		
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
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No puede ingresar Sensibilizadores repetidos");
		}
			return ResponseEntity.status(HttpStatus.OK).body("Se ha creado el grupo");
	}
	
	@GetMapping("/groups")
	public List<Group> getGroups() {
		return groupService.getGroups();
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
	
	/* Dada la zona y turno, se obtienen los grupos */
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
	
	@PostMapping("update-group")
	public ResponseEntity<?> updateGroup(@RequestBody UpdateGroupRequest groupRequest) {
		Group groupToUpdate = groupService.findGroup(groupRequest.getLeader());
		if (groupToUpdate != null) {
			if (groupRequest.getNewLeader() != 0) {
				/* Se cambia de líder */
				groupService.updateLeader(groupToUpdate ,groupRequest.getNewLeader());
				
				/* El líder viejo no tiene grupo asignado */
				userService.setUserWithoutGroup(groupRequest.getLeader());
			}
			for (User u: groupRequest.getSens()) {
				groupService.addSensToGroup(u.getUser_id(), groupToUpdate);
				userService.setGroupNumber(u.getUser_id(), groupToUpdate.getGroup_number());
			}
			for (User u: groupRequest.getSensWithoutGroup() ) {
				userService.setUserWithoutGroup(u.getUser_id());
				groupService.deleteSensFromGroup(u.getUser_id());
			}
			return ResponseEntity.status(HttpStatus.OK).body(groupToUpdate);
		} else {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupToUpdate);
		}
	}
	/* Dadel grupo , se obtienen todos los sensibilizadores */
	@GetMapping("/users-by-group/{group_number}")
	public List<UserGroupResponse> getusersByGroup(@PathVariable int group_number) {
		 return groupService.getUserByGroup(group_number);
	}
	
}
