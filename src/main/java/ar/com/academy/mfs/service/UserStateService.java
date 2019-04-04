package ar.com.academy.mfs.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
	    .collect(Collectors.groupingBy(UserState::getMonthFrom, Collectors.counting()));
		
		userStates.forEach((u,x) -> {
			listResumenes.add(new ResumenLicencia(String.valueOf(LocalDate.now().getYear()), x.intValue(),u.toString() ));
		});
		return getMonthsCompleted(listResumenes);
	}

	private List<ResumenLicencia> getMonthsCompleted(List<ResumenLicencia> listResumenes) {
		ArrayList<Integer> meses = new ArrayList<>(12) ;
		meses.add(1);
		meses.add(2);
		meses.add(3);
		meses.add(4);
		meses.add(5);
		meses.add(6);
		meses.add(7);
		meses.add(8);
		meses.add(9);
		meses.add(10);
		meses.add(11);
		meses.add(12);
		
		for (int mes : meses )
		{
			if(!listResumenes.stream().anyMatch(p -> p.getMes().equals(String.valueOf(mes) )));
			{
				listResumenes.add(new ResumenLicencia(String.valueOf(LocalDate.now().getYear()),0,String.valueOf(mes)));
			}
		}
	    listResumenes.sort(Comparator.comparing(ResumenLicencia::getAnio));
	    return listResumenes;
	}
	
}
