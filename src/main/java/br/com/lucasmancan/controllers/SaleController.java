package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.services.AbstractService;
import br.com.lucasmancan.services.SaleService;
import br.com.lucasmancan.utils.AppPaginator;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/api/sales")
@Log4j2
public class SaleController extends AbstractService<Sale> {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ModelMapper mapper;

    @ResponseBody
    @GetMapping
    public AppResponse getAll(@PageableDefault(page = 0, size = 30) @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size,
                              @RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "customerName", required = false) String customerName,
                              @RequestParam(value = "upper", required = false) BigDecimal upper,
                              @RequestParam(value = "lower", required = false) BigDecimal lower) {
        try {

            if (Objects.equals(page, null)) {
                page = 1;
            }

            if (Objects.equals(size, null)) {
                size = 30;
            }

            return new AppResponse("", this.saleService.findAll(new AppPaginator(page, size), status, customerName, upper, lower));
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }


    @ResponseBody
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppResponse save(@Valid @RequestBody SaleDTO saleDTO) throws AppNotFoundException {
        try {

            saleService.save(saleDTO);
            return new AppResponse("Venda atualizada!", false);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @GetMapping("/{code}")
    public AppResponse getByCode(@PathVariable("code") Long code) throws AppNotFoundException {
        try {
            return new AppResponse("", saleService.findByCode(code));
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @DeleteMapping("/{code}")
    public AppResponse delete(@PathVariable("code") Long code) throws AppNotFoundException {
        try {
            saleService.remove(code);

            return new AppResponse("Venda excluída!", null);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }


    @ResponseBody
    @PutMapping("{code}")
    public AppResponse update(@PathVariable("code") Long code, @RequestBody SaleDTO saleDTO) {
        try {
            return new AppResponse("Venda atualizada!", saleService.update(code, saleDTO));
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }
}
