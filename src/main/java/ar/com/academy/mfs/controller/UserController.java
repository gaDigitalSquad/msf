package ar.com.academy.mfs.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.DocumentTypeAndNumberRequest;
import ar.com.academy.mfs.request.PasswordRequest;
import ar.com.academy.mfs.request.UserRequest;
import ar.com.academy.mfs.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserRepository user_repository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserService user_service;
	@Autowired
	private RoleRepository role_repository;
	@Autowired
	private ZoneRepository zoneRepository;
	@Autowired
	private JavaMailSender emailSender;

	public UserController(UserRepository user_repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.user_repository = user_repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Creación de un usuario
	 * 
	 * @param userRequest
	 * @return
	 */

	@PostMapping("/users")
	@ResponseBody
	ResponseEntity<?> postUser(@Valid @RequestBody UserRequest userRequest) {
		if (user_repository.findByUsername(userRequest.getUsername()) != null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");

		Role role = role_repository.findByRoleName(userRequest.getRole());
		if (role == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");

		Zone zone = zoneRepository.findById(userRequest.getZone_id()).get();
		if (zone == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no válida");

		User user = new User(userRequest.getUsername(), userRequest.getPassword(), userRequest.getFirstname(),
				userRequest.getLastname(), role.getRoleId(), userRequest.getPhoneNumber(),
				userRequest.getDocumentType(), userRequest.getDocumentNumber(), userRequest.getZone_id());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user_repository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	/**
	 * Obtener todos los usuarios existentes
	 * 
	 * @return List<User>
	 */

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return user_service.getAllUsers();
	}

	/**
	 * Saber si un dni ya se encuentra registrado
	 * 
	 * @param dni
	 * @return true si está disponible, false de lo contrario
	 */
	@PostMapping("/users/check-document")
	public ResponseEntity<?> isDocumentAvailable(@RequestBody DocumentTypeAndNumberRequest document) {
		boolean res = user_service.isDocumentAvailable(document);
		if (res == true) {
			return ResponseEntity.status(HttpStatus.OK).body("No existe");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("OK");
		}
	}

	@GetMapping("/users/{username}")
	public User getUser(@PathVariable String username) {
		User u = user_service.findByUsername(username);
		System.out.println(u.isCompleted());
		return u;
	}

	@DeleteMapping("/user/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username) {
		User user = user_service.findByUsername(username);
		if (user != null) {
			user_service.deleteUser(user);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido eliminar el usuario.");
		}
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

	// Obtener los sensibilizadores de un determinado lider sín el líder

	@GetMapping("/getMySensNoLider/{supervisor_id}")
	public List<User> getMySensNoLider(@PathVariable int supervisor_id) {
		return user_service.getMySensWithoutLider(supervisor_id);
	}

	// Obtengo los sensibilizadores de un determinado grupo por número de grupo

	@GetMapping("/getGroupSens/{group_number}")
	public List<User> getGroupSens(@PathVariable int group_number) {
		return user_service.getGroupSens(group_number);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/update-user/{user_id}")
	@ResponseBody
	ResponseEntity<?> putUser(@PathVariable int user_id, @RequestBody UserRequest userRequest) {
		User user = user_repository.findById(user_id).get();
		if (updateUser(userRequest, user))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}

	private boolean updateUser(@RequestBody UserRequest userRequest, User user) {
		if (user != null) {
			if (userRequest.getUsername() != null)
				user.setUsername(userRequest.getUsername());
			if (userRequest.getFirstname() != null)
				user.setFirstname(userRequest.getFirstname());
			if (userRequest.getLastname() != null)
				user.setLastname(userRequest.getLastname());
			if (userRequest.getRole() != null)
				user.setRole_id(role_repository.findByRoleName(userRequest.getRole()).getRoleId());
			if (userRequest.getPhoneNumber() > 0)
				user.setPhoneNumber(userRequest.getPhoneNumber());
			if (userRequest.getDocumentType() != null)
				user.setDocumentType(userRequest.getDocumentType());
			if (userRequest.getDocumentNumber() > 0)
				user.setDocumentNumber(userRequest.getDocumentNumber());
			if (userRequest.getZone_id() > 0)
				user.setZone_id(userRequest.getZone_id());
			user_repository.save(user);
			return true;
		}
		return false;
	}

	/**
	 * Es un borrado lógico
	 * 
	 * @param user_id
	 * @return
	 */
	@DeleteMapping("/delete-user/{user_id}")
	@ResponseBody
	ResponseEntity<?> deleteUser(@PathVariable int user_id) {
		User user = user_repository.findById(user_id).get();
		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe usuario con id: " + user_id);
		} else {
			user.setActive(false);
			/* Si está en un grupo, se lo desvincula del mismo */
			if (user.getGroup_number() != 0) {

			}
			user_repository.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
	}
	
	@PostMapping("/findUser")
	@ResponseBody ResponseEntity<?> getUser(@RequestBody DocumentTypeAndNumberRequest documentTypeAndNumber) {
		User user = findByDocumentTypeAndDocumentNumber(documentTypeAndNumber.getDocumentType(), documentTypeAndNumber.getDocumentNumber());
		if (user == null ) return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario no encontrado");
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	
	@PostMapping("/findUserByLastname")
	@ResponseBody ResponseEntity<?> getUserByLastname(@RequestBody UserRequest lastname) {
		List<User> users = findByLastname(lastname.getLastname());
		if ( users.isEmpty() ) return ResponseEntity.status(HttpStatus.CONFLICT).body("No se encontraron usuarios con " + lastname.getLastname());
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@GetMapping("/exist-document/{documentType}/{documentNumber}")
	@ResponseBody
	ResponseEntity<?> getUser(@PathVariable String documentType, @PathVariable int documentNumber) {
		User user = findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
		if (user == null)
			return ResponseEntity.status(HttpStatus.OK).body("Disponible");
		return ResponseEntity.status(HttpStatus.OK).body("No disponible");
	}

	private User findByDocumentTypeAndDocumentNumber(String documentType, int documentNumber) {
		return user_repository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
	}
	
	private List<User> findByLastname(String lastname) {
		return user_repository.findByLastnameContainingIgnoreCase(lastname);
	}

	@PostMapping("/change-password/{user_id}")
	@ResponseBody
	ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest, @PathVariable int user_id) {
		User user = user_repository.findById(user_id).get();
		if (!bCryptPasswordEncoder.matches(passwordRequest.getActualPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("La contraseña actual es incorrecta.");
		} else {
			user_service.changeUserPassword(user, passwordRequest.getNewPassword());
			return ResponseEntity.status(HttpStatus.OK).body("La contraseña fue modificada con éxito.");
		}
	}
	
	@PostMapping("/change-password-email/{email}")
	@ResponseBody
	ResponseEntity<?> changePasswordByEmail(@RequestBody PasswordRequest passwordRequest, @PathVariable String email) {
		User user = user_repository.findByUsername(email);
		user_service.changeUserPassword(user, passwordRequest.getNewPassword());
		return ResponseEntity.status(HttpStatus.OK).body("La contraseña fue modificada con éxito.");
	}

	@GetMapping("/reset-password/{email}")
	public void resetPassword(@PathVariable String email) {
	try
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("MSF - Reestablecer contraseña");
		message.setText("Para reestablecer su contraseña ingrese al siguiente link: https://msf-frontend.herokuapp.com/reset-password");
		emailSender.send(message);
	}catch(
	Exception e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	@GetMapping("/exist-user/{email}")
	public ResponseEntity<?> dniVerification(@PathVariable String email) {
	User user = user_repository.findByUsername(email);
	if (user != null)
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	else
		return ResponseEntity.status(HttpStatus.OK).body("No existe");
	}
	
	@GetMapping("/find-user/{id}")
	public ResponseEntity<?> findUserById(@PathVariable int id) {
		User user = user_repository.findById(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

}
