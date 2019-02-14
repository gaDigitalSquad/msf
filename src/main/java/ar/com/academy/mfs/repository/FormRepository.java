package ar.com.academy.mfs.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
	Optional<Form> findByDni(int dni);
	
	@Query(value = "select * from msf.form where completed_by_user_id = ?1", nativeQuery = true)
	Optional<List<Form>> findByCompleted_by_user_id(int user_id);
	
	@Query(value = "select * from msf.form where completed_by_user_id =?1 and form_date = ?2", nativeQuery = true)
	List<Form> findByUserAndDate(int user, Date date);
}
