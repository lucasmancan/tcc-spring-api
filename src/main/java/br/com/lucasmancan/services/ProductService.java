package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.models.ProductPrice;
import br.com.lucasmancan.models.Status;
import br.com.lucasmancan.repositories.ProductPriceRepository;
import br.com.lucasmancan.repositories.ProductRepository;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;import java.util.List;

@Service
public class ProductService extends AbstractService<Product> {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductPriceRepository priceRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ModelMapper mapper;

    private ProductDTO convert(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    public Product save(Product product) throws AppNotFoundException {
        if(product.getId() == null){
            product.setCreatedAt(new Date());
            product.setAccount(getLoggedAccount());
            product.setCreationAppUser(getPrincipal());
        }

        product.setUpdatedAt(new Date());

        return repository.save(product);
    }

    public void remove(Long code) throws AppNotFoundException {
        var entity = find(code);

        entity.setStatus(Status.deleted);

        repository.save(entity);
    }

    public void removeCategory(Long code) throws AppNotFoundException {
        var entity = productCategoryService.findByCode(code);

        entity.setStatus(Status.deleted);

        productCategoryService.save(entity);
    }

    @Cacheable(value = "productsCache")
    public Page<ProductDTO> findAll(Pageable pageable, String name, String categoryName) {

        var domainPageable = repository.findAll(getLoggedAccount().getId(), pageable, name, categoryName);

        return domainPageable.map(this::convert);
    }


    public List<Product> findAll(String name, String categoryName) {
        return  repository.findAll(getLoggedAccount().getId(), name, categoryName);
    }

    public Product findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    public Product findByCode(Long code) throws AppNotFoundException {
        return find(code);
    }

    public Product find(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);
    }

    public Product update(Long code, Product product) throws AppNotFoundException {
        var old = find(code);

        return save(product);
    }


    @Cacheable(value = "productsCache")
    public List<Product> findAll() {
        return repository.findAll();
    }

    public ProductPrice savePrice(ProductPrice price) {

        price.setCreatedAt(new Date());
        price.setCreationAppUser(getPrincipal());
        price.setUpdatedAt(new Date());

        return priceRepository.save(price);
    }

    public List<ProductPrice> findAllPrices(Long productId) {
        return priceRepository.findAllByProductId(productId);
    }
}
