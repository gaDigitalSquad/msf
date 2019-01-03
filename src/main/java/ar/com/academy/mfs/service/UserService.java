package ar.com.academy.mfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.request.UserRequest;

@Service("userService")
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public UserService() {
    	
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
//    public User createUser(UserRequest ur) {
//		User user = new User(ur);
//		User userCreate = userRepository.save(user);
//		return userCreate;
//	}

//    public User saveUser(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(true);
//        int userRole = 1;
//        user.setRole_id(userRole);
//        userRepository.save(user);
//        return user;
//    }

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(long id) {
		return userRepository.findById(id).get();
	}
	
//	public User updateUser(long user_id, User updateUser) {
//		User toUpdateUser = getUserById(user_id);
//		if(updateUser.getDocumentNumber()>0) {
//			toUpdateUser.setDocumentNumber(updateUser.getDocumentNumber());
//		}
//		if(updateUser.getDocumentType()!=null){
//			toUpdateUser.setDocumentType(updateUser.getDocumentType());
//		}
//		if(updateUser.getFirstname()!=null) {
//			toUpdateUser.setFirstname(updateUser.getFirstname());
//		}
//		if(updateUser.getLastname()!=null) {
//			toUpdateUser.setLastname(updateUser.getLastname());
//		}
//		if(updateUser.getPassword()!=null) {
//			toUpdateUser.setPassword(updateUser.getPassword());
//		}
//		if(updateUser.getPhoneNumber()>0) {
//			toUpdateUser.setPhoneNumber(updateUser.getPhoneNumber());
//		}
//		if(updateUser.getRole_id()>0) {
//			toUpdateUser.setRole_id(updateUser.getRole_id());
//		}
//		if(updateUser.getUsername()!=null) {
//			toUpdateUser.setUsername(updateUser.getUsername());
//		}
//		return toUpdateUser;
//	}
//	
	public long deleteLogicUser(long user_id) {
		long toReturn = 0 ;
		User toDeleteUser = userRepository.findById(user_id).get();
		if(!toDeleteUser.isActive()) {
			toDeleteUser.setActive(false);
			toReturn=toDeleteUser.getUser_id();
			userRepository.save(toDeleteUser);
		}
		return toReturn;
	}
	
	public long deletePhysicalUser(long user_id) {
		long toReturn=0;
		if(userRepository.existsById(user_id)) {
			userRepository.deleteById(user_id);
			toReturn=user_id;
		}
		return toReturn;
		
	}

	public boolean usernameExists(String username) {
		if(userRepository.findByUsername(username)!=null) {
			return true;
		}
		return false;
	}

	public void save(User signupUser) {
		userRepository.save(signupUser);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}	

}