package ar.com.academy.mfs.controller;

import java.util.List;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.ZoneRequest;
import ar.com.academy.mfs.service.ZoneService;

@RestController
public class ZoneController {

	@Autowired
	ZoneService zoneService;
	
	@Autowired
	ZoneRepository zoneRepository;

	@GetMapping("/zones")
	public List<Zone> getZones() {
		List<Zone> zonas = zoneService.getAllZones();
		return zonas;
	}

	@GetMapping("/zone/{zone_name}")
	public Zone getZoneByZone_name(@PathVariable String zone_name) {
		return zoneService.getZoneByZone_name(zone_name);
	}

	@GetMapping("/zoneById/{zone_id}")
	public Zone getZoneByZone_id(@PathVariable int zone_id) {
		return zoneService.getZoneByZone_id(zone_id);
	}
	
	@PutMapping("/zone")
	public ResponseEntity<?> updateZone(@RequestBody ZoneRequest zr) {
		Zone z = zoneService.getZoneByZone_id(zr.getZone());
		z.setAmount(zr.getAmount());
		z.setTarget(zr.getTarget());
		Date newToDate = new Date (zr.getToDate().getTime() + 24*60*60*1000);
		z.setToDate(newToDate);
		Date newFromDate = new Date(zr.getFromDate().getTime() + 24*60*60*1000);
		z.setFromDate(newFromDate);
		if (z.getFromDate() == null) {
			
		}
		
		zoneRepository.save(z);
		return ResponseEntity.status(HttpStatus.OK).body(z);
	}
	
	@GetMapping("/zones-no-target")
	public List<Zone> getZonesWithoutTarget() {
		List<Zone> zones = zoneService.getZonesWithoutTarget();
		return zones;
	}
	
}
