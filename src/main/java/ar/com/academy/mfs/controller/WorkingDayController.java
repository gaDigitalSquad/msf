package ar.com.academy.mfs.controller;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

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

import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.request.WorkingDayRequest;
import ar.com.academy.mfs.service.WorkingDayService;
import ar.com.academy.mfs.model.Area;
import ar.com.academy.mfs.model.Group;
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
	@ResponseBody ResponseEntity<?> postWorkingDay(@Valid @RequestBody WorkingDayRequest workingDayRequest) {
		User supervisor = userRepository.findById( workingDayRequest.getSupervisor()).get();
		User user = userRepository.findById( workingDayRequest.getUser()).get();
		if(supervisor == null || user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supervisor o Usuario no valido");
		
		System.out.println(workingDayRequest.getWorkingDate());
		System.out.println(workingDayRequest.isPresent());
		
		Group group = groupRepository.findUserGroup(supervisor.getUser_id());
		
		System.out.println(group.getZone_id());
		Zone zone = zoneRepository.findById(group.getZone_id()).get();
		if(zone == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no valida");
		
		System.out.println(group.getArea_id());
		Area area = areaRepository.findById(group.getArea_id()).get();
		if(area == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area no valida");
		
		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
		long diff = workingDayRequest.getTo_hour().getTime() - workingDayRequest.getFrom_hour().getTime();
		
		int hoursWorked = (int) (diff / (60 * 60 * 1000));
		System.out.println("difference between hours: " + crunchifyFormatter.format(hoursWorked));
		
		WorkingDay workingDay = new WorkingDay(supervisor.getUser_id(),
												user.getUser_id(), 
												workingDayRequest.isPresent(), 
												workingDayRequest.getWorkingDate(), 
												workingDayRequest.getFrom_hour(), 
												workingDayRequest.getTo_hour(), 
												zone.getZoneId(),
												area.getArea_id(),
												workingDayRequest.getAmountOfNewPartners(), 
												(float)workingDayRequest.getTotalAmount(), 
												workingDayRequest.getObservations(),
												hoursWorked);
		
		workingDayRepository.save(workingDay);
		
		return ResponseEntity.status(HttpStatus.OK).body(workingDay);
	}
	
	// Con el ID del usuario vamos a buscar todos los socios que consiguio en cierto periodo de tiempo
	@PostMapping("/workingDay/{user_id}")
	@ResponseBody ResponseEntity<?> getMetricasUsuario(@PathVariable int user_id, @RequestBody DateRequest dateRequest) {
	
		User user = userRepository.findById(user_id).get();
		if(user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
		
		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetweenAndUser(user.getUser_id(), dateRequest.getFrom(), dateRequest.getTo());
		
		float socios = 0;
		float monto = 0;
		float horas = 0;
		float sociosHora = 0;
		
		for(WorkingDay wk: workingDays) {
			socios = socios + wk.getAmountOfNewPartners();
			monto = monto + wk.getTotalAmount();
			horas = horas + wk.getHours_worked();
		}
		
		sociosHora = socios / horas;

		Metricas m = new Metricas(socios, monto, horas, sociosHora);
		
		return ResponseEntity.status(HttpStatus.OK).body(m);
	}
	
}
