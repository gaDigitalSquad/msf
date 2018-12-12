package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByNameRole(String nameRole);
}
