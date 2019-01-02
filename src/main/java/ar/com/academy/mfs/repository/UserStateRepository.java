package ar.com.academy.mfs.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.UserState;

@Repository
public interface UserStateRepository extends JpaRepository<UserState,Integer> {
		
	
	UserState findByUserStateId(long UserStateId);
	UserState findByStateId (long stateId);
	

}
