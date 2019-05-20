package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.Client;
import br.com.lucasmancan.models.Client;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService extends AbstractService<Client> {

	@Autowired
	private ClientRepository repository;

	@Override
	public Client save(Client entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(Client entity) {
		repository.delete(entity);
	}

	@Override
	public Client findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}


	@Override
	public Client findByCode(Long code) throws AppNotFoundException, AppSecurityContextException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public List<Client> findAll() throws AppSecurityContextException {
		return repository.findAll(getLoggedAccount().getId());
	}
}
