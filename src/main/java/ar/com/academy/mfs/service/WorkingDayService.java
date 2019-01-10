package ar.com.academy.mfs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

}
