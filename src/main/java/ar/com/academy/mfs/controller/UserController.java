package ar.com.academy.mfs.controller;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.security.JWTAuthenticationFilter;
import ar.com.academy.mfs.security.SecurityService;
import ar.com.academy.mfs.service.UserService;
import ar.com.academy.mfs.request.DocumentTypeAndNumberRequest;
import ar.com.academy.mfs.request.PasswordRequest;
import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.request.UserRequest;
import ar.com.academy.mfs.response.GenericResponse;

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
	private JavaMailSender mailSender;
	@Autowired
	private MessageSource messages;
	@Autowired
	private SecurityService securityService;
	
	public UserController(UserRepository user_repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.user_repository = user_repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	// Creación de un usuario
	
	@PostMapping("/users") 
	@ResponseBody ResponseEntity postUser(@Valid @RequestBody UserRequest userRequest) {
		if(user_repository.findByUsername(userRequest.getUsername()) != null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
		
		Role role = role_repository.findByRoleName(userRequest.getRole());
		if(role == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
		
		Zone zone = zoneRepository.findById(userRequest.getZone_id()).get();
		if(zone == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Zona no válida");
		
		User user = new User(userRequest.getUsername(), 
							userRequest.getPassword(), 
							userRequest.getFirstname(), 
							userRequest.getLastname(), 
							role.getRoleId(), 
							userRequest.getPhoneNumber(), 
							userRequest.getDocumentType(), 
							userRequest.getDocumentNumber(),
							userRequest.getZone_id());
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
		User u = user_service.findByUsername(username);
		System.out.println(u.isCompleted());
		return u;
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
	
	// Obtengo los sensibilizadores de un determinado grupo por número de grupo
	
	@GetMapping("/getGroupSens/{group_number}")
	public List<User> getGroupSens(@PathVariable int group_number) {
		return user_service.getGroupSens(group_number);
	}
	
	// Actualizar usuario
	
	@CrossOrigin(origins = "*")
	@PutMapping("/update-user/{user_id}")
	@ResponseBody ResponseEntity<?> putUser(@PathVariable int user_id, @RequestBody UserRequest userRequest) {
		User user = user_repository.findById(user_id).get();
		if(updateUser(userRequest, user)) return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}
	
	
	@PatchMapping("/update-user/{user_id}")
	@ResponseBody ResponseEntity<?> patchUser(@Valid @PathVariable int user_id, @RequestBody UserRequest userRequest) {
		User user = user_repository.findById(user_id).get();
		if(updateUser(userRequest, user)) return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
	}
	
	private boolean updateUser(@RequestBody UserRequest userRequest, User user) {
		if(user != null) {
			if(userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
//			if(userRequest.getPassword() != null) user.setPassword(userRequest.getPassword());
			if(userRequest.getFirstname() != null) user.setFirstname(userRequest.getFirstname());
			if(userRequest.getLastname() != null) user.setLastname(userRequest.getLastname());
			if(userRequest.getRole() != null) user.setRole_id(role_repository.findByRoleName(userRequest.getRole()).getRoleId());
			if(userRequest.getPhoneNumber() > 0) user.setPhoneNumber(userRequest.getPhoneNumber());
			if(userRequest.getDocumentType() != null) user.setDocumentType(userRequest.getDocumentType());
			if(userRequest.getDocumentNumber() > 0) user.setDocumentNumber(userRequest.getDocumentNumber());
			if(userRequest.getZone_id() > 0) user.setZone_id(userRequest.getZone_id());
			user_repository.save(user);
			return true;
		}
		return false;
	}
	
	@PostMapping("/findUser")
	@ResponseBody ResponseEntity<?> getUser(@RequestBody DocumentTypeAndNumberRequest documentTypeAndNumber) {
		User user = findByDocumentTypeAndDocumentNumber(documentTypeAndNumber.getDocumentType(), documentTypeAndNumber.getDocumentNumber());
		if (user == null ) return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario no encontrado");
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	private User findByDocumentTypeAndDocumentNumber(String documentType, int documentNumber) {
		return user_repository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
	}
	
	@PostMapping("/change-password/{user_id}")
	@ResponseBody ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest, @PathVariable int user_id) {
		User user = user_repository.findById(user_id).get();
		if (!bCryptPasswordEncoder.matches(passwordRequest.getActualPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("La contraseña actual es incorrecta.");
		} else {
			user_service.changeUserPassword(user, passwordRequest.getNewPassword());
			return ResponseEntity.status(HttpStatus.OK).body("La contraseña fue modificada con éxito.");
		}
	}
	
//	@PostMapping("/changeManualPassword")
//	public String changePassword(@RequestBody String password) {
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		User user =  user_repository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		user_service.changeUserPassword(user, password);
//		return "Password change succesful";
//	}
	
//	@RequestMapping(value = "/rol/{username}", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody 
//	public Map findRoleByUsername(@PathVariable String username) {
//		User user = user_service.findByUsername(username);
//		String role = user.getRole().getRoleName();
//		return Collections.singletonMap("role", role);
//	}
	
	
	@PostMapping("/user/resetPassword")
	//@ResponseBody
	public String resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		User user = user_service.findUserByUsername(userEmail);
		if (user == null) {
		    return "user not found";
		}
		String token = UUID.randomUUID().toString();
		user_service.createPasswordResetTokenForUser(user.getUser_id(), token);
		mailSender.send(constructResetTokenEmail("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(), request.getLocale(), token, user));
		//return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
		return "mail mandado";
	}
	
	private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
		String url = contextPath + "/user/changePassword?id=" + user.getUser_id() + "&token=" + token;
	    String message = "Reset Password";
	    return constructEmail("Reset Password", message + " \r\n" + url, user);
	}
			 
	private SimpleMailMessage constructEmail(String subject, String body, User user) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject(subject);
	    email.setText(body);
	    email.setTo(user.getUsername());
	    email.setFrom("teijiz.matias@gmail.com");
	    return email;
	}
	
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String showChangePasswordPage(Locale locale, Model model,@RequestParam("id") long id, @RequestParam("token") String token) {
	    String result = securityService.validatePasswordResetToken(id, token);
	    if (result != null) {
	        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
	        return "redirect:/login";
	    }
	    return "redirect:/updatePassword.html";
	}
	
	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePassword(Locale locale, String newPassword) {//problema que tengo es que no puedo recuperar el usuario que quiere recuperar la contraseña
		System.out.println("\n\n\n" + SecurityContextHolder.getContext().getAuthentication().getName() + "\n\n\n");
	    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   // String username = SecurityContextHolder.getContext().getAuthentication().getName();
		user_service.changeUserPassword(user, newPassword);
	    return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
	}

}
