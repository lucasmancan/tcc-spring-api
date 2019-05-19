package br.com.lucasmancan.security;

import java.util.Optional;

import br.com.lucasmancan.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.lucasmancan.repositories.UserRepository;

@Component
public class AppUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return Optional.ofNullable(repo.findByEmail(email))
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!")) ;
	}

}
