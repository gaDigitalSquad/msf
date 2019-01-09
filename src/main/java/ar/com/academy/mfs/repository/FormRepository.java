package ar.com.academy.mfs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
	Optional<Form> findByDni(int dni);
}
