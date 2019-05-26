package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.models.Client;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserService extends AbstractService<AppUser> {

	@Autowired
	private UserRepository repository;

	@Override
	public AppUser save(AppUser entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(AppUser entity) {
		repository.delete(entity);
	}



	@Override
	public AppUser findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public AppUser findByCode(Long code) throws AppNotFoundException, AppSecurityContextException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public Page<AppUser> findAll(Pageable pageable) {
		return repository.findAll();
	}
}
