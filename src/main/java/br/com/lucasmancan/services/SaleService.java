package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.AccountSummary;
import br.com.lucasmancan.dtos.AmountByItem;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.dtos.SaleItemDTO;
import br.com.lucasmancan.dtos.sql.AmountByEntity;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.*;
import br.com.lucasmancan.repositories.SaleCustomerRepository;
import br.com.lucasmancan.repositories.SaleItemRepository;
import br.com.lucasmancan.repositories.SaleRepository;
import lombok.extern.log4j.Log4j;
import net.bytebuddy.matcher.CollectionErasureMatcher;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;import java.util.List;

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

    public Sale save(Sale sale) {

        if(sale.getId() == null){
            sale.setCreatedAt(new Date());
        }

        if (sale.getStatus() == null) {
            sale.setStatus(Status.pending);
        }


        if(!CollectionUtils.isEmpty(sale.getItems())){
            sale.setAmount(BigDecimal.ZERO);
            sale.setDiscount(BigDecimal.ZERO);
            sale.setGrossAmount(BigDecimal.ZERO);
            sale.setOtherExpenses(BigDecimal.ZERO);

            for(SaleItem item: sale.getItems()){


                item = this.saveSaleItem(item);

                item.setSale(sale);

                sale.getGrossAmount().add(item.getGrossAmount());
                sale.getAmount().add(item.getAmount());
                sale.getDiscount().add(item.getDiscount());
                sale.getOtherExpenses().add(item.getOtherExpenses());

            }
        }

        if(!CollectionUtils.isEmpty(sale.getCustomers())) {

            for(SaleCustomer saleCustomer: sale.getCustomers()){

                saleCustomer = this.saleSaleCustomer(saleCustomer);

                saleCustomer.setSale(sale);

            }
        }

//        sale.setUpdatedAt(new Date());

        return repository.save(sale);
    }

    private SaleCustomer saleSaleCustomer(SaleCustomer saleCustomer) {
        saleCustomer.setUpdatedAt(new Date());


        if(saleCustomer.getStatus() == null){
            saleCustomer.setStatus(Status.approved);
        }

        if(saleCustomer.getId() == null){
            saleCustomer.setCreatedAt(new Date());
        }

        return saleCustomer;
    }

    private SaleItem saveSaleItem(SaleItem item) {
        if(item.getId() == null){
            item.setCreatedAt(new Date());
        }

        item.setGrossAmount(item.getUnitary()
                .multiply(new BigDecimal(item.getQuantity()))
                .add(item.getOtherExpenses()));

        item.setAmount(item.getGrossAmount().subtract(item.getDiscount()));
        item.setUpdatedAt(new Date());

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
        return repository.findAll(getLoggedAccount().getId(), pageable);
    }

    @Cacheable(value = "salesCache")
    public List<Object[][]> findAll(Pageable pageable, String status, String customerName,String employee, BigDecimal lower, BigDecimal upper) {

        return   getEntityManager().createNamedQuery("Sale.fetchAll")
                .setParameter("accountId", getLoggedAccount().getId())
                .setParameter("status", status)
                .setParameter("employee", employee)
                .setParameter("customerName", customerName)
                .setParameter("lower", lower)
                .setParameter("upper", upper)
                .getResultList();
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
