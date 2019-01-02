package ar.com.academy.mfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.TypeOfArea;
import ar.com.academy.mfs.repository.TypeOfAreaRepository;

@Service("typeOfAreaService")
public class TypeOfAreaService {
	
	@Autowired
	TypeOfAreaRepository typeOfAreaRepository;
	
	public TypeOfArea getByType_of_area_id(long type_of_area_id) {
		return typeOfAreaRepository.findByTypeOfAreaId(type_of_area_id);
	}
	
	public TypeOfArea getByDescription(String description) {
		return typeOfAreaRepository.findByDescription(description);
	}

}
