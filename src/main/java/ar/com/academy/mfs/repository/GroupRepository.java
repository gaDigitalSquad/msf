package ar.com.academy.mfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.academy.mfs.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	List<Group> findBySupervisor(long supervisor);

}
