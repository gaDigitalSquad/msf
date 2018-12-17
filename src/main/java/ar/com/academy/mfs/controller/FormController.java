package ar.com.academy.mfs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import ar.com.academy.mfs.model.Form;
import ar.com.academy.mfs.service.FormService;


public class FormController {
	@Autowired
	FormService formService;
	
	@PostMapping("/form")
	public Form createForm(@RequestBody Form inputForm) {
		Form form = formService.createForm(inputForm);
		return form;
	}
	
	
	@GetMapping("/form/{dni}")
	public Form getFormByDni(@PathVariable int dni) {
		return formService.getFormByDni(dni);
	}
}
