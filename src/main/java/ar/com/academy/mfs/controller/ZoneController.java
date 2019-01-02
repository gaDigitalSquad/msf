package ar.com.academy.mfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.service.ZoneService;

@RestController
public class ZoneController {

	@Autowired
	ZoneService zoneService;
	
	@GetMapping("/zone/{zone_name}")
	public Zone getZoneByZone_name(@PathVariable String zone_name) {
		return zoneService.getZoneByZone_name(zone_name);
	}
	
	@GetMapping("/zone/{zone_id}")
	public Zone getZoneByZone_id(@PathVariable long zone_id) {
		return zoneService.getZoneByZone_id(zone_id);
	}
}
