package ar.com.academy.mfs.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import ar.com.academy.mfs.response.WorkingDayResponse;

@RestController
public class WorkingDayController {

	@Autowired
	private WorkingDayRepository workingDayRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ZoneRepository zoneRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkingDayService workingDayService;

	@PostMapping("/workingDay")
	@ResponseBody
	ResponseEntity<?> postWorkingDay(@Valid @RequestBody WorkingDayRequest workingDayRequest) {
		User supervisor = userRepository.findById(workingDayRequest.getSupervisor()).get();
		User user = userRepository.findById(workingDayRequest.getUser()).get();
		if (supervisor == null || user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supervisor o Usuario no valido");

		Group group = groupRepository.findUserGroup(supervisor.getUser_id());

		Zone zone = zoneRepository.findById(group.getZone_id()).get();
		if (zone == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no valida");

		DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
		long diff = workingDayRequest.getTo_hour().getTime() - workingDayRequest.getFrom_hour().getTime();

		int hoursWorked = (int) (diff / (60 * 60 * 1000));

		WorkingDay workingDay = new WorkingDay(supervisor.getUser_id(), user.getUser_id(),
				workingDayRequest.isPresent(), workingDayRequest.getWorkingDate(), workingDayRequest.getFrom_hour(),
				workingDayRequest.getTo_hour(), zone.getZoneId(), workingDayRequest.getAmountOfNewPartners(),
				(float) workingDayRequest.getTotalAmount(), workingDayRequest.getObservations(), hoursWorked, workingDayRequest.isCompleted());

		workingDayRepository.save(workingDay);

		return ResponseEntity.status(HttpStatus.OK).body(workingDay);
	}
	
	/**
	 * Método para guardar un set de working days
	 * @param listOfWorkingDays
	 * @return workinkDaysResponse
	 */
	
	@PostMapping("/workingDays")
	public ResponseEntity<?> createWorkingDays(@RequestBody ArrayList<WorkingDayRequest> listOfWorkingDays) {
		List<WorkingDayResponse> workingDaysResponse = new ArrayList<>();
		for (WorkingDayRequest inputWorkingDay: listOfWorkingDays) {
			WorkingDayResponse wd = workingDayService.createWorkingDayResponse(inputWorkingDay);
			workingDaysResponse.add(wd);
		}
		return ResponseEntity.status(HttpStatus.OK).body(workingDaysResponse);
	}

	// Conseguir las métricas de un determinado usuario

	@GetMapping("/workingDay/{user_id}")
	@ResponseBody
	public ResponseEntity<?> getWorkingDay(@PathVariable int user_id, @RequestBody Date date) {
		WorkingDay day = workingDayRepository.findByWorkingDateAndUser(user_id, date);
		return ResponseEntity.status(HttpStatus.OK).body(day);
	}

	// Con el ID del usuario vamos a buscar todos los socios que consiguio en cierto
	// periodo de tiempo
	@PostMapping("/workingDay/{user_id}")
	@ResponseBody
	ResponseEntity<?> getMetricasUsuario(@PathVariable int user_id, @RequestBody DateRequest dateRequest) {

		User user = userRepository.findById(user_id).get();
		if (user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");

		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetweenAndUser(user.getUser_id(),
				dateRequest.getFrom(), dateRequest.getTo());

		float socios = 0;
		float monto = 0;
		float horas = 0;
		float sociosHora = 0;

		for (WorkingDay wk : workingDays) {
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
	public ResponseEntity<?> getMetricasDeMisSensibilizadores(@PathVariable int user_id,
			@RequestBody DateRequest dateRequest) {
		Optional<User> user = userRepository.findById(user_id);
		if (!user.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El lider solicitado no existe");
		else if (user.get().getRole_id() != 3)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario solicitado no es un lider");
		else {
			User lider = user.get();
			List<UserMetricas> metricasDeSensibilizadores = new ArrayList<>();
			List<User> sensibilizadores = new ArrayList<>();
			sensibilizadores = userService.getMySens(lider.getUser_id());
			for (User userToSave : sensibilizadores) {
				UserMetricas userMetrica = new UserMetricas(userToSave,
						workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
				metricasDeSensibilizadores.add(userMetrica);
			}
			return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadores);
		}
	}

	@PostMapping("/metrics/group/{group_number}")
	@ResponseBody
	public ResponseEntity<?> getMetricasGroup(@PathVariable int group_number, @RequestBody DateRequest dateRequest) {
		Group group = groupRepository.findByGroupNumber(group_number);
		if (group == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El grupo no existe");
		List<User> sensibilizadores = new ArrayList<>();
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		sensibilizadores = userService.getMySens(group.getSupervisor());
		for (User userToSave : sensibilizadores) {
			UserMetricas userMetrica = new UserMetricas(userToSave,
					workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
			metricasDeSensibilizadoresLider.add(userMetrica);
		}
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}

	@PostMapping("/workingDay/zone/{zone_id}")
	@ResponseBody
	public ResponseEntity<?> getMetricasZone(@PathVariable int zone_id, @RequestBody DateRequest dateRequest) {
		Set<Integer> groupsOfZone = new HashSet<>();
		groupsOfZone = groupRepository.findAllbyZoneId(zone_id);
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		for (Integer groupNumber : groupsOfZone) {
			Group group = groupRepository.findByGroupNumber(groupNumber);
			List<User> sensibilizadores = new ArrayList<>();
			sensibilizadores = userService.getMySens(group.getSupervisor());
			for (User userToSave : sensibilizadores) {
				UserMetricas userMetrica = new UserMetricas(userToSave,
						workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
				metricasDeSensibilizadoresLider.add(userMetrica);
			}
			Optional<User> lider = userRepository.findById(group.getSupervisor());
			User liderSaved = lider.get();
			UserMetricas userMetrica = new UserMetricas(liderSaved,
					workingDayService.getMetricasUsuario(group.getSupervisor(), dateRequest));
			metricasDeSensibilizadoresLider.add(userMetrica);
		}
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}

	@PostMapping("/workingDay/general")
	@ResponseBody
	public ResponseEntity<?> getMetricasGeneral(@RequestBody DateRequest dateRequest) {
		Set<Integer> allZones = new HashSet<>();
		allZones = groupRepository.findAllZones();
		List<UserMetricas> metricasDeSensibilizadoresLider = new ArrayList<>();
		for (Integer zones : allZones) {
			Set<Integer> groupsOfZone = new HashSet<>();
			groupsOfZone = groupRepository.findAllbyZoneId(zones);
			for (Integer groupNumber : groupsOfZone) {
				Group group = groupRepository.findByGroupNumber(groupNumber);
				List<User> sensibilizadores = new ArrayList<>();
				sensibilizadores = userService.getMySens(group.getSupervisor());
				for (User userToSave : sensibilizadores) {
					UserMetricas userMetrica = new UserMetricas(userToSave,
							workingDayService.getMetricasUsuario(userToSave.getUser_id(), dateRequest));
					metricasDeSensibilizadoresLider.add(userMetrica);
				}
				Optional<User> lider = userRepository.findById(group.getSupervisor());
				User liderSaved = lider.get();
				UserMetricas userMetrica = new UserMetricas(liderSaved,
						workingDayService.getMetricasUsuario(group.getSupervisor(), dateRequest));
				metricasDeSensibilizadoresLider.add(userMetrica);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(metricasDeSensibilizadoresLider);
	}

	@PostMapping("/workingDay/group/{lider_id}")
	public ResponseEntity<?> getGroupWorkingDay(@PathVariable int lider_id, @RequestBody DateRequest dateRequest) {
		Optional<User> user = userRepository.findById(lider_id);
		if (!user.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El lider solicitado no existe");
		else if (user.get().getRole_id() != 3)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario solicitado no es un lider");
		else {
			User lider = user.get();
			List<User> sensibilizadores = userService.getMySens(lider.getUser_id());
			sensibilizadores.add(lider);
			List<WorkingDay> workingDaysGroup = new ArrayList<>();
			for (User userToGet : sensibilizadores) {
				WorkingDay userWorkDay = workingDayService.getWorkingDay(userToGet.getUser_id(), dateRequest);
				if (userWorkDay != null) {
					workingDaysGroup.add(userWorkDay);
				}
			}
			System.out.println(CollectionUtils.isEmpty(workingDaysGroup));
			if (CollectionUtils.isEmpty(workingDaysGroup)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se ha cargado el workingDay de ese dia");
			}
			return ResponseEntity.status(HttpStatus.OK).body(workingDaysGroup);
		}
	}

	// Obtener métricas por mes de un sensibilizador

	@GetMapping("/full-metrics-sens/{user_id}")
	public ResponseEntity<?> getAllUserMetrics(@PathVariable int user_id) {
		User user = userRepository.findById(user_id).get();
		if (user == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");

		// Obtengo AÑO actual
		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Establezco el rango de fecha, se asume que la campaña es anual
		Date start = Date.valueOf(year + "-01-01");
		Date end = Date.valueOf(year + "-12-31");
		
		System.out.println(year + "-01-01");

		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetweenAndUser(user.getUser_id(), start,
				end);
		List<Metricas> metricas = new ArrayList<Metricas>();

		float sociosEnero = 0, sociosFebrero = 0, sociosMarzo = 0, sociosAbril = 0, sociosMayo = 0, sociosJunio = 0,
				sociosJulio = 0, sociosAgosto = 0, sociosSep = 0, sociosOct = 0, sociosNov = 0, sociosDic = 0;
		float montoEnero = 0, montoFebrero = 0, montoMarzo = 0, montoAbril = 0, montoMayo = 0, montoJunio = 0,
				montoJulio = 0, montoAgosto = 0, montoSep = 0, montoOct = 0, montoNov = 0, montoDic = 0;
		float horasEnero = 0, horasFebrero = 0, horasMarzo = 0, horasAbril = 0, horasMayo = 0, horasJunio = 0,
				horasJulio = 0, horasAgosto = 0, horasSep = 0, horasOct = 0, horasNov = 0, horasDic = 0;
		float sociosHoraEnero = 0, sociosHoraFebrero = 0, sociosHoraMarzo = 0, sociosHoraAbril = 0, sociosHoraMayo = 0,
				sociosHoraJunio = 0, sociosHoraJulio = 0, sociosHoraAgosto = 0, sociosHoraSep = 0, sociosHoraOct = 0,
				sociosHoraNov = 0, sociosHoraDic = 0;

		for (WorkingDay wk : workingDays) {
			System.out.println(wk.getWorkingDate().toString());
			LocalDate date = wk.getWorkingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (date.getMonthValue() == 1) {
				sociosEnero = sociosEnero + wk.getAmountOfNewPartners();
				montoEnero = montoEnero + wk.getTotalAmount();
				horasEnero = horasEnero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 2) {
				sociosFebrero = sociosFebrero + wk.getAmountOfNewPartners();
				montoFebrero = montoFebrero + wk.getTotalAmount();
				horasFebrero = horasFebrero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 3) {
				sociosMarzo = sociosMarzo + wk.getAmountOfNewPartners();
				montoMarzo = montoMarzo + wk.getTotalAmount();
				horasMarzo = horasMarzo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 4) {
				sociosAbril = sociosAbril + wk.getAmountOfNewPartners();
				montoAbril = montoAbril + wk.getTotalAmount();
				horasAbril = horasAbril + wk.getHours_worked();
			}
			if (date.getMonthValue() == 5) {
				sociosMayo = sociosMayo + wk.getAmountOfNewPartners();
				montoMayo = montoMayo + wk.getTotalAmount();
				horasMayo = horasMayo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 6) {
				sociosJunio = sociosJunio + wk.getAmountOfNewPartners();
				montoJunio = montoJunio + wk.getTotalAmount();
				horasJunio = horasJunio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 7) {
				sociosJulio = sociosJulio + wk.getAmountOfNewPartners();
				montoJulio = montoJulio + wk.getTotalAmount();
				horasJulio = horasJulio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 8) {
				sociosAgosto = sociosAgosto + wk.getAmountOfNewPartners();
				montoAgosto = montoAgosto + wk.getTotalAmount();
				horasAgosto = horasAgosto + wk.getHours_worked();
			}
			if (date.getMonthValue() == 9) {
				sociosSep = sociosSep + wk.getAmountOfNewPartners();
				montoSep = montoSep + wk.getTotalAmount();
				horasSep = horasSep + wk.getHours_worked();
			}
			if (date.getMonthValue() == 10) {
				sociosOct = sociosOct + wk.getAmountOfNewPartners();
				montoOct = montoOct + wk.getTotalAmount();
				horasOct = horasOct + wk.getHours_worked();
			}
			if (date.getMonthValue() == 11) {
				sociosNov = sociosNov + wk.getAmountOfNewPartners();
				montoNov = montoNov + wk.getTotalAmount();
				horasNov = horasNov + wk.getHours_worked();
			}
			if (date.getMonthValue() == 12) {
				sociosDic = sociosDic + wk.getAmountOfNewPartners();
				montoDic = montoDic + wk.getTotalAmount();
				horasDic = horasDic + wk.getHours_worked();
			}
		}

		sociosHoraEnero = sociosEnero / horasEnero;
		sociosHoraFebrero = sociosFebrero / horasFebrero;
		sociosHoraMarzo = sociosMarzo / horasMarzo;
		sociosHoraAbril = sociosAbril / horasAbril;
		sociosHoraMayo = sociosMayo / horasMayo;
		sociosHoraJunio = sociosJunio / horasJunio;
		sociosHoraJulio = sociosJulio / horasJulio;
		sociosHoraAgosto = sociosAgosto / horasAgosto;
		sociosHoraSep = sociosSep / horasSep;
		sociosHoraOct = sociosOct / horasOct;
		sociosHoraNov = sociosNov / horasNov;
		sociosHoraDic = sociosDic / horasDic;

		Metricas enero = new Metricas(sociosEnero, montoEnero, horasEnero, sociosHoraEnero);
		Metricas febrero = new Metricas(sociosFebrero, montoFebrero, horasFebrero, sociosHoraFebrero);
		Metricas marzo = new Metricas(sociosMarzo, montoMarzo, horasMarzo, sociosHoraMarzo);
		Metricas abril = new Metricas(sociosAbril, montoAbril, horasAbril, sociosHoraAbril);
		Metricas mayo = new Metricas(sociosMayo, montoMayo, horasMayo, sociosHoraMayo);
		Metricas junio = new Metricas(sociosJunio, montoJunio, horasJunio, sociosHoraJunio);
		Metricas julio = new Metricas(sociosJulio, montoJulio, horasJulio, sociosHoraJulio);
		Metricas agosto = new Metricas(sociosAgosto, montoAgosto, horasAgosto, sociosHoraAgosto);
		Metricas septiembre = new Metricas(sociosSep, montoSep, horasSep, sociosHoraSep);
		Metricas octubre = new Metricas(sociosOct, montoOct, horasOct, sociosHoraOct);
		Metricas noviembre = new Metricas(sociosNov, montoOct, horasOct, sociosHoraNov);
		Metricas diciembre = new Metricas(sociosDic, montoDic, horasDic, sociosHoraDic);

		metricas.add(enero);
		metricas.add(febrero);
		metricas.add(marzo);
		metricas.add(abril);
		metricas.add(mayo);
		metricas.add(junio);
		metricas.add(julio);
		metricas.add(agosto);
		metricas.add(septiembre);
		metricas.add(octubre);
		metricas.add(noviembre);
		metricas.add(diciembre);

		return ResponseEntity.status(HttpStatus.OK).body(metricas);
	}

	// Obtener métricas por mes de un grupo

	@GetMapping("/full-metrics-group/{group_number}")
	public ResponseEntity<?> getAllGroupMetrics(@PathVariable int group_number) {

		// Obtengo AÑO actual
		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Establezco el rango de fecha, se asume que la campaña es anual
		Date start = Date.valueOf(year + "-01-01");
		Date end = Date.valueOf(year + "-12-31");
		
		System.out.println(year + "-01-01");

		// Primero, obtengo todos los working day del grupo

		List<WorkingDay> workingDays = workingDayRepository.findByGroupNumberAndDate(group_number, start, end);

		// Declaro array donde voy a devolver las métricas
		List<Metricas> metricas = new ArrayList<Metricas>();

		// Recorro y filtro por mes

		float sociosEnero = 0, sociosFebrero = 0, sociosMarzo = 0, sociosAbril = 0, sociosMayo = 0, sociosJunio = 0,
				sociosJulio = 0, sociosAgosto = 0, sociosSep = 0, sociosOct = 0, sociosNov = 0, sociosDic = 0;
		float montoEnero = 0, montoFebrero = 0, montoMarzo = 0, montoAbril = 0, montoMayo = 0, montoJunio = 0,
				montoJulio = 0, montoAgosto = 0, montoSep = 0, montoOct = 0, montoNov = 0, montoDic = 0;
		float horasEnero = 0, horasFebrero = 0, horasMarzo = 0, horasAbril = 0, horasMayo = 0, horasJunio = 0,
				horasJulio = 0, horasAgosto = 0, horasSep = 0, horasOct = 0, horasNov = 0, horasDic = 0;
		float sociosHoraEnero = 0, sociosHoraFebrero = 0, sociosHoraMarzo = 0, sociosHoraAbril = 0, sociosHoraMayo = 0,
				sociosHoraJunio = 0, sociosHoraJulio = 0, sociosHoraAgosto = 0, sociosHoraSep = 0, sociosHoraOct = 0,
				sociosHoraNov = 0, sociosHoraDic = 0;

		for (WorkingDay wk : workingDays) {
			LocalDate date = wk.getWorkingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (date.getMonthValue() == 1) {
				sociosEnero = sociosEnero + wk.getAmountOfNewPartners();
				montoEnero = montoEnero + wk.getTotalAmount();
				horasEnero = horasEnero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 2) {
				sociosFebrero = sociosFebrero + wk.getAmountOfNewPartners();
				montoFebrero = montoFebrero + wk.getTotalAmount();
				horasFebrero = horasFebrero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 3) {
				sociosMarzo = sociosMarzo + wk.getAmountOfNewPartners();
				montoMarzo = montoMarzo + wk.getTotalAmount();
				horasMarzo = horasMarzo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 4) {
				sociosAbril = sociosAbril + wk.getAmountOfNewPartners();
				montoAbril = montoAbril + wk.getTotalAmount();
				horasAbril = horasAbril + wk.getHours_worked();
			}
			if (date.getMonthValue() == 5) {
				sociosMayo = sociosMayo + wk.getAmountOfNewPartners();
				montoMayo = montoMayo + wk.getTotalAmount();
				horasMayo = horasMayo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 6) {
				sociosJunio = sociosJunio + wk.getAmountOfNewPartners();
				montoJunio = montoJunio + wk.getTotalAmount();
				horasJunio = horasJunio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 7) {
				sociosJulio = sociosJulio + wk.getAmountOfNewPartners();
				montoJulio = montoJulio + wk.getTotalAmount();
				horasJulio = horasJulio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 8) {
				sociosAgosto = sociosAgosto + wk.getAmountOfNewPartners();
				montoAgosto = montoAgosto + wk.getTotalAmount();
				horasAgosto = horasAgosto + wk.getHours_worked();
			}
			if (date.getMonthValue() == 9) {
				sociosSep = sociosSep + wk.getAmountOfNewPartners();
				montoSep = montoSep + wk.getTotalAmount();
				horasSep = horasSep + wk.getHours_worked();
			}
			if (date.getMonthValue() == 10) {
				sociosOct = sociosOct + wk.getAmountOfNewPartners();
				montoOct = montoOct + wk.getTotalAmount();
				horasOct = horasOct + wk.getHours_worked();
			}
			if (date.getMonthValue() == 11) {
				sociosNov = sociosNov + wk.getAmountOfNewPartners();
				montoNov = montoNov + wk.getTotalAmount();
				horasNov = horasNov + wk.getHours_worked();
			}
			if (date.getMonthValue() == 12) {
				sociosDic = sociosDic + wk.getAmountOfNewPartners();
				montoDic = montoDic + wk.getTotalAmount();
				horasDic = horasDic + wk.getHours_worked();
			}
		}

		sociosHoraEnero = sociosEnero / horasEnero;
		sociosHoraFebrero = sociosFebrero / horasFebrero;
		sociosHoraMarzo = sociosMarzo / horasMarzo;
		sociosHoraAbril = sociosAbril / horasAbril;
		sociosHoraMayo = sociosMayo / horasMayo;
		sociosHoraJunio = sociosJunio / horasJunio;
		sociosHoraJulio = sociosJulio / horasJulio;
		sociosHoraAgosto = sociosAgosto / horasAgosto;
		sociosHoraSep = sociosSep / horasSep;
		sociosHoraOct = sociosOct / horasOct;
		sociosHoraNov = sociosNov / horasNov;
		sociosHoraDic = sociosDic / horasDic;

		Metricas enero = new Metricas(sociosEnero, montoEnero, horasEnero, sociosHoraEnero);
		Metricas febrero = new Metricas(sociosFebrero, montoFebrero, horasFebrero, sociosHoraFebrero);
		Metricas marzo = new Metricas(sociosMarzo, montoMarzo, horasMarzo, sociosHoraMarzo);
		Metricas abril = new Metricas(sociosAbril, montoAbril, horasAbril, sociosHoraAbril);
		Metricas mayo = new Metricas(sociosMayo, montoMayo, horasMayo, sociosHoraMayo);
		Metricas junio = new Metricas(sociosJunio, montoJunio, horasJunio, sociosHoraJunio);
		Metricas julio = new Metricas(sociosJulio, montoJulio, horasJulio, sociosHoraJulio);
		Metricas agosto = new Metricas(sociosAgosto, montoAgosto, horasAgosto, sociosHoraAgosto);
		Metricas septiembre = new Metricas(sociosSep, montoSep, horasSep, sociosHoraSep);
		Metricas octubre = new Metricas(sociosOct, montoOct, horasOct, sociosHoraOct);
		Metricas noviembre = new Metricas(sociosNov, montoOct, horasOct, sociosHoraNov);
		Metricas diciembre = new Metricas(sociosDic, montoDic, horasDic, sociosHoraDic);

		metricas.add(enero);
		metricas.add(febrero);
		metricas.add(marzo);
		metricas.add(abril);
		metricas.add(mayo);
		metricas.add(junio);
		metricas.add(julio);
		metricas.add(agosto);
		metricas.add(septiembre);
		metricas.add(octubre);
		metricas.add(noviembre);
		metricas.add(diciembre);

		return ResponseEntity.status(HttpStatus.OK).body(metricas);
	}

	@GetMapping("/full-metrics-zone/{zone_id}")
	public ResponseEntity<?> getAllZoneMetrics(@PathVariable int zone_id) {

		// Obtengo AÑO actual
		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Establezco el rango de fecha, se asume que la campaña es anual
		Date start = Date.valueOf(year + "-01-01");
		Date end = Date.valueOf(year + "-12-31");
		
		System.out.println(year + "-01-01");

		// Primero, obtengo todos los working day de la zona

		List<WorkingDay> workingDays = workingDayRepository.findByZoneAndDate(zone_id, start, end);

		// Declaro array donde voy a devolver las métricas
		List<Metricas> metricas = new ArrayList<Metricas>();

		// Recorro y filtro por mes

		float sociosEnero = 0, sociosFebrero = 0, sociosMarzo = 0, sociosAbril = 0, sociosMayo = 0, sociosJunio = 0,
				sociosJulio = 0, sociosAgosto = 0, sociosSep = 0, sociosOct = 0, sociosNov = 0, sociosDic = 0;
		float montoEnero = 0, montoFebrero = 0, montoMarzo = 0, montoAbril = 0, montoMayo = 0, montoJunio = 0,
				montoJulio = 0, montoAgosto = 0, montoSep = 0, montoOct = 0, montoNov = 0, montoDic = 0;
		float horasEnero = 0, horasFebrero = 0, horasMarzo = 0, horasAbril = 0, horasMayo = 0, horasJunio = 0,
				horasJulio = 0, horasAgosto = 0, horasSep = 0, horasOct = 0, horasNov = 0, horasDic = 0;
		float sociosHoraEnero = 0, sociosHoraFebrero = 0, sociosHoraMarzo = 0, sociosHoraAbril = 0, sociosHoraMayo = 0,
				sociosHoraJunio = 0, sociosHoraJulio = 0, sociosHoraAgosto = 0, sociosHoraSep = 0, sociosHoraOct = 0,
				sociosHoraNov = 0, sociosHoraDic = 0;

		for (WorkingDay wk : workingDays) {
			LocalDate date = wk.getWorkingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (date.getMonthValue() == 1) {
				sociosEnero = sociosEnero + wk.getAmountOfNewPartners();
				montoEnero = montoEnero + wk.getTotalAmount();
				horasEnero = horasEnero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 2) {
				sociosFebrero = sociosFebrero + wk.getAmountOfNewPartners();
				montoFebrero = montoFebrero + wk.getTotalAmount();
				horasFebrero = horasFebrero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 3) {
				sociosMarzo = sociosMarzo + wk.getAmountOfNewPartners();
				montoMarzo = montoMarzo + wk.getTotalAmount();
				horasMarzo = horasMarzo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 4) {
				sociosAbril = sociosAbril + wk.getAmountOfNewPartners();
				montoAbril = montoAbril + wk.getTotalAmount();
				horasAbril = horasAbril + wk.getHours_worked();
			}
			if (date.getMonthValue() == 5) {
				sociosMayo = sociosMayo + wk.getAmountOfNewPartners();
				montoMayo = montoMayo + wk.getTotalAmount();
				horasMayo = horasMayo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 6) {
				sociosJunio = sociosJunio + wk.getAmountOfNewPartners();
				montoJunio = montoJunio + wk.getTotalAmount();
				horasJunio = horasJunio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 7) {
				sociosJulio = sociosJulio + wk.getAmountOfNewPartners();
				montoJulio = montoJulio + wk.getTotalAmount();
				horasJulio = horasJulio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 8) {
				sociosAgosto = sociosAgosto + wk.getAmountOfNewPartners();
				montoAgosto = montoAgosto + wk.getTotalAmount();
				horasAgosto = horasAgosto + wk.getHours_worked();
			}
			if (date.getMonthValue() == 9) {
				sociosSep = sociosSep + wk.getAmountOfNewPartners();
				montoSep = montoSep + wk.getTotalAmount();
				horasSep = horasSep + wk.getHours_worked();
			}
			if (date.getMonthValue() == 10) {
				sociosOct = sociosOct + wk.getAmountOfNewPartners();
				montoOct = montoOct + wk.getTotalAmount();
				horasOct = horasOct + wk.getHours_worked();
			}
			if (date.getMonthValue() == 11) {
				sociosNov = sociosNov + wk.getAmountOfNewPartners();
				montoNov = montoNov + wk.getTotalAmount();
				horasNov = horasNov + wk.getHours_worked();
			}
			if (date.getMonthValue() == 12) {
				sociosDic = sociosDic + wk.getAmountOfNewPartners();
				montoDic = montoDic + wk.getTotalAmount();
				horasDic = horasDic + wk.getHours_worked();
			}
		}

		sociosHoraEnero = sociosEnero / horasEnero;
		sociosHoraFebrero = sociosFebrero / horasFebrero;
		sociosHoraMarzo = sociosMarzo / horasMarzo;
		sociosHoraAbril = sociosAbril / horasAbril;
		sociosHoraMayo = sociosMayo / horasMayo;
		sociosHoraJunio = sociosJunio / horasJunio;
		sociosHoraJulio = sociosJulio / horasJulio;
		sociosHoraAgosto = sociosAgosto / horasAgosto;
		sociosHoraSep = sociosSep / horasSep;
		sociosHoraOct = sociosOct / horasOct;
		sociosHoraNov = sociosNov / horasNov;
		sociosHoraDic = sociosDic / horasDic;

		Metricas enero = new Metricas(sociosEnero, montoEnero, horasEnero, sociosHoraEnero);
		Metricas febrero = new Metricas(sociosFebrero, montoFebrero, horasFebrero, sociosHoraFebrero);
		Metricas marzo = new Metricas(sociosMarzo, montoMarzo, horasMarzo, sociosHoraMarzo);
		Metricas abril = new Metricas(sociosAbril, montoAbril, horasAbril, sociosHoraAbril);
		Metricas mayo = new Metricas(sociosMayo, montoMayo, horasMayo, sociosHoraMayo);
		Metricas junio = new Metricas(sociosJunio, montoJunio, horasJunio, sociosHoraJunio);
		Metricas julio = new Metricas(sociosJulio, montoJulio, horasJulio, sociosHoraJulio);
		Metricas agosto = new Metricas(sociosAgosto, montoAgosto, horasAgosto, sociosHoraAgosto);
		Metricas septiembre = new Metricas(sociosSep, montoSep, horasSep, sociosHoraSep);
		Metricas octubre = new Metricas(sociosOct, montoOct, horasOct, sociosHoraOct);
		Metricas noviembre = new Metricas(sociosNov, montoOct, horasOct, sociosHoraNov);
		Metricas diciembre = new Metricas(sociosDic, montoDic, horasDic, sociosHoraDic);

		metricas.add(enero);
		metricas.add(febrero);
		metricas.add(marzo);
		metricas.add(abril);
		metricas.add(mayo);
		metricas.add(junio);
		metricas.add(julio);
		metricas.add(agosto);
		metricas.add(septiembre);
		metricas.add(octubre);
		metricas.add(noviembre);
		metricas.add(diciembre);

		return ResponseEntity.status(HttpStatus.OK).body(metricas);
	}

	@GetMapping("/full-metrics")
	public ResponseEntity<?> getAllMetrics() {
		// Establezco el rango de fecha, se asume que la campaña es anual
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-12-31");

		// Primero, obtengo todos los working day del año campaña

		List<WorkingDay> workingDays = workingDayRepository.findBetweenDate(start, end);

		// Declaro array donde voy a devolver las métricas
		List<Metricas> metricas = new ArrayList<Metricas>();

		// Recorro y filtro por mes

		float sociosEnero = 0, sociosFebrero = 0, sociosMarzo = 0, sociosAbril = 0, sociosMayo = 0, sociosJunio = 0,
				sociosJulio = 0, sociosAgosto = 0, sociosSep = 0, sociosOct = 0, sociosNov = 0, sociosDic = 0;
		float montoEnero = 0, montoFebrero = 0, montoMarzo = 0, montoAbril = 0, montoMayo = 0, montoJunio = 0,
				montoJulio = 0, montoAgosto = 0, montoSep = 0, montoOct = 0, montoNov = 0, montoDic = 0;
		float horasEnero = 0, horasFebrero = 0, horasMarzo = 0, horasAbril = 0, horasMayo = 0, horasJunio = 0,
				horasJulio = 0, horasAgosto = 0, horasSep = 0, horasOct = 0, horasNov = 0, horasDic = 0;
		float sociosHoraEnero = 0, sociosHoraFebrero = 0, sociosHoraMarzo = 0, sociosHoraAbril = 0, sociosHoraMayo = 0,
				sociosHoraJunio = 0, sociosHoraJulio = 0, sociosHoraAgosto = 0, sociosHoraSep = 0, sociosHoraOct = 0,
				sociosHoraNov = 0, sociosHoraDic = 0;

		for (WorkingDay wk : workingDays) {
			LocalDate date = wk.getWorkingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (date.getMonthValue() == 1) {
				sociosEnero = sociosEnero + wk.getAmountOfNewPartners();
				montoEnero = montoEnero + wk.getTotalAmount();
				horasEnero = horasEnero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 2) {
				sociosFebrero = sociosFebrero + wk.getAmountOfNewPartners();
				montoFebrero = montoFebrero + wk.getTotalAmount();
				horasFebrero = horasFebrero + wk.getHours_worked();
			}
			if (date.getMonthValue() == 3) {
				sociosMarzo = sociosMarzo + wk.getAmountOfNewPartners();
				montoMarzo = montoMarzo + wk.getTotalAmount();
				horasMarzo = horasMarzo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 4) {
				sociosAbril = sociosAbril + wk.getAmountOfNewPartners();
				montoAbril = montoAbril + wk.getTotalAmount();
				horasAbril = horasAbril + wk.getHours_worked();
			}
			if (date.getMonthValue() == 5) {
				sociosMayo = sociosMayo + wk.getAmountOfNewPartners();
				montoMayo = montoMayo + wk.getTotalAmount();
				horasMayo = horasMayo + wk.getHours_worked();
			}
			if (date.getMonthValue() == 6) {
				sociosJunio = sociosJunio + wk.getAmountOfNewPartners();
				montoJunio = montoJunio + wk.getTotalAmount();
				horasJunio = horasJunio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 7) {
				sociosJulio = sociosJulio + wk.getAmountOfNewPartners();
				montoJulio = montoJulio + wk.getTotalAmount();
				horasJulio = horasJulio + wk.getHours_worked();
			}
			if (date.getMonthValue() == 8) {
				sociosAgosto = sociosAgosto + wk.getAmountOfNewPartners();
				montoAgosto = montoAgosto + wk.getTotalAmount();
				horasAgosto = horasAgosto + wk.getHours_worked();
			}
			if (date.getMonthValue() == 9) {
				sociosSep = sociosSep + wk.getAmountOfNewPartners();
				montoSep = montoSep + wk.getTotalAmount();
				horasSep = horasSep + wk.getHours_worked();
			}
			if (date.getMonthValue() == 10) {
				sociosOct = sociosOct + wk.getAmountOfNewPartners();
				montoOct = montoOct + wk.getTotalAmount();
				horasOct = horasOct + wk.getHours_worked();
			}
			if (date.getMonthValue() == 11) {
				sociosNov = sociosNov + wk.getAmountOfNewPartners();
				montoNov = montoNov + wk.getTotalAmount();
				horasNov = horasNov + wk.getHours_worked();
			}
			if (date.getMonthValue() == 12) {
				sociosDic = sociosDic + wk.getAmountOfNewPartners();
				montoDic = montoDic + wk.getTotalAmount();
				horasDic = horasDic + wk.getHours_worked();
			}
		}

		sociosHoraEnero = sociosEnero / horasEnero;
		sociosHoraFebrero = sociosFebrero / horasFebrero;
		sociosHoraMarzo = sociosMarzo / horasMarzo;
		sociosHoraAbril = sociosAbril / horasAbril;
		sociosHoraMayo = sociosMayo / horasMayo;
		sociosHoraJunio = sociosJunio / horasJunio;
		sociosHoraJulio = sociosJulio / horasJulio;
		sociosHoraAgosto = sociosAgosto / horasAgosto;
		sociosHoraSep = sociosSep / horasSep;
		sociosHoraOct = sociosOct / horasOct;
		sociosHoraNov = sociosNov / horasNov;
		sociosHoraDic = sociosDic / horasDic;

		Metricas enero = new Metricas(sociosEnero, montoEnero, horasEnero, sociosHoraEnero);
		Metricas febrero = new Metricas(sociosFebrero, montoFebrero, horasFebrero, sociosHoraFebrero);
		Metricas marzo = new Metricas(sociosMarzo, montoMarzo, horasMarzo, sociosHoraMarzo);
		Metricas abril = new Metricas(sociosAbril, montoAbril, horasAbril, sociosHoraAbril);
		Metricas mayo = new Metricas(sociosMayo, montoMayo, horasMayo, sociosHoraMayo);
		Metricas junio = new Metricas(sociosJunio, montoJunio, horasJunio, sociosHoraJunio);
		Metricas julio = new Metricas(sociosJulio, montoJulio, horasJulio, sociosHoraJulio);
		Metricas agosto = new Metricas(sociosAgosto, montoAgosto, horasAgosto, sociosHoraAgosto);
		Metricas septiembre = new Metricas(sociosSep, montoSep, horasSep, sociosHoraSep);
		Metricas octubre = new Metricas(sociosOct, montoOct, horasOct, sociosHoraOct);
		Metricas noviembre = new Metricas(sociosNov, montoOct, horasOct, sociosHoraNov);
		Metricas diciembre = new Metricas(sociosDic, montoDic, horasDic, sociosHoraDic);

		metricas.add(enero);
		metricas.add(febrero);
		metricas.add(marzo);
		metricas.add(abril);
		metricas.add(mayo);
		metricas.add(junio);
		metricas.add(julio);
		metricas.add(agosto);
		metricas.add(septiembre);
		metricas.add(octubre);
		metricas.add(noviembre);
		metricas.add(diciembre);

		return ResponseEntity.status(HttpStatus.OK).body(metricas);
	}

}
