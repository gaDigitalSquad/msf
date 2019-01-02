package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.academy.mfs.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {
	Zone findByZoneName(String zoneName);
	Zone findByZoneId(long zone_id);
}
