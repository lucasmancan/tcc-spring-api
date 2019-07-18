package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.services.ProductCategoryService;
import br.com.lucasmancan.services.ProductService;
import br.com.lucasmancan.utils.AppPaginator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return productService.findByCode(code);
    }

    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO save(@Valid @RequestBody ProductDTO productDTO) throws AppNotFoundException {
        return productService.save(productDTO);
    }

    @ResponseBody
    @PutMapping("/{code}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO update(@PathVariable("code") Long code, @Valid @RequestBody ProductDTO productDTO) throws AppNotFoundException {
        return productService.update(code, productDTO);
    }

}
