package ar.com.academy.mfs.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.model.User;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	
	List<Group> findBySupervisor(long supervisor);
	
	@Query(value = "select group_number from msf.group order by group_number desc limit 1", nativeQuery = true)
	Integer findLastGroupNumber();

	@Query(value = "select supervised_id from msf.group where supervisor_id = ?1", nativeQuery = true)
	List<Integer> findMySens(int supervisor_id);
	
	@Query(value = "select * from msf.group where group_number = ?1 limit 1", nativeQuery = true)
	Group findByGroupNumber(int group_number);

	@Query(value = "select * from msf.group where supervisor_id = ?1 limit 1", nativeQuery = true)
	Group findUserGroup(int supervisor_id);
	
	@Query(value = "select group_number from msf.group where zone_id = ?1", nativeQuery = true)
	Set<Integer> findAllbyZoneId(int zone_id);

	@Query(value = "select zone_id from msf.group", nativeQuery = true)
	Set<Integer> findAllZones();
	
	

}
