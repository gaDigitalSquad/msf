package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.State;

@Repository
public interface StateRepository extends JpaRepository<State,Integer>{
	State findByStateId(long stateId);
	State findByDescription(String description);
}
