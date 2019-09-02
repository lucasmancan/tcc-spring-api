package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService extends AbstractService<Product> {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ModelMapper mapper;


    private Product convert(ProductDTO dto, Product product) throws AppNotFoundException {

        if (product == null) {
            product = new Product();
        }

        product = mapper.map(dto, Product.class);
        product.setCategory(productCategoryService.findByCode(dto.getCategory().getCode()));
        product.setAccount(getLoggedAccount());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    private ProductDTO convert(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    public void save(ProductDTO dto) throws AppNotFoundException {

        var product = convert(dto, null);

        product.setCreatedAt(LocalDateTime.now());
        product.setAccount(getLoggedAccount());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreationAppUser(getPrincipal());

        repository.save(product);
    }

    public void remove(Long code) throws AppNotFoundException {
        var entity = find(code);

        repository.delete(entity);
    }

    @Cacheable(value = "productsCache")
    public Page<ProductDTO> findAll(Pageable pageable, String name, String categoryName) {

        var domainPageable = repository.findAll(getLoggedAccount().getId(), pageable, name, categoryName);

        return domainPageable.map(this::convert);
    }

    public Product findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    public ProductDTO findByCode(Long code) throws AppNotFoundException {
        var product = find(code);

        return convert(product);
    }

    public Product find(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);
    }

    public ProductDTO update(Long code, ProductDTO productDTO) throws AppNotFoundException {

        var product = find(code);

        product = convert(productDTO, product);

        product.setCode(code);

        return convert(product);
    }


    @Cacheable(value = "productsCache")
    public List<Product> findAll() {
        return repository.findAll();
    }
}
