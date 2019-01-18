package ar.com.academy.mfs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.PasswordResetToken;
import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.User;

import ar.com.academy.mfs.repository.GroupRepository;

import ar.com.academy.mfs.repository.PasswordResetTokenRepository;
import ar.com.academy.mfs.repository.RoleRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.request.UserRequest;

@Service("userService")
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    

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
	
	public User getUserById(int id) {
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
	public long deleteLogicUser(int user_id) {
		long toReturn = 0 ;
		User toDeleteUser = userRepository.findById(user_id).get();
		if(!toDeleteUser.isActive()) {
			toDeleteUser.setActive(false);
			toReturn=toDeleteUser.getUser_id();
			userRepository.save(toDeleteUser);
		}
		return toReturn;
	}
	
	public long deletePhysicalUser(int user_id) {
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

	// Role_id = 3 => LIDER
	public List<User> getLeadersWithoutGroup() {
		List<User> leadersWithoutGroup = new ArrayList<User>();
		List<User> users = userRepository.findAll();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getGroup_number() == null && users.get(i).getRole_id() == 3) {
				leadersWithoutGroup.add(users.get(i));
			}
		}
		return leadersWithoutGroup;
	}
	
	// Role_id = 2 => SENSIBILIZADOR
	public List<User> getSensWithoutGroup() {
		List<User> sensWithoutGroup = new ArrayList<User>();
		List<User> users = userRepository.findAll();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getGroup_number() == null && users.get(i).getRole_id() == 2) {
				sensWithoutGroup.add(users.get(i));
			}
		}
		return sensWithoutGroup;
	}
	
	public List<User> getMySens(int supervisor_id) {
		List<User> mySens = new ArrayList<User>();
		List<Integer> sensId = groupRepository.findMySens(supervisor_id);
		for (int i = 0; i < sensId.size(); i++) {
			User u = userRepository.findById(sensId.get(i)).get();
			mySens.add(u);
		}
		return mySens;
	}
	
	public void createPasswordResetTokenForUser(int user_id, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(token, user_id);
	    passwordTokenRepository.save(myToken);
	}

	public void changeUserPassword(User user, String password) {
	    user.setPassword(bCryptPasswordEncoder.encode(password));
	    userRepository.save(user);
	}

	public List<User> getGroupSens(int group_number) {
		List<User> mySens = new ArrayList<User>();
		List<Integer> sensId = groupRepository.findGroupSens(group_number);
		for (int i = 0; i < sensId.size(); i++) {
			User u = userRepository.findById(sensId.get(i)).get();
			mySens.add(u);
		}
		return mySens;
	}

}