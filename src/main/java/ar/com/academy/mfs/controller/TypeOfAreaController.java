package ar.com.academy.mfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.TypeOfArea;
import ar.com.academy.mfs.service.TypeOfAreaService;

@RestController
public class TypeOfAreaController {
	@Autowired 
	TypeOfAreaService typeOfAreaService;
	
	@GetMapping("/type_of_area/{type_of_area_id}")
	public TypeOfArea getByType_of_area_id(@PathVariable long type_of_area_id) {
		return typeOfAreaService.getByType_of_area_id(type_of_area_id);
	}
	
	@GetMapping("/type_of_area/{description}")
	public TypeOfArea getByDescription(@PathVariable String description) {
		return typeOfAreaService.getByDescription(description);
	}
}
