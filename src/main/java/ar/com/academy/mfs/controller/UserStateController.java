package ar.com.academy.mfs.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.service.UserStateService;

@RestController
public class UserStateController {
	@Autowired
	UserStateService userStateService;
	
	@GetMapping("/user_state/{state_id}")
	public UserState getUserStateByStateId(@PathVariable long state_id) {
		return userStateService.findByStateId(state_id);
	}
	
	@GetMapping("/user_state/{user_state_id}")
	public UserState getUserStateByUserStateId(@PathVariable long user_state_id) {
		return userStateService.findByUserStateId(user_state_id);
	}
	
}
