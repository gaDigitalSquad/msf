package ar.com.academy.mfs.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.repository.UserStateRepository;
import ar.com.academy.mfs.response.ResumenLicencia;

@Service("userStateService")
public class UserStateService {
	@Autowired 
	UserStateRepository userStateRepository;
	
	public UserState findByUserStateId(int UserStateId) {
		return userStateRepository.findByUserStateId(UserStateId);
	}
	
	public UserState findByStateId (long stateId) {
		return userStateRepository.findByStateId(stateId);
	
	}
	 
	public  List<ResumenLicencia> getmetricasLicencias()
	{
		List<ResumenLicencia> listResumenes = new ArrayList<>();
		Map<Integer,Long > userStates =  userStateRepository.findbyLicenseInPeriod(LocalDate.now().getYear())
				.stream()
	    .collect(Collectors.groupingBy(UserState::getYearFrom, Collectors.counting()));
		
		userStates.forEach((u,x) -> {
			listResumenes.add(new ResumenLicencia(u.toString(), x.intValue()));
		});
		return listResumenes;
		
	}
	
}
