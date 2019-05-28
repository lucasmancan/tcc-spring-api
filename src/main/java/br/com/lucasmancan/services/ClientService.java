package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Client;
import br.com.lucasmancan.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClientService extends AbstractService<Client> {

	@Autowired
	private ClientRepository repository;

	@Override
	public Client save(Client entity) {

        if (entity.getCode() == null) {
            entity.setCreatedAt(new Date());
            entity.setAccount(getLoggedAccount());
        }


        entity.setUpdatedAt(new Date());
        entity.setCreationAppUser(getPrincipal());

		return repository.save(entity);
	}

	@Override
	public void remove(Client entity) {
		repository.delete(entity);
	}

    public Page<Client> findAll(Pageable pageable) {
        return repository.findAll(getLoggedAccount().getId(), pageable);
    }

	@Override
	public Client findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}


	@Override
    public Client findByCode(Long code) throws AppNotFoundException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

    public List<Client> findAll() {
        return repository.findAll();
	}
}
