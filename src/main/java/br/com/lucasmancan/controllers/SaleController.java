package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.services.AbstractService;
import br.com.lucasmancan.services.SaleService;
import br.com.lucasmancan.utils.AppPaginator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/api/sales")
public class SaleController extends AbstractService<Sale> {

    @Autowired
    private SaleService saleService;


    @Autowired
    private ModelMapper mapper;

    @ResponseBody
    @GetMapping
    public Page<SaleDTO> getAll(@PageableDefault(page = 0, size = 30) @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size,
                                @RequestParam(value = "status", required = false) String status,
                                @RequestParam(value = "customerName", required = false) String customerName,
                                @RequestParam(value = "upper", required = false) BigDecimal upper,
                                @RequestParam(value = "lower", required = false) BigDecimal lower) {


        if (Objects.equals(page, null)) {
            page = 1;
        }

        if (Objects.equals(size, null)) {
            size = 30;
        }

        return this.saleService.findAll(new AppPaginator(page, size), status, customerName, upper, lower);
    }


    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SaleDTO save(@Valid @RequestBody SaleDTO saleDTO) throws AppNotFoundException {
        return saleService.save(saleDTO);
    }

    @ResponseBody
    @GetMapping("/{code}")
    public SaleDTO getByCode(@PathVariable("code") Long code) throws AppNotFoundException {
        return saleService.findByCode(code);
    }

    @ResponseBody
    @PutMapping("/{code}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public SaleDTO update(@PathVariable("code") Long code, @Valid @RequestBody SaleDTO saleDTO) throws AppNotFoundException {
        return saleService.update(code, saleDTO);
    }
}
