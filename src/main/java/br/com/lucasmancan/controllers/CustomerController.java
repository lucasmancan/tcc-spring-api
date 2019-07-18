package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.CustomerDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Customer;
import br.com.lucasmancan.services.CustomerService;
import br.com.lucasmancan.utils.AppPaginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @GetMapping
    public Page<Customer> getAll(@PageableDefault(page = 0, size = 30) @RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.findAll(new AppPaginator(page, size));
    }

    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDTO save(@Valid @RequestBody CustomerDTO customerDTO) throws AppNotFoundException {
        return customerService.save(customerDTO);
    }

    @ResponseBody
    @GetMapping("/{code}")
    public CustomerDTO getByCode(@PathVariable("code") Long code) throws AppNotFoundException {
        return customerService.findByCode(code);
    }

    @ResponseBody
    @PutMapping("/{code}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDTO update(@PathVariable("code") Long code, @Valid @RequestBody CustomerDTO customerDTO) throws AppNotFoundException {
        return customerService.update(code, customerDTO);
    }

}
