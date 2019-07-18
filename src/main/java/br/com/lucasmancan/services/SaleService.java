package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.dtos.SaleItemDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleService extends AbstractService<Sale> {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private ModelMapper mapper;

	public Sale convert(SaleDTO saleDTO) {

		var sale = new Sale();

		sale.setStatus(saleDTO.getStatus());
		sale.setOtherExpenses(saleDTO.getOtherExpenses());
		sale.setDiscount(saleDTO.getDiscount());
		sale.setAmount(saleDTO.getAmount());
		sale.setGrossAmount(saleDTO.getGrossAmount());
		sale.setUpdatedAt(saleDTO.getUpdatedAt());

		if (!CollectionUtils.isEmpty(saleDTO.getItems())) {
			for (SaleItemDTO saleItemDTO : saleDTO.getItems()) {

				final SaleItem saleItem = new SaleItem();

				saleItem.setAmount(saleItemDTO.getAmount());
				saleItem.setStatus(saleItemDTO.getStatus());
				saleItem.setGrossAmount(saleItemDTO.getGrossAmount());
				saleItem.setDiscount(saleItemDTO.getDiscount());
				saleItem.setOtherExpenses(saleItemDTO.getOtherExpenses());
				saleItem.setUnitary(saleItemDTO.getUnitary());

				sale.getItems().add(saleItem);
			}
		}

		return sale;
	}

	public SaleDTO convert(Sale sale) {

		var saleDTO = new SaleDTO();

		saleDTO.setStatus(saleDTO.getStatus());
		saleDTO.setOtherExpenses(saleDTO.getOtherExpenses());
		saleDTO.setDiscount(saleDTO.getDiscount());
		saleDTO.setAmount(saleDTO.getAmount());
		saleDTO.setGrossAmount(saleDTO.getGrossAmount());
		saleDTO.setUpdatedAt(saleDTO.getUpdatedAt());

		if (!CollectionUtils.isEmpty(saleDTO.getItems())) {
			for (SaleItem saleItemDTO : sale.getItems()) {

				final SaleItemDTO saleItem = new SaleItemDTO();
				final ProductDTO productDTO = mapper.map(saleItemDTO, ProductDTO.class);

				saleItem.setProduct(productDTO);
				saleItem.setAmount(saleItemDTO.getAmount());
				saleItem.setStatus(saleItemDTO.getStatus());
				saleItem.setGrossAmount(saleItemDTO.getGrossAmount());
				saleItem.setDiscount(saleItemDTO.getDiscount());
				saleItem.setOtherExpenses(saleItemDTO.getOtherExpenses());
				saleItem.setUnitary(saleItemDTO.getUnitary());

				saleDTO.getItems().add(saleItem);
			}
		}

		return saleDTO;
	}


	public SaleDTO save(SaleDTO saleDTO) {

		var sale = convert(saleDTO);

			sale.setEmployee(getPrincipal());

			sale.setAccount(getLoggedAccount());

		for (SaleItem item : sale.getItems()) {
			item.setSale(sale);

			sale.setDiscount(sale.getDiscount().add(item.getDiscount()));
			sale.setOtherExpenses(sale.getOtherExpenses().add(item.getOtherExpenses()));
			sale.setGrossAmount(sale.getGrossAmount().add(item.getGrossAmount()));
		}


		sale.setAmount(sale.getGrossAmount().subtract(sale.getDiscount()).add(sale.getOtherExpenses()));
		sale.setUpdatedUser(getPrincipal());

		sale = repository.save(sale);

		return this.convert(sale);
	}


	public void remove(Long code) throws AppNotFoundException {

		var sale = find(code);

		repository.delete(sale);
	}

	@Cacheable(value = "salesCache")
	public Page<SaleDTO> findAll(Pageable pageable) {
		return repository.findAll(getLoggedAccount().getId(), pageable);
	}

	@Cacheable(value = "salesCache")
	public Page<SaleDTO> findAll(Pageable pageable, String status, String customerName, BigDecimal lower, BigDecimal upper) {
		return repository.findAll(getLoggedAccount().getId(), pageable, status, customerName, lower, upper);
	}

	@Cacheable(value = "salesCache")
	public List findAll() {
		return repository.findAll();
	}

	@Cacheable(value = "salesCache", key = "#id")
	public Sale findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}

	public Sale find(Long code) throws AppNotFoundException {
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
	}

	@Cacheable(value = "salesCache", key = "#code")
	public SaleDTO findByCode(Long code) throws AppNotFoundException {

		var sale = repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());

		return convert(sale);
	}

	public SaleDTO update(Long code, SaleDTO saleDTO) throws AppNotFoundException {

		var sale = find(code);

		sale.setAmount(saleDTO.getAmount());
		sale.setDiscount(saleDTO.getDiscount());
		sale.setGrossAmount(saleDTO.getGrossAmount());
		sale.setOtherExpenses(saleDTO.getOtherExpenses());
		sale.setStatus(saleDTO.getStatus());
		sale.setUpdatedUser(getPrincipal());

		sale = repository.save(sale);

		return convert(sale);
	}
}
