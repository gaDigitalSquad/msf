package ar.com.academy.mfs.security;

import static ar.com.academy.mfs.security.Constants.HEADER_AUTHORIZACION_KEY;
import static ar.com.academy.mfs.security.Constants.ISSUER_INFO;
import static ar.com.academy.mfs.security.Constants.SUPER_SECRET_KEY;
import static ar.com.academy.mfs.security.Constants.TOKEN_BEARER_PREFIX;
import static ar.com.academy.mfs.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
/*import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;*/

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins="http://localhost:4200")
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	/*Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;*/  
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public JWTAuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ar.com.academy.mfs.model.User credenciales = new ObjectMapper().readValue(request.getInputStream(), ar.com.academy.mfs.model.User.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credenciales.getUsername(), credenciales.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
				.setSubject(((User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
		response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(token);
		
		/*try {
			// This block configure the logger with handler and formatter  
			fh = new FileHandler("C:/Users/capacitacion/projects/msf/msf-backend/Logs/LogFile.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			
			// the following statement is used to log any messages  
			logger.info("Se realizo el login de " + ((User)auth.getPrincipal()).getUsername() + "\n");
		} catch (SecurityException e) {  
		        e.printStackTrace();  
		} catch (IOException e) {  
		        e.printStackTrace();  
		}*/  
	}
	
	public String createToken(String username) {
		return Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
		.setSubject(username)
		.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
		.signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
	}

}