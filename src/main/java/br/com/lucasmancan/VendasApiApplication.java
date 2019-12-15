package br.com.lucasmancan;

import br.com.lucasmancan.models.*;
import br.com.lucasmancan.repositories.*;
import br.com.lucasmancan.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableCaching
public class VendasApiApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerService customerRepository;


    public static void main(String[] args) {
        SpringApplication.run(VendasApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//
//        for (int i = 0; i < 50; i++) {
//            createProduct(true);
//        }
//
//        createSales();

    }

    public AppUser createUser() {

        AppUser user = userRepository.findAll().stream().findAny().orElse(null);

        if (user == null) {

            user = new AppUser();
            Account account = createAccount();

            user = new AppUser("lucas",
                    new BCryptPasswordEncoder().encode("root"));

            user.setAccount(account);
            user.setActive(true);
            user.setExpired(false);


            user = userRepository.save(user);
        }


        return user;
    }


    private Account createAccount() {
        Account account = (Account) accountRepository.findAll().stream().findAny().orElse(null);

        if (account == null) {
            account = new Account();
            account.setActive(true);
            account.setCreatedAt(LocalDateTime.now());

            account = accountRepository.save(account);
        }

        return account;
    }

    public void createSales() {
        for (int i = 0; i < 50; i++) {
            var sale = new Sale();
            sale.setAmount(new BigDecimal(Math.random() * 1000));
            sale.setDiscount(new BigDecimal(Math.random() * 1000));
            sale.setGrossAmount(new BigDecimal(Math.random() * 1000));
            sale.setOtherExpenses(new BigDecimal(Math.random() * 1000));
            sale.setAccount(createAccount());
            sale.setEmployee(createUser());

            for (int j = 0; j < 20; j++) {
                var saleItem = new SaleItem();
                saleItem.setAmount(new BigDecimal(Math.random() * 1000));
                saleItem.setDiscount(new BigDecimal(Math.random() * 1000));
                saleItem.setGrossAmount(new BigDecimal(Math.random() * 1000));
                saleItem.setOtherExpenses(new BigDecimal(Math.random() * 1000));
                saleItem.setUnitary(new BigDecimal(Math.random() * 1000));
                saleItem.setProduct(createProduct(false));
                saleItem.setQuantity(1);
                saleItem.setSale(sale);

                sale.getItems().add(saleItem);
            }

            saleRepository.save(sale);

        }
    }

    private Product createProduct(Boolean criar) {
        Product product = null;
        if (!criar) {
            product = productRepository.findAll().stream().findAny().orElse(null);
        }


        if (product == null || criar) {

            product = new Product();
            product.setCategory(createCategory());
            product.setAccount(createAccount());
            product.setDescription("" + Math.random() * 19999999);
            product.setName("" + Math.random() * 19999999);

            product = productRepository.save(product);
        }


        return product;
    }

    private ProductCategory createCategory() {
        ProductCategory product = productCategoryRepository.findAll().stream().findAny().orElse(null);

        if (product == null) {

            product = new ProductCategory();
            product.setAccount(createAccount());
            product.setDescription("" + Math.random() * 19999999);
            product.setName("" + Math.random() * 19999999);

            product = productCategoryRepository.save(product);
        }

        return product;
	}
}
