package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.services.ProductCategoryService;
import br.com.lucasmancan.services.ProductService;
import br.com.lucasmancan.utils.AppPaginator;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Log4j2
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @GetMapping
    public AppResponse getAll(@PageableDefault(page = 0, size = 30) @RequestParam(value="page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size,
                              @RequestParam(value="name", required = false) String name,
                              @RequestParam(value = "categoryName", required = false) String categoryName) {

        try {
            if (page == null) {
                page = 1;
            }

            if (size == null) {
                size = 30;
            }

            return new AppResponse("", productService.findAll(new AppPaginator(page, size), name, categoryName));
        } catch (Exception ex) {
//            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @GetMapping("/{code}")
    public AppResponse getByCode(@PathVariable("code") Long code) {
        try {
            return new AppResponse("", productService.findByCode(code));
        } catch (AppNotFoundException ex) {
            return new AppResponse("Produto não encontrado!", null);
        } catch (Exception ex) {
//            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppResponse save(@Valid @RequestBody ProductDTO productDTO) {
        try {
            productService.save(productDTO);

            return new AppResponse("Produto atualizado!", null);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Produto não encontrado!", null);
        } catch (Exception ex) {
//            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @PutMapping("/{code}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppResponse update(@PathVariable("code") Long code, @Valid @RequestBody ProductDTO productDTO) throws AppNotFoundException {
        try {
            return new AppResponse("Produto atualizado!", productService.update(code, productDTO));
        } catch (AppNotFoundException ex) {
            return new AppResponse("Produto não encontrado!", null);
        } catch (Exception ex) {
//            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @DeleteMapping("/{code}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppResponse remove(@PathVariable("code") Long code) {
        try {
            productService.remove(code);
            return new AppResponse("Produto excluído!", null);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Produto não encontrado!", null);
        } catch (Exception ex) {
//            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

}
