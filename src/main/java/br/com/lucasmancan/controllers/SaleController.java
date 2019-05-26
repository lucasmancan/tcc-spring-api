package br.com.lucasmancan.controllers;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;
	
	@ResponseBody
	@GetMapping
	public ResponseEntity getAll(@PageableDefault(page=0, size = 1) @RequestBody PageRequest pageable) {
		try{

			var sales = saleService.findAll(pageable);

			return ResponseEntity.ok(sales);
		}catch (Exception e){
			return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ResponseBody
	@GetMapping("/{code}")
	public ResponseEntity getByCode(@PathParam("code") Long code) {
		try{

			var sale = saleService.findByCode(code);

			return ResponseEntity.ok(sale);

		}catch (AppNotFoundException e){
			return ResponseEntity.notFound().build();
		} catch (Exception e){
			return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/me")
	public ResponseEntity home()
	{
		AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity(principal,  HttpStatus.OK);
	}


	@GetMapping("/generate")
	public ResponseEntity generate(){

		for(var i = 0; i < 5000; i ++){
			Sale sale = new Sale();


			List<SaleItem> itens = new ArrayList<SaleItem>();

			for(var j = 0; j< 10; j ++){
				SaleItem item = new SaleItem();

				item.setUnitary(new BigDecimal(Math.random() * 1000));
				item.setOtherExpenses(new BigDecimal(Math.random() * 100));
				item.setQuantity((int) Math.random() * 100);
				item.setDiscount(new BigDecimal(Math.random() * 500));
				item.setSale(sale);

				itens.add(item);
			}


			sale.setItems(new ArrayList<>());

			sale.getItems().addAll(itens);
			saleService.save(sale);

		}

		return ResponseEntity.ok().build();
	}
}
