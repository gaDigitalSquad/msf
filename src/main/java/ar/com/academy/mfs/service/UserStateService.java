package ar.com.academy.mfs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.repository.UserStateRepository;

@Service("userStateService")
public class UserStateService {
	@Autowired 
	UserStateRepository userStateRepository;
	
	public UserState findByUserStateId(long UserStateId) {
		return userStateRepository.findByUserStateId(UserStateId);
	}
	
	public UserState findByStateId (long stateId) {
		return userStateRepository.findByStateId(stateId);
	
	}

}
