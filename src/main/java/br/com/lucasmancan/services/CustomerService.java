package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.CustomerDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Customer;
import br.com.lucasmancan.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends AbstractService<Customer> {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ModelMapper mapper;


    public Customer convert(CustomerDTO dto) {

        var customer = new Customer();

        return customer;

    }

    public CustomerDTO convert(Customer customer) {

        var dto = new CustomerDTO();

        return dto;
    }

    public CustomerDTO save(CustomerDTO dto) throws AppNotFoundException {

        var customer = convert(dto);

        customer.setAccount(getLoggedAccount());
        customer.setCreationAppUser(getPrincipal());


        customer = repository.save(customer);

        return convert(customer);
    }

    public void remove(Customer entity) {
        repository.delete(entity);
    }

    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(getLoggedAccount().getId(), pageable);
    }

    public Customer find(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(() -> new AppNotFoundException());
    }

    public CustomerDTO findByCode(Long code) throws AppNotFoundException {
        var customer = find(code);

        return convert(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public CustomerDTO update(Long code, CustomerDTO customerDTO) {

        return null;
    }
}
