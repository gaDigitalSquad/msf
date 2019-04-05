package ar.com.academy.mfs.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.UserState;

@Repository
public interface UserStateRepository extends JpaRepository<UserState,Integer> {
		
	
	UserState findByUserStateId(int UserStateId);
	UserState findByStateId (long stateId);
	
	@Transactional
	@Modifying
	@Query(value = "update msf.user set user_state_id = ?2 where user_id = ?1", nativeQuery = true)
	int setUserStateId(int user_id, int id);

	@Query(value = "select * from msf.user_state where state_id <> 1  and state_id <> 5 "
			+ "and state_id <> 16 and date_part('year',from_date)= ?1", nativeQuery = true)
	List<UserState> findbyLicenseInPeriod(int year);
	
	
}
