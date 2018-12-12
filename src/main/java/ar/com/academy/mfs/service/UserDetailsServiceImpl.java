package ar.com.academy.mfs.service;


import static java.util.Collections.emptyList;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import ar.com.academy.mfs.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository usuarioRepository;

	public UserDetailsServiceImpl(UserRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ar.com.academy.mfs.model.User usuario = usuarioRepository.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(usuario.getUsername(), usuario.getPassword(), emptyList());
	}
}