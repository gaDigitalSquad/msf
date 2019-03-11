package ar.com.academy.mfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.com.academy.mfs.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {
	Zone findByZoneName(String zoneName);
	Zone findByZoneId(int zone_id);
	
	@Query(value = "select * from msf.zone where target = null or target = 0", nativeQuery = true)
	List<Zone> findZonesWithoutTarget();
}
