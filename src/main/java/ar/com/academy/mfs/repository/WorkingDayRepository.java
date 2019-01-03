package ar.com.academy.mfs.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.model.User;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer>{
	
	List<WorkingDay> findByWorkingDateBetween(Date start, Date end);
	List<WorkingDay> findByWorkingDateBetweenAndUser(Date start, Date end, User user);
}
