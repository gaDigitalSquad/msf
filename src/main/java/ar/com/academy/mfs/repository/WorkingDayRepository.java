package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.WorkingDay;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer>{
	
	
}
