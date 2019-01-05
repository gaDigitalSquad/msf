package ar.com.academy.mfs.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.security.JWTAuthenticationFilter;
import ar.com.academy.mfs.service.UserService;
import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.request.UserRequest;

@RestController
public class UserController {
	@Autowired
	UserRepository user_repository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserService user_service;
	@Autowired
	RoleRepository role_repository;
	
	
	public UserController(UserRepository user_repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.user_repository = user_repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping("/users") //para guardar nuevos usuarios 
	@ResponseBody ResponseEntity postUser(@Valid @RequestBody UserRequest userRequest) {
		if(user_repository.findByUsername(userRequest.getUsername()) != null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
		
		Role role = role_repository.findByRoleName(userRequest.getRole());
		if(role == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
		
		User user = new User(userRequest.getUsername(), 
							userRequest.getPassword(), 
							userRequest.getFirstname(), 
							userRequest.getLastname(), 
							role.getRoleId(), 
							userRequest.getPhoneNumber(), 
							userRequest.getDocumentType(), 
							userRequest.getDocumentNumber());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user_repository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return user_service.getAllUsers();
	}
	
	@GetMapping("/users/{username}")
	public User getUser(@PathVariable String username) {
		return user_service.findByUsername(username);
	}
	
	@PostMapping("/signup") //devolver el token, cuando crea un nuevo usuario
	public String signUp(@RequestBody User signUpUser) {
	  if (this.user_service.usernameExists(signUpUser.getUsername())) {
	    return "EXISTS";
	  } signUpUser.setPassword(bCryptPasswordEncoder.encode(signUpUser.getPassword()));
	  this.user_service.save(signUpUser);
	  JWTAuthenticationFilter jWTAuthenticationFilter= new JWTAuthenticationFilter() ;
	  return jWTAuthenticationFilter.createToken(signUpUser.getUsername());
	}
	
	@GetMapping("/leadersWithoutGroup")
	public List<User> getLeadersWithoutGroup() {
		return user_service.getLeadersWithoutGroup();
	}
	
	@GetMapping("/sensWithoutGroup")
	public List<User> getSensWithoutGroup() {
		return user_service.getSensWithoutGroup();
	}
	
	// Obtener los sensibilizadores de un determinado lider
	
	@GetMapping("/getMySens/{supervisor_id}")
	public List<User> getMySens(@PathVariable int supervisor_id) {
		return user_service.getMySens(supervisor_id);
	}
	
	@PutMapping("/users/{user_id}")
	@ResponseBody ResponseEntity<?> putUser(@PathVariable int user_id, @RequestBody UserRequest userRequest) {
		User user = user_repository.findById(user_id).get();
		if(updateUser(userRequest, user)) return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}
	
	@PatchMapping("/users/{user_id}")
	@ResponseBody ResponseEntity<?> patchUser(@PathVariable int user_id, @RequestBody UserRequest userRequest) {
		User user = user_repository.findById(user_id).get();
		if(updateUser(userRequest, user)) return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}
	
	private boolean updateUser(@RequestBody UserRequest userRequest, User user) {
		if(user != null) {
			if(userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
			if(userRequest.getPassword() != null) user.setPassword(userRequest.getPassword());
			if(userRequest.getFirstname() != null) user.setFirstname(userRequest.getFirstname());
			if(userRequest.getLastname() != null) user.setLastname(userRequest.getLastname());
			if(userRequest.getRole() != null) user.setRole_id(role_repository.findByRoleName(userRequest.getRole()).getRoleId());
			if(userRequest.getPhoneNumber() > 0) user.setPhoneNumber(userRequest.getPhoneNumber());
			if(userRequest.getDocumentType() != null) user.setDocumentType(userRequest.getDocumentType());
			if(userRequest.getDocumentNumber() > 0) user.setDocumentNumber(userRequest.getDocumentNumber());
			user_repository.save(user);
			return true;
		}
		return false;
	}
}
