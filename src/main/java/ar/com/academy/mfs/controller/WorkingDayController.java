package ar.com.academy.mfs.controller;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import ar.com.academy.mfs.service.UserService;
import ar.com.academy.mfs.service.WorkingDayService;
import ar.com.academy.mfs.error.NotGroupFoundException;
import ar.com.academy.mfs.error.NotLiderException;
import ar.com.academy.mfs.error.NotUserFoundException;
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
import ar.com.academy.mfs.response.UserMetricas;

@RestController
public class WorkingDayController {

	@Autowired
	private WorkingDayRepository workingDayRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ZoneRepository zoneRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkingDayService workingDayService;
	
	@PostMapping("/workingDay")
	@ResponseBody ResponseEntity<?> postWorkingDay(@Valid @RequestBody WorkingDayRequest workingDayRequest) {
		User supervisor = userRepository.findById( workingDayRequest.getSupervisor()).get();
		User user = userRepository.findById( workingDayRequest.getUser()).get();
		if(supervisor == null || user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supervisor o Usuario no valido");
		
		//System.out.println(workingDayRequest.getWorkingDate());
		//System.out.println(workingDayRequest.isPresent());
		
		Group group = groupRepository.findUserGroup(supervisor.getUser_id());
		
		//System.out.println(group.getZone_id());
		Zone zone = zoneRepository.findById(group.getZone_id()).get();
		if(zone == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no valida");
		
		//System.out.println(group.getArea_id());
		Area area = areaRepository.findById(group.getArea_id()).get();
		if(area == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Area no valida");
		
		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
		long diff = workingDayRequest.getTo_hour().getTime() - workingDayRequest.getFrom_hour().getTime();
		
		int hoursWorked = (int) (diff / (60 * 60 * 1000));
		//System.out.println("difference between hours: " + crunchifyFormatter.format(hoursWorked));
		
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
	
	@PostMapping("/workingDay/lider/{user_id}")
	@ResponseBody
	public ResponseEntity<?> getMetricasDeMisSensibilizadores(@PathVariable int user_id, @RequestBody DateRequest dateRequest) throws NotUserFoundException, NotLiderException{
		Optional<User> user = userRepository.findById(user_id);
		if(!user.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El lider solicitado no existe");
		else if(user.get().getRole_id() != 3)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario solicitado no es un lider");
		else {
			User lider = user.get();
			List<UserMetricas> metricasDeSensibilizadores = new ArrayList<>();
			List<User> sensibilizadores = new ArrayList<>();
			sensibilizadores = userService.getMySens(lider.getUser_id());
			for(User userToSave: sensibilizadores) {
				UserMetricas userMetrica = new UserMetricas(userToSave, workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
				metricasDeSensibilizadores.add(userMetrica);
			}
			return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadores);
		}
	}
	
	@PostMapping("/workingDay/group/{group_number}")
	@ResponseBody
	public ResponseEntity<?> getMetricasGroup(@PathVariable int group_number, @RequestBody DateRequest dateRequest) throws NotGroupFoundException, NotUserFoundException{
		Group group = groupRepository.findByGroupNumber(group_number);
		if(group==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El grupo no existe");
		List<User> sensibilizadores = new ArrayList<>();
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		sensibilizadores = userService.getMySens(group.getSupervisor());
		for(User userToSave: sensibilizadores) {
			UserMetricas userMetrica = new UserMetricas(userToSave, workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
			metricasDeSensibilizadoresLider.add(userMetrica);
		}
		Optional<User> lider = userRepository.findById(group.getSupervisor());
		User liderSaved = lider.get();
		UserMetricas userMetrica = new UserMetricas(liderSaved, workingDayService.getMetricasUsuario(group.getSupervisor(), dateRequest));
		metricasDeSensibilizadoresLider.add(userMetrica);
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}
	
	@PostMapping("/workingDay/zone/{zone_id}")
	@ResponseBody
	public ResponseEntity<?> getMetricasZone(@PathVariable int zone_id, @RequestBody DateRequest dateRequest) throws NotUserFoundException{
		Set<Integer> groupsOfZone = new HashSet<>();
		groupsOfZone = groupRepository.findAllbyZoneId(zone_id);
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		for(Integer groupNumber: groupsOfZone){
			Group group = groupRepository.findByGroupNumber(groupNumber);
			List<User> sensibilizadores = new ArrayList<>();
			sensibilizadores = userService.getMySens(group.getSupervisor());
			for(User userToSave: sensibilizadores) {
				UserMetricas userMetrica = new UserMetricas(userToSave, workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
				metricasDeSensibilizadoresLider.add(userMetrica);
			}
			Optional<User> lider = userRepository.findById(group.getSupervisor());
			User liderSaved = lider.get();
			UserMetricas userMetrica = new UserMetricas(liderSaved, workingDayService.getMetricasUsuario(group.getSupervisor(), dateRequest));
			metricasDeSensibilizadoresLider.add(userMetrica);
		}
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}
	
	@PostMapping("/workingDay/general")
	@ResponseBody
	public ResponseEntity<?> getMetricasGeneral(@RequestBody DateRequest dateRequest) throws NotUserFoundException{
		Set<Integer> allZones = new HashSet<>();
		allZones = groupRepository.findAllZones();
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		for(Integer zones: allZones){
			Set<Integer> groupsOfZone = new HashSet<>();
			groupsOfZone = groupRepository.findAllbyZoneId(zones);
			for(Integer groupNumber: groupsOfZone){
				Group group = groupRepository.findByGroupNumber(groupNumber);
				List<User> sensibilizadores = new ArrayList<>();
				sensibilizadores = userService.getMySens(group.getSupervisor());
				for(User userToSave: sensibilizadores) {
					UserMetricas userMetrica = new UserMetricas(userToSave, workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
					metricasDeSensibilizadoresLider.add(userMetrica);
				}
				Optional<User> lider = userRepository.findById(group.getSupervisor());
				User liderSaved = lider.get();
				UserMetricas userMetrica = new UserMetricas(liderSaved, workingDayService.getMetricasUsuario(group.getSupervisor(), dateRequest));
				metricasDeSensibilizadoresLider.add(userMetrica);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}
}
