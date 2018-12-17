package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
	Form findByDni(int dni);
}
