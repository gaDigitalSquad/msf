package ar.com.academy.mfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Area;
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	List<Area> findByZoneId(long zoneId);
	Area findByTypeOfAreaId(long typeOfAreaId);
	Area findByDescription(String description);
}
