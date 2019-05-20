package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService extends AbstractService<Account> {

	@Autowired
	private AccountRepository repository;

    @Override
	public Account save(Account entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(Account entity) {
		repository.delete(entity);
	}

	@Override
	public <T> T findByCode(Long code) throws AppNotFoundException, AppSecurityContextException {
		return null;
	}

	@Override
	public Account findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public List<Account> findAll() {
		return repository.findAll();
	}
}
