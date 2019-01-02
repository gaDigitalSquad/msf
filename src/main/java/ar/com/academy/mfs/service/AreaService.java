package ar.com.academy.mfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Area;
import ar.com.academy.mfs.repository.AreaRepository;

@Service("areaService")
public class AreaService {

	@Autowired
	AreaRepository areaRepository;
	
	public List<Area> findByZone_id(long zone_id){
		return areaRepository.findByZoneId(zone_id);
	}
	
	public Area findByType_of_area_id(long type_of_area_id) {
		return areaRepository.findByTypeOfAreaId(type_of_area_id);
	}
	
	public Area findByDescription(String description) {
		return areaRepository.findByDescription(description);
	}
	
}
