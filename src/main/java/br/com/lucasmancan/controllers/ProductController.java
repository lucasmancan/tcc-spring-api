package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.models.ProductCategory;
import br.com.lucasmancan.services.ProductCategoryService;
import br.com.lucasmancan.services.ProductService;
import br.com.lucasmancan.utils.AppPaginator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @GetMapping
    public Page<ProductDTO> getAll(@PageableDefault(page = 0, size = 30) @RequestParam("page") int page, @RequestParam("size") int size) {
        return productService.findAll(new AppPaginator(page, size));
    }

    @ResponseBody
    @GetMapping("/{code}")
    public ProductDTO getByCode(@PathVariable("code") Long code) throws AppNotFoundException {
        return mapper.map(productService.findByCode(code), ProductDTO.class);
    }

    @GetMapping("/generate")
    public ResponseEntity generate() {

        ProductCategory category = new ProductCategory();
        category.setName("Categoria teste " + new Date().getTime());
        category.setDescription("Descricao " + category.getName());

        category = productCategoryService.save(category);

        for (var i = 0; i < 500; i++) {

            Product product = new Product();
            product.setName("Produto teste " + i);
            product.setDescription("Descricao Produto Teste: " + i);
            product.setCategory(category);

            productService.save(product);

        }

        return ResponseEntity.ok().build();
    }
}
