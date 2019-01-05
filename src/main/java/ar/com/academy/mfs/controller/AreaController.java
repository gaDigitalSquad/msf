package ar.com.academy.mfs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Area;
import ar.com.academy.mfs.service.AreaService;

@RestController
public class AreaController {

	@Autowired 
	AreaService areaService;
	
	
	@GetMapping("/areas/{zone_id}")
	public List<Area> getAllAreasByZoneId(@PathVariable long zone_id){
		return areaService.findByZone_id(zone_id);
	}
	
	
	@GetMapping("/area/{type_of_area_id}")
	public Area getAreaByTypeOfAreaId(@PathVariable int type_of_area_id){
		return areaService.findByType_of_area_id(type_of_area_id);
	}
	
	
	@GetMapping("/area/{description}")
	public Area getAreaByDescription(@PathVariable String description){
		return areaService.findByDescription(description);
	}
}
