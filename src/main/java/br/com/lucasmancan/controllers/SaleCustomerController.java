package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.services.AbstractService;
import br.com.lucasmancan.services.SaleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales-customers")
@Log4j2
public class SaleCustomerController extends AbstractService<Sale> {

    @Autowired
    private SaleService saleService;

    @ResponseBody
    @GetMapping("/{saleId}")
    public AppResponse getAllBySale(@PathVariable("saleId") Long saleId) {
        try {


            return new AppResponse("", this.saleService.listCustomers(saleId));
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }

    @ResponseBody
    @DeleteMapping("/{saleId}")
    public AppResponse delete(@PathVariable("saleId") Long saleId) throws AppNotFoundException {
        try {
            saleService.removeCustomer(saleId);

            return new AppResponse("Venda excluída!", null);
        } catch (AppNotFoundException ex) {
            return new AppResponse("Venda não encontrada!", null);
        } catch (Exception ex) {
            log.warn("Erro Interno" + ex);
            return AppResponse.OOPS;
        }
    }
}
