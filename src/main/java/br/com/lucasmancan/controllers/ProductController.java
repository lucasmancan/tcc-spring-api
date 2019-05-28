package br.com.lucasmancan.controllers;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.models.ProductCategory;
import br.com.lucasmancan.services.ProductCategoryService;
import br.com.lucasmancan.services.ProductService;
import br.com.lucasmancan.utils.AppPaginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ResponseBody
    @GetMapping
    public ResponseEntity getAll(@PageableDefault(page = 0, size = 30) @RequestParam("page") int page, @RequestParam("size") int size) {
        try {

            var products = productService.findAll(new AppPaginator(page, size));

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @GetMapping("/{code}")
    public ResponseEntity getByCode(@PathParam("code") Long code) {
        try {

            var product = productService.findByCode(code);

            return ResponseEntity.ok(product);

        } catch (AppNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generate")
    public ResponseEntity generate() {


        ProductCategory category = new ProductCategory();
        category.setName("Categoria teste " + new Date().getTime());
        category.setDescription("Descricao " + category.getName());

        category = productCategoryService.save(category);

        for (var i = 0; i < 20; i++) {

            Product product = new Product();
            product.setName("Produto teste " + i);
            product.setDescription("Descricao Produto Teste: " + i);
            product.setCategory(category);

            productService.save(product);

        }

        return ResponseEntity.ok().build();
    }
}
