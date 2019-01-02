package ar.com.academy.mfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Target;
import ar.com.academy.mfs.repository.TargetRepository;

@Service("targetService")
public class TargetService {
	
	@Autowired
	TargetRepository targetRepository;
	
	public Target getTargetByTarget_id(long target_id) {
		return targetRepository.findByTargetId(target_id);
	}

}
