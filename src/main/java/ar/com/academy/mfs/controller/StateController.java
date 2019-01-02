package ar.com.academy.mfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.State;
import ar.com.academy.mfs.service.StateService;

@RestController
public class StateController {
	@Autowired
	StateService stateService;
	
	@GetMapping("/state/id/{state_id}")
	public State getStateByStateId(@PathVariable long state_id) {
		return stateService.getStateByStateId(state_id);
	}
	
	@GetMapping("/state/{description}")
	public State getStateByDescription(@PathVariable String description) {
		return stateService.getStateByDescription(description);
	}
	
}
