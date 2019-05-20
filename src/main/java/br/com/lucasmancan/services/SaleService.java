package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.repositories.SaleRepository;
import br.com.lucasmancan.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService extends AbstractService<Sale> {

	@Autowired
	private SaleRepository repository;

	@Override
	public Sale save(Sale entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(Sale entity) {
		repository.delete(entity);
	}

	@Override
	public Sale findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public Sale findByCode(Long code) throws AppNotFoundException, AppSecurityContextException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}


	@Override
	public List<Sale> findAll() throws AppSecurityContextException {
		return repository.findAll(getLoggedAccount().getId());
	}
}
