package ar.com.academy.mfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.State;
import ar.com.academy.mfs.repository.StateRepository;

@Service("stateService")
public class StateService {
@Autowired 
StateRepository stateRepository;


	public State getStateByStateId(long stateId) {
		return stateRepository.findByStateId(stateId);
	}
	
	public State getStateByDescription(String description) {
		return stateRepository.findByDescription(description);
	}

	public List<State> getAllStates() {
		return stateRepository.findAll();
	}
	
}
