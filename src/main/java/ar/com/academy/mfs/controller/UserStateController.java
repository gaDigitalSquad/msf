package ar.com.academy.mfs.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.repository.UserStateRepository;
import ar.com.academy.mfs.request.UserStateRequest;
import ar.com.academy.mfs.service.UserService;
import ar.com.academy.mfs.service.UserStateService;

@RestController
public class UserStateController {
	@Autowired
	UserStateService userStateService;
	
	@Autowired
	UserStateRepository userStateRepository;
	
	@Autowired
	UserService userService;
	
	// Crear licencia para un usuario
	@PostMapping("/license/{user_id}")
	public ResponseEntity<?> createUserState(@RequestBody UserStateRequest userStateRequest, @PathVariable int user_id) {
		User u = userService.getUserById(user_id);
		UserState userState = new UserState(userStateRequest.getFromDate(),
											userStateRequest.getToDate(), 
											userStateRequest.getDescription());
		userStateRepository.save(userState);
		System.out.println(userState.getUserStateId());
		int id = userState.getUserStateId();
		int r = userStateRepository.setUserStateId(u.getUser_id(), id);
		return ResponseEntity.status(HttpStatus.OK).body(userState);	
	}
	
	@GetMapping("/user_state/{state_id}")
	public UserState getUserStateByStateId(@PathVariable long state_id) {
		return userStateService.findByStateId(state_id);
	}
	
	@GetMapping("/user_state/{user_state_id}")
	public UserState getUserStateByUserStateId(@PathVariable long user_state_id) {
		return userStateService.findByUserStateId(user_state_id);
	}
	
}
