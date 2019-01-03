package ar.com.academy.mfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.repository.RoleRepository;

@Service("roleService")
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
}
