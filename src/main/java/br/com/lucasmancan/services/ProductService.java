package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends AbstractService<Product> {

	@Autowired
	private ProductRepository repository;

	@Override
	public Product save(Product entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(Product entity) {
		repository.delete(entity);
	}

	@Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
	}

	@Override
	public Product findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
    public Product findByCode(Long code) throws AppNotFoundException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

	public List<Product> findAll() {
		return repository.findAll();
	}
}
