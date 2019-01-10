package ar.com.academy.mfs.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.ZoneRepository;


@Service("zoneService")
public class ZoneService {
	@Autowired
	ZoneRepository zoneRepository;
	
	public List<Zone> getAllZones() {
		return zoneRepository.findAll();
	}
	
	public Zone getZoneByZone_name(String zone_name) {
		return zoneRepository.findByZoneName(zone_name);
	}

	public Zone getZoneByZone_id(int zone_id) {
		return zoneRepository.findByZoneId(zone_id);
	}

}
