package ar.com.academy.mfs.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.com.academy.mfs.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	
//	List<Group> findBySupervisor_id(int supervisor);
	@Query(value = "select * from msf.group where group_number = ?1", nativeQuery = true)
	List<Group> findAllByGroup_number(int group_number);
	
	@Query(value = "select group_number from msf.group order by group_number desc limit 1", nativeQuery = true)
	Integer findLastGroupNumber();

	@Query(value = "select supervised_id from msf.group where supervisor_id = ?1 and active = true", nativeQuery = true)
	List<Integer> findMySens(int supervisor_id);
	
	@Query(value = "select * from msf.group where group_number = ?1 limit 1", nativeQuery = true)
	Group findByGroupNumber(int group_number);

	@Query(value = "select * from msf.group where supervisor_id = ?1 limit 1", nativeQuery = true)
	Group findUserGroup(int supervisor_id);
	
	@Query(value = "select group_number from msf.group where zone_id = ?1", nativeQuery = true)
	Set<Integer> findAllbyZoneId(int zone_id);

	@Query(value = "select zone_id from msf.group", nativeQuery = true)
	Set<Integer> findAllZones();

	@Query(value = "select distinct on (group_number) group_id, supervisor_id, supervised_id, zone_id, from_date, to_date, group_number, turn, active from msf.group where zone_id = ?1", nativeQuery = true)
	List<Group> findByZone(int zone_id);
	
	@Query(value = "select distinct on (group_number) group_id, supervisor_id, supervised_id, zone_id, from_date, to_date, group_number, turn, active from msf.group where zone_id = ?1 and turn = ?2", nativeQuery = true)
	List<Group> findByZoneAndTurn(int zone_id, String turn);
	
	@Query(value = "select supervisor_id from msf.group where group_number = ?1 limit 1", nativeQuery = true)
	int findLeaderByGroupNumber(int group_number);
	
	@Query(value = "select * from msf.group where supervisor_id = ?1", nativeQuery = true)
	List<Group> getGroupsByLider(int supervisor_id);
	
	@Query(value = "select * from msf.group where supervised_id = ?1 and active = true", nativeQuery = true)
	Group getSens(int supervised_id);

	@Query(value = "select * from msf.group where supervised_id = ?1 and active = true", nativeQuery = true)
	Group getSensByGroupNumber(int user_id);
	
}
