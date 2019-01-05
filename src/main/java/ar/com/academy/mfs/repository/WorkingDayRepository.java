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
}
