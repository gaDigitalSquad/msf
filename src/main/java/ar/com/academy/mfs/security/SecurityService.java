package ar.com.academy.mfs.security;

import java.util.Calendar;
import java.util.Optional;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.PasswordResetToken;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.repository.PasswordResetTokenRepository;
import ar.com.academy.mfs.repository.UserRepository;

@Service
public class SecurityService {
	
	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public String validatePasswordResetToken(long id, String token) {
		PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
	    if ((passToken == null) || (passToken.getUser_id() != id)) {
	        return "invalidToken";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate()
	        .getTime() - cal.getTime()
	        .getTime()) <= 0) {
	        return "expired";
	    }
	    
	    Optional<User> user = userRepository.findById(passToken.getUser_id());
	    Authentication auth = new UsernamePasswordAuthenticationToken(user, null);//revisar esta linea
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    return null;
	}
	
}
