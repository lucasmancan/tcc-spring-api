package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.repositories.SaleRepository;
import br.com.lucasmancan.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class SaleService extends AbstractService<Sale> {

	@Autowired
	private SaleRepository repository;

	@Override
	public Sale save(Sale sale) {

		if (sale.getCode() == null) {
			sale.setEmployee(getPrincipal());
			sale.setAccount(getLoggedAccount());
			sale.setCreatedAt(new Date());
		}

		if (sale.getState() == null) {
			sale.setState(Sale.SaleState.PEN);
		}

		if (sale.getDiscount() == null) {
			sale.setDiscount(BigDecimal.ZERO);
		}

		if (sale.getOtherExpenses() == null) {
			sale.setOtherExpenses(BigDecimal.ZERO);
		}

		if (sale.getTotalGross() == null) {
			sale.setTotalGross(BigDecimal.ZERO);
		}

		if (sale.getTotalLiquid() == null) {
			sale.setTotalLiquid(BigDecimal.ZERO);
		}

		sale.setUpdatedAt(new Date());


		for (SaleItem item : sale.getItems()) {

			if (item.getDiscount() == null) {
				item.setDiscount(BigDecimal.ZERO);
			}

			if (item.getOtherExpenses() == null) {
				item.setOtherExpenses(BigDecimal.ZERO);
			}

			if (item.getTotalGross() == null) {
				item.setTotalGross(BigDecimal.ZERO);
			}

			if (item.getTotalLiquid() == null) {
				item.setTotalLiquid(BigDecimal.ZERO);
			}

			item.setTotalGross(item.getUnitary()
					.multiply(new BigDecimal(item.getQuantity()))
					.add(item.getOtherExpenses()));

			item.setTotalLiquid(item.getTotalGross().subtract(item.getDiscount()));
			item.setUpdatedAt(new Date());

			item.setSale(sale);

			sale.setDiscount(sale.getDiscount().add(item.getDiscount()));
			sale.setOtherExpenses(sale.getOtherExpenses().add(item.getOtherExpenses()));
			sale.setTotalGross(sale.getTotalGross().add(item.getTotalGross()));
		}


		sale.setTotalLiquid(sale.getTotalGross().subtract(sale.getDiscount()).add(sale.getOtherExpenses()));
		sale.setUpdatedUser(getPrincipal());

		return repository.save(sale);
	}


	@Override
	public void remove(Sale entity) {
		repository.delete(entity);
	}

	@Override
	public Page<Sale> findAll(Pageable pageable) throws AppSecurityContextException {
		return repository.findAll(pageable);
	}

	@Override
	public List findAll() throws AppSecurityContextException {
		return null;
	}

	@Override
	public Sale findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public Sale findByCode(Long code) throws AppNotFoundException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

}
