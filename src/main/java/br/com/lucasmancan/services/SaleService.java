package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.AccountSummary;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.dtos.sql.AmountByEntity;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleCustomer;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.models.Status;
import br.com.lucasmancan.repositories.SaleCustomerRepository;
import br.com.lucasmancan.repositories.SaleItemRepository;
import br.com.lucasmancan.repositories.SaleRepository;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j
public class SaleService extends AbstractService<Sale> {

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private SaleCustomerRepository customerRepository;

    @Autowired
    private SaleRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    public SaleDTO convert(Sale sale) {
        var saleDTO = mapper.map(sale, SaleDTO.class);
        return saleDTO;
    }

    public Sale save(Sale sale) throws AppNotFoundException {

        if(sale.getId() == null){
            sale.setCreatedAt(LocalDateTime.now());
            sale.setEmployee(getPrincipal());
            sale.setAccount(getLoggedAccount());
        }

        if (sale.getStatus() == null) {
            sale.setStatus(Status.pending);
        }

        if(sale.getAdditionalValues() == null){
            sale.setAdditionalValues(BigDecimal.ZERO);
        }

        if(sale.getDiscount() == null){
            sale.setDiscount(BigDecimal.ZERO);
        }

        if(sale.getOtherExpenses() == null){
            sale.setOtherExpenses(BigDecimal.ZERO);
        }

        sale.setAmount(BigDecimal.ZERO);
        sale.setGrossAmount(BigDecimal.ZERO);

        if(!CollectionUtils.isEmpty(sale.getItems())){
            sale.setDiscount(BigDecimal.ZERO);

            sale.setAmount(BigDecimal.ZERO);
            sale.setGrossAmount(BigDecimal.ZERO);
            sale.setOtherExpenses(BigDecimal.ZERO);

            for(SaleItem item: sale.getItems()){
                item = this.saveSaleItem(item);

                item.setSale(sale);

                sale.setGrossAmount(sale.getGrossAmount().add(item.getGrossAmount()));;
                sale.setDiscount(sale.getDiscount().add(item.getDiscount()));
                sale.setOtherExpenses(sale.getOtherExpenses().add(item.getOtherExpenses()));
            }
        }


        sale.setAmount(sale.getGrossAmount().subtract(sale.getDiscount()).subtract(sale.getOtherExpenses()).add(sale.getAdditionalValues()));

        if(!CollectionUtils.isEmpty(sale.getCustomers())) {
            for(SaleCustomer saleCustomer: sale.getCustomers()){
                saleCustomer = this.saleSaleCustomer(saleCustomer);
                saleCustomer.setSale(sale);
            }
        }

        sale.setUpdatedAt(LocalDateTime.now());


        if(sale.getAccount() == null){
            sale.setAccount(getLoggedAccount());
        }

        sale = repository.save(sale);

        getEntityManager().detach(sale);

        return repository.fetchAllById(sale.getId()).orElseThrow(AppNotFoundException::new);
    }

    private SaleCustomer saleSaleCustomer(SaleCustomer saleCustomer) {
        saleCustomer.setUpdatedAt(LocalDateTime.now());


        if(saleCustomer.getStatus() == null){
            saleCustomer.setStatus(Status.approved);
        }

        if(saleCustomer.getId() == null){
            saleCustomer.setCreatedAt(LocalDateTime.now());
        }

        return saleCustomer;
    }

    private SaleItem saveSaleItem(SaleItem item) {
        if(item.getId() == null){
            item.setCreatedAt(LocalDateTime.now());
        }


        item.setUnitary(item.getProduct().getPrice());
        item.setGrossAmount(item.getProduct().getPrice()
                .multiply(new BigDecimal(item.getQuantity()))
                .add(item.getOtherExpenses()));

        item.setAmount(item.getGrossAmount().subtract(item.getDiscount()));
        item.setUpdatedAt(LocalDateTime.now());

        return item;
    }


    private Sale map(Sale sale, Sale oldSale) {
        sale.setId(oldSale.getId());
        sale.setCode(oldSale.getCode());
        return sale;
    }


    public SaleItem removeItem(Long id) throws AppNotFoundException {

        var saleItem = saleItemRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Item not found!"));

        saleItem.setStatus(Status.deleted);

        return saleItemRepository.save(saleItem);
    }

    public List<SaleCustomer> listCustomers(Long saleId) throws AppNotFoundException {
        return customerRepository.findAll(saleId);
    }
    public List<SaleItem> listItems(Long saleId) throws AppNotFoundException {
        return saleItemRepository.findAll(saleId);
    }

    public SaleCustomer removeCustomer(Long id) throws AppNotFoundException {

        var saleCustomer = customerRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Sale customer not found not found!"));

        saleCustomer.setStatus(Status.deleted);

        return customerRepository.save(saleCustomer);
    }

    public void remove(Long code) throws AppNotFoundException {

        var sale = find(code);

        sale.setStatus(Status.deleted);

        repository.save(sale);
    }

    @Cacheable(value = "salesCache")
    public Page<SaleDTO> findAll(Pageable pageable) {
//        return repository.findAll(getLoggedAccount().getId(), pageable);

        return null;
    }

//    @Cacheable(value = "salesCache")
    public Page<SaleDTO> findAll(Pageable pageable, String status, String customerName, String employee) {

//
//
//        List x =   getEntityManager().createNamedQuery("Sale.fetchAll")
//                .setParameter("accountId", getLoggedAccount().getId())
//                .setParameter("status", status)
//                .setParameter("employee", employee)
//                .setParameter("customerName", customerName)
//                .setParameter("lower", lower)
//                .setParameter("upper", upper)
//                .getResultList();

        Status statusw = status == null ? null : Status.valueOf(status);

        return repository.findAll(getLoggedAccount().getId(), statusw, pageable);
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
    public Sale findByCode(Long code) throws AppNotFoundException {

        var sale = repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);

        return sale;
    }

    public Sale update(Long code, Sale sale) throws AppNotFoundException {
        var oldSale = find(code);

       sale = map(sale, oldSale);

        return save(sale);
    }

    public AccountSummary getSummary() {


        var result =  repository.getSummary(getLoggedAccount().getId());

        AccountSummary accountSummary = new AccountSummary();

        accountSummary.setAmount((BigDecimal) result[0][0]);
        accountSummary.setDiscount((BigDecimal) result[0][1]);
        accountSummary.setGrossAmount((BigDecimal) result[0][2]);
        accountSummary.setOtherExpenses((BigDecimal) result[0][3]);
        accountSummary.setTotal((BigInteger) result[0][4]);

        return accountSummary;
    }

    public List<AmountByEntity>  getCustomerSummary() {

        return getEntityManager().createNamedQuery("Sale.fetchAmountByCustomer", AmountByEntity.class)
                .setParameter("accountId", getLoggedAccount().getId())
                .getResultList();
    }

    public List<AmountByEntity> getProductSummary() {

        return getEntityManager().createNamedQuery("Sale.fetchAmountByProduct", AmountByEntity.class)
                .setParameter("accountId", getLoggedAccount().getId())
                .getResultList();
    }
}
