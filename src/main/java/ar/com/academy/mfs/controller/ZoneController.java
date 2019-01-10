package ar.com.academy.mfs.controller;

import java.util.List;

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
	
	@GetMapping("/zones")
	public List<Zone> getAllUsers() {
		return zoneService.getAllZones();
	}
	
	@GetMapping("/zone/{zone_name}")
	public Zone getZoneByZone_name(@PathVariable String zone_name) {
		return zoneService.getZoneByZone_name(zone_name);
	}
	
	@GetMapping("/zoneById/{zone_id}")
	public Zone getZoneByZone_id(@PathVariable int zone_id) {
		return zoneService.getZoneByZone_id(zone_id);
	}
}
