package ar.com.academy.mfs.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.model.User;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer>{
	
	List<WorkingDay> findByWorkingDateBetween(Date start, Date end);

	@Query(value = "select * from msf.working_day where user_id = ?1 and working_date between ?2 and ?3", nativeQuery = true)
	List<WorkingDay> findByWorkingDateBetweenAndUser(int user_id, Date from, Date to);
	
	@Query(value = "select * from msf.working_day where user_id = ?1 and working_date = ?2", nativeQuery = true)
	WorkingDay findByWorkingDateAndUser(int user_id, Date fecha);
	
	@Query(value = "select * from msf.working_day where group_number = ?1 and working_date between ?2 and ?3", nativeQuery = true)
	List<WorkingDay> findByGroupNumberAndDate(int group_number, Date start, Date end);
	
	@Query(value = "select * from msf.working_day where zone_id = ?1 and working_date between ?2 and ?3", nativeQuery = true)
	List<WorkingDay> findByZoneAndDate(int zone_id, Date start, Date end);
	
	@Query(value = "select * from msf.working_day where working_date between ?1 and ?2", nativeQuery = true)
	List<WorkingDay> findBetweenDate(Date start, Date end);
}
