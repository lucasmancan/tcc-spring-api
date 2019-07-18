package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.models.ProductCategory;
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

    private Product convert(ProductDTO dto) {
        return this.convert(dto, null);
    }

    private Product convert(ProductDTO dto, Product product) {

        if (product == null) {
            product = new Product();
        }

        product = mapper.map(product, Product.class);
        product.setCategory(mapper.map(dto.getCategory(), ProductCategory.class));

        return product;
    }

    private ProductDTO convert(Product product) {
        var dto = mapper.map(product, ProductDTO.class);
        return dto;
    }

    public ProductDTO save(ProductDTO dto) throws AppNotFoundException {

        var product = mapper.map(dto, Product.class);
        var productCategory = productCategoryService.findByCode(dto.getCode());

        product.setCategory(productCategory);
        product.setCreatedAt(LocalDateTime.now());
        product.setAccount(getLoggedAccount());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreationAppUser(getPrincipal());

        product = repository.save(product);

        return convert(product);
    }

    public void remove(Product entity) {
        repository.delete(entity);
    }

    @Cacheable(value = "productsCache")
    public Page<ProductDTO> findAll(Pageable pageable) {
        return repository.findAll(getLoggedAccount().getId(), pageable);
    }

    public Product findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
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

        return convert(product);
    }


    @Cacheable(value = "productsCache")
    public List<Product> findAll() {
        return repository.findAll();
    }
}
