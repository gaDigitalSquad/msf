package ar.com.academy.mfs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.WorkingDayRepository;
import ar.com.academy.mfs.request.WorkingDayRequest;

@Service("workingDayService")
public class WorkingDayService {

	@Autowired
	WorkingDayRepository workingDayRepository;
	@Autowired
	UserRepository userRepository;
	
	public ResponseEntity createWorkingDay(WorkingDayRequest workingDayRequest) {
		Optional<User> supervisor = userRepository.findById(workingDayRequest.getSupervisor());
		Optional<User> user = userRepository.findById(workingDayRequest.getUser());
		if(supervisor == null || user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuarios no validos");
		WorkingDay workingDay = new WorkingDay(supervisor.get(), user.get(), workingDayRequest.isPresent(), workingDayRequest.getWorkingDate(), workingDayRequest.getFrom_hour(), workingDayRequest.getTo_hour(), workingDayRequest.getZone(), workingDayRequest.getAmountOfNewPartners(), workingDayRequest.getTotalAmount(), workingDayRequest.getObservations(), workingDayRequest.isCompleted());
		workingDayRepository.save(workingDay);
		return ResponseEntity.status(HttpStatus.OK).body(workingDay);
	}

}
