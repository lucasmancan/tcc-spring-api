package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Product;
import br.com.lucasmancan.models.ProductPrice;
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
@RequestMapping("/api/products-prices")
@Log4j2
public class ProductPriceController {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/{productId}")
    public AppResponse getAllByProduct(@PathVariable("productId") Long productId) {

        try {
            return new AppResponse("", productService.findAllPrices(productId));
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppResponse save(@Valid @RequestBody ProductPrice price) {
        try {
            productService.savePrice(price);

            return new AppResponse("Preço atualizado!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

}
