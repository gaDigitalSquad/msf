package ar.com.academy.mfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Target;
import ar.com.academy.mfs.service.TargetService;

@RestController
public class TargetController {

	@Autowired
	TargetService targetService;
	
	@GetMapping("/target/{target_id}")
	public Target getTargetByTarget_id(@PathVariable long target_id) {
		return targetService.getTargetByTarget_id(target_id);
	}
	
}
