package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.academy.mfs.model.Target;

public interface TargetRepository  extends JpaRepository<Target, Integer>{
	public Target findByTargetId(long targetId);
	
}
