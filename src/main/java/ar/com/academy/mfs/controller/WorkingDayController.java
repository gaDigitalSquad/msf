package ar.com.academy.mfs.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.request.WorkingDayRequest;
import ar.com.academy.mfs.service.WorkingDayService;
import ar.com.academy.mfs.model.Area;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.AreaRepository;
import ar.com.academy.mfs.repository.GroupRepository;
import ar.com.academy.mfs.repository.WorkingDayRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.DateRequest;
import ar.com.academy.mfs.response.Metricas;

@RestController
public class WorkingDayController {

	@Autowired
	WorkingDayRepository workingDayRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ZoneRepository zoneRepository;
	@Autowired
	AreaRepository areaRepository;
	@Autowired
	GroupRepository groupRepository;
	
	@PostMapping("/workingDay")
	@ResponseBody ResponseEntity postWorkingDay(@Valid @RequestBody WorkingDayRequest workingDayRequest) {
		User supervisor = userRepository.findById((long) workingDayRequest.getSupervisor()).get();
		User user = userRepository.findById((long) workingDayRequest.getUser()).get();
		if(supervisor == null || user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supervisor o Usuario no valido");
		
		Zone zone = zoneRepository.findById(workingDayRequest.getZone()).get();
		if(zone == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no valida");
		
		Area area = areaRepository.findById(workingDayRequest.getArea()).get();
		if(area == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area no valida");
		
		WorkingDay workingDay = new WorkingDay(supervisor.getUser_id(), 
												user.getUser_id(), 
												workingDayRequest.isPresent(), 
												workingDayRequest.getWorkingDate(), 
												workingDayRequest.getFrom_hour(), 
												workingDayRequest.getTo_hour(), 
												zone.getZoneId(),
												area.getArea_id(),
												workingDayRequest.getAmountOfNewPartners(), 
												workingDayRequest.getTotalAmount(), 
												workingDayRequest.getObservations());
		
		workingDayRepository.save(workingDay);
		
		return ResponseEntity.status(HttpStatus.OK).body(workingDay);
	}
	
	// Con el ID del usuario vamos a buscar todos los socios que consiguio en cierto periodo de tiempo
	@PostMapping("users/{idUser}/workingDay")
	@ResponseBody ResponseEntity<?> getMetricasUsuario(@PathVariable long idUser, @RequestBody DateRequest dateRequest) {
	
		User user = userRepository.findById(idUser).get();
		if(user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
		
		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetweenAndUser(dateRequest.getFrom(), dateRequest.getTo(), user);
		
		int cant = 0;
		float socios = 0;

		for(WorkingDay wk: workingDays) {
			cant = cant + wk.getAmountOfNewPartners();
			socios = socios + wk.getTotalAmount();
		}
		
		Metricas m = new Metricas(cant,socios);
		
		return ResponseEntity.status(HttpStatus.OK).body(m);
	}
	
	/*
	@PostMapping("groups/{idGroup}/workingDays")
	@ResponseBody ResponseEntity<?> getMetricasGroup(@PathVariable Long idGroup, @RequestBody DateRequest dateRequest) {
	
		Group group = groupRepository.findById(idGroup).get();
		if(group == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid group");
		
		List<User> users = userRepository.findByGroup(group);
		return ResponseEntity.status(HttpStatus.OK).body(group);
		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetween(dateRequest.getFrom(), dateRequest.getTo());
		
		int cant = 0;
		float socios = 0;
		
		for(User user: users) {
			for(WorkingDay wk: workingDays) {
				if(wk.getUser() == user) {
					cant = cant + wk.getAmountOfNewPartners();
					socios = socios + wk.getTotalAmount();
				}
			}
		}
		
		Metricas m = new Metricas(cant,socios);
		
		return ResponseEntity.status(HttpStatus.OK).body(m);
	}
	*/
	
}
