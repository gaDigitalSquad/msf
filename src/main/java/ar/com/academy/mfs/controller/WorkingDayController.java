package ar.com.academy.mfs.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.request.WorkingDayRequest;
import ar.com.academy.mfs.service.WorkingDayService;

@RestController
public class WorkingDayController {

	@Autowired
	WorkingDayService workingDayService;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/workingday")
	@ResponseBody ResponseEntity postWorkingDay(@Valid @RequestBody WorkingDayRequest workingDayRequest) {
		return workingDayService.createWorkingDay(workingDayRequest);
	}
	
	
}
