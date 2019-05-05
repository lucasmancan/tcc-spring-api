package br.com.lucasmancan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.lucasmancan.models.User;
import br.com.lucasmancan.repositories.UserRepository;

public class AppUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = repo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return user;
	}

}
