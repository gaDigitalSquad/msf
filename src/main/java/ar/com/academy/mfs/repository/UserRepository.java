package ar.com.academy.mfs.repository;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByLastname(String lastname);
	User findByUsername(String username);
	User findByDocumentTypeAndDocumentNumber(String documentType, int documentNumber);
	
	@Query(value="select * from msf.user where document_number = ?1", nativeQuery = true)
	User findByDocument(int dni);
	
	@Query(value = "select * from msf.user where group_number = ?1", nativeQuery = true)
	List<User> findSensFromGroup(int group_number);
}
