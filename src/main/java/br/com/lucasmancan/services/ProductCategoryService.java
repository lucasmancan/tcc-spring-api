package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.ProductCategory;
import br.com.lucasmancan.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryService extends AbstractService<ProductCategory> {

    @Autowired
    private ProductCategoryRepository repository;

    public ProductCategory save(ProductCategory entity) {

        if (entity.getCode() == null) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setAccount(getLoggedAccount());
        }

        entity.setUpdatedAt(LocalDateTime.now());

        return repository.save(entity);
    }

    public void remove(ProductCategory entity) {
        repository.delete(entity);
    }

    public Page<ProductCategory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public ProductCategory findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
    }

    public ProductCategory findByCode(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);
    }

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }
}
