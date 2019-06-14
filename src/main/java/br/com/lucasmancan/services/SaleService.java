package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

		if (sale.getGrossAmount() == null) {
			sale.setGrossAmount(BigDecimal.ZERO);
		}

		if (sale.getAmount() == null) {
			sale.setAmount(BigDecimal.ZERO);
		}

		sale.setUpdatedAt(new Date());


		for (SaleItem item : sale.getItems()) {

			if (item.getDiscount() == null) {
				item.setDiscount(BigDecimal.ZERO);
			}

			if (item.getOtherExpenses() == null) {
				item.setOtherExpenses(BigDecimal.ZERO);
			}

			if (item.getGrossAmount() == null) {
				item.setGrossAmount(BigDecimal.ZERO);
			}

			if (item.getAmount() == null) {
				item.setAmount(BigDecimal.ZERO);
			}

			item.setGrossAmount(item.getUnitary()
					.multiply(new BigDecimal(item.getQuantity()))
					.add(item.getOtherExpenses()));

			item.setAmount(item.getGrossAmount().subtract(item.getDiscount()));
			item.setUpdatedAt(new Date());

			item.setSale(sale);

			sale.setDiscount(sale.getDiscount().add(item.getDiscount()));
			sale.setOtherExpenses(sale.getOtherExpenses().add(item.getOtherExpenses()));
			sale.setGrossAmount(sale.getGrossAmount().add(item.getGrossAmount()));
		}


		sale.setAmount(sale.getGrossAmount().subtract(sale.getDiscount()).add(sale.getOtherExpenses()));
		sale.setUpdatedUser(getPrincipal());

		return repository.save(sale);
	}


	@Override
	public void remove(Sale entity) {
		repository.delete(entity);
	}

	@Cacheable(value = "salesCache")
	public Page<SaleDTO> findAll(Pageable pageable) {
		return repository.findAll(getLoggedAccount().getId(), pageable);
	}

	@Cacheable(value = "salesCache")
	public Page<SaleDTO> findAll(Pageable pageable, String status, String customerName, BigDecimal lower, BigDecimal upper) {
		return repository.findAll(getLoggedAccount().getId(), pageable, status, customerName, lower, upper);
	}


	@Override
	@Cacheable(value = "salesCache")
	public List findAll() {
		return repository.findAll();
	}

	@Override
	@Cacheable(value = "salesCache", key = "#id")
	public Sale findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	@Cacheable(value = "salesCache", key = "#code")
	public Sale findByCode(Long code) throws AppNotFoundException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

}