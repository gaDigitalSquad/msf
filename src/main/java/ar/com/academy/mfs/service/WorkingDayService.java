package ar.com.academy.mfs.service;

import java.text.DecimalFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.WorkingDayRepository;
import ar.com.academy.mfs.request.DateRequest;
import ar.com.academy.mfs.request.WorkingDayRequest;
import ar.com.academy.mfs.response.Metricas;

@Service("workingDayService")
public class WorkingDayService {

	@Autowired
	WorkingDayRepository workingDayRepository;
	@Autowired
	UserRepository userRepository;
	
//	public ResponseEntity createWorkingDay(WorkingDayRequest workingDayRequest) {
//		Optional<User> supervisor = userRepository.findById(workingDayRequest.getSupervisor());
//		Optional<User> user = userRepository.findById(workingDayRequest.getUser());
//		if(supervisor == null || user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuarios no validos");
//		WorkingDay workingDay = new WorkingDay(supervisor.get(), user.get(), workingDayRequest.isPresent(), workingDayRequest.getWorkingDate(), workingDayRequest.getFrom_hour(), workingDayRequest.getTo_hour(), workingDayRequest.getZone(), workingDayRequest.getAmountOfNewPartners(), workingDayRequest.getTotalAmount(), workingDayRequest.getObservations(), workingDayRequest.isCompleted());
//		workingDayRepository.save(workingDay);
//		return ResponseEntity.status(HttpStatus.OK).body(workingDay);
//	}
	
	public Metricas getMetricasUsuario(int user_id, DateRequest dateRequest) {
		
		User user = userRepository.findById(user_id).get();
		
		
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
		
		return m;
	}
	
	public WorkingDay getWorkingDay(int user_id, DateRequest date){
		   return workingDayRepository.findByWorkingDateAndUser(user_id, date.getTo());
		}

	public WorkingDay createWorkingDay(WorkingDayRequest inputWorkingDay) {
		// Falta verificación de id de usuarios
	
		User lider = userRepository.findById(inputWorkingDay.getSupervisor()).get();
		int zone = lider.getZone_id();
		
		int hoursWorked = 0;
		
		// Cálculo de horas trabajadas, solo si las horas son != null
		if (inputWorkingDay.getTo_hour() != null && inputWorkingDay.getFrom_hour() != null) {
			DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
			long diff = inputWorkingDay.getTo_hour().getTime() - inputWorkingDay.getFrom_hour().getTime();
			hoursWorked = (int) (diff / (60 * 60 * 1000));
		}
		
		WorkingDay WorkingDayToSave = new WorkingDay(lider.getUser_id(),
										inputWorkingDay.getUser(),
										inputWorkingDay.isPresent(),
										inputWorkingDay.getWorkingDate(),
										inputWorkingDay.getFrom_hour(),
										inputWorkingDay.getTo_hour(),
										zone,
										inputWorkingDay.getAmountOfNewPartners(),
										inputWorkingDay.getTotalAmount(),
										inputWorkingDay.getObservations(),
										hoursWorked);
		WorkingDay workingDaySaved = workingDayRepository.save(WorkingDayToSave);
		return workingDaySaved;
	}

}
