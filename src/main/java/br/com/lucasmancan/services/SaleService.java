package br.com.lucasmancan.services;

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
import java.util.Optional;

@Service
public class SaleService extends AbstractService<Sale> {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper mapper;

	public Sale convert(SaleDTO saleDTO) throws AppNotFoundException {

		var sale = new Sale();

		sale.setCode(saleDTO.getCode());
		sale.setStatus(saleDTO.getStatus());
		sale.setOtherExpenses(saleDTO.getOtherExpenses());
		sale.setDiscount(saleDTO.getDiscount());
		sale.setAmount(saleDTO.getAmount());
		sale.setGrossAmount(saleDTO.getGrossAmount());
		sale.setUpdatedAt(saleDTO.getUpdatedAt());

		if (sale.getAccount() == null) {
			sale.setAccount(getLoggedAccount());
		}

		if (sale.getEmployee() == null) {
			sale.setEmployee(getPrincipal());
		}

		if (!CollectionUtils.isEmpty(saleDTO.getItems())) {

			sale.setOtherExpenses(BigDecimal.ZERO);
			sale.setDiscount(BigDecimal.ZERO);
			sale.setAmount(BigDecimal.ZERO);
			sale.setGrossAmount(BigDecimal.ZERO);

			for (SaleItemDTO saleItemDTO : saleDTO.getItems()) {

				final SaleItem saleItem = new SaleItem();

				saleItem.setAmount(saleItemDTO.getAmount());
				saleItem.setStatus(saleItemDTO.getStatus());
				saleItem.setGrossAmount(saleItemDTO.getGrossAmount());
				saleItem.setDiscount(saleItemDTO.getDiscount());
				saleItem.setOtherExpenses(saleItemDTO.getOtherExpenses());
				saleItem.setUnitary(saleItemDTO.getUnitary());
				saleItem.setProduct(productService.find(saleItemDTO.getProduct().getCode()));

				saleItem.setGrossAmount(saleItem.getUnitary()
						.multiply(new BigDecimal(saleItem.getQuantity()))
						.add(saleItem.getOtherExpenses()));

				saleItem.setAmount(saleItem.getGrossAmount().subtract(saleItem.getDiscount()));

				sale.setGrossAmount(sale.getGrossAmount().add(saleItem.getGrossAmount()));
				sale.setAmount(sale.getAmount().add(saleItem.getAmount()));
				sale.setDiscount(sale.getDiscount().add(saleItem.getDiscount()));
				sale.setOtherExpenses(sale.getOtherExpenses().add(saleItem.getOtherExpenses()));

				sale.getItems().add(saleItem);
			}
		}

		return sale;
	}

	public SaleDTO convert(Sale sale) {

		var saleDTO = mapper.map(sale, SaleDTO.class);
//
//        saleDTO.setCode(sale.getCode());
//        saleDTO.setStatus(sale.getStatus());
//        saleDTO.setOtherExpenses(sale.getOtherExpenses());
//        saleDTO.setDiscount(sale.getDiscount());
//        saleDTO.setAmount(sale.getAmount());
//        saleDTO.setGrossAmount(sale.getGrossAmount());
//        saleDTO.setUpdatedAt(sale.getUpdatedAt());
//
//        if (!CollectionUtils.isEmpty(sale.getItems())) {
//
//            for (SaleItem saleItem : sale.getItems()) {
//
//                final SaleItemDTO saleItemDTO = new SaleItemDTO();
//                final ProductDTO productDTO = mapper.map(saleItem.getProduct(), ProductDTO.class);
//
//                saleItemDTO.setProduct(productDTO);
//                saleItemDTO.setAmount(saleItem.getAmount());
//                saleItemDTO.setStatus(saleItem.getStatus());
//                saleItemDTO.setGrossAmount(saleItem.getGrossAmount());
//                saleItemDTO.setDiscount(saleItem.getDiscount());
//                saleItemDTO.setOtherExpenses(saleItem.getOtherExpenses());
//                saleItemDTO.setUnitary(saleItem.getUnitary());
//
//                saleDTO.getItems().add(saleItemDTO);
//            }
//        }

		return saleDTO;
	}

	public SaleDTO save(SaleDTO saleDTO) throws AppNotFoundException {

		var sale = convert(saleDTO);

		if (sale.getCode() != null) {
			var savedSale = this.find(sale.getCode());

			sale = this.map(sale, savedSale);
		}

		sale = repository.saveAndFlush(sale);

		Optional<Sale> sale1 = repository.findById(sale.getId());

		System.out.println(sale.getCode());

		return this.convert(sale1.get());
	}

	private Sale map(Sale sale, Sale savedSale) {
		sale.setId(savedSale.getId());
		return sale;
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
		return repository.findAll(getLoggedAccount().getId(), pageable);
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
		return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);
	}

	@Cacheable(value = "salesCache", key = "#code")
	public SaleDTO findByCode(Long code) throws AppNotFoundException {

		var sale = repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);

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
