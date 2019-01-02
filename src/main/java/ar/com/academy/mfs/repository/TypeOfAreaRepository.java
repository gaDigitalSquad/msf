package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.TypeOfArea;

@Repository
public interface TypeOfAreaRepository extends JpaRepository<TypeOfArea, Long>  {
	TypeOfArea findByDescription(String description);
	TypeOfArea findByTypeOfAreaId(long typeOfAreaId);
}
