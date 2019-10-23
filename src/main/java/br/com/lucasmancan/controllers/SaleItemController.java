package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
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
@RequestMapping("/api/sales-items")
@Log4j2
public class SaleItemController extends AbstractService<Sale> {

    @Autowired
    private SaleService saleService;


    @ResponseBody
    @GetMapping("/{saleId}")
    public AppResponse getAllBySale(@PathVariable("saleId") Long saleId) {
        try {


            return new AppResponse("", this.saleService.listItems(saleId));
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @DeleteMapping("/{saleId}")
    public AppResponse delete(@PathVariable("saleId") Long saleId) throws AppNotFoundException {
        try {
            saleService.removeItem(saleId);

            return new AppResponse("Venda excluída!", null);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }
}
