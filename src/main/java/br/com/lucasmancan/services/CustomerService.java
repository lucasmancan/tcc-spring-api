package br.com.lucasmancan.services;

import br.com.lucasmancan.dtos.AddressDTO;
import br.com.lucasmancan.dtos.CustomerDTO;
import br.com.lucasmancan.dtos.EmailDTO;
import br.com.lucasmancan.dtos.PhoneDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Customer;
import br.com.lucasmancan.models.CustomerAddress;
import br.com.lucasmancan.models.CustomerEmail;
import br.com.lucasmancan.models.CustomerPhone;
import br.com.lucasmancan.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CustomerService extends AbstractService<Customer> {

    @Autowired
    private CustomerRepository repository;


    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper mapper;


    public Customer convert(CustomerDTO dto) {
        return this.convert(dto, null);
    }

    public Customer convert(CustomerDTO dto, Customer customer) {

        if (customer == null) {
            customer = new Customer();
        }

        customer.setActive(dto.getActive());
        customer.setDocument(dto.getDocument());
        customer.setName(dto.getName());
        customer.setType(dto.getType());

        for (AddressDTO addressDTO : dto.getAddresses()) {
            final var customerAddress = new CustomerAddress();
            customerAddress.setCity(addressDTO.getCity());
            customerAddress.setCountry(addressDTO.getCountry());
            customerAddress.setState(addressDTO.getState());
            customerAddress.setStreet(addressDTO.getStreet());
            customerAddress.setNumber(addressDTO.getNumber());
            customerAddress.setZipCode(addressDTO.getZipCode());
            customerAddress.setCustomer(customer);
            customerAddress.setCreationAppUser(getPrincipal());

            if (customer.getAddresses() == null) {
                customer.setAddresses(new HashSet<>());
            }

            customer.getAddresses().add(customerAddress);
        }

        for (PhoneDTO phoneDTO : dto.getPhones()) {
            final var customerPhone = new CustomerPhone();

            customerPhone.setAreaCode(phoneDTO.getAreaCode());
            customerPhone.setCountryCode(phoneDTO.getCountryCode());
            customerPhone.setPhoneNumber(phoneDTO.getPhoneNumber());
            customerPhone.setType(phoneDTO.getContactType());
            customerPhone.setCustomer(customer);
            customerPhone.setCreationAppUser(getPrincipal());

            if (customer.getPhones() == null) {
                customer.setPhones(new HashSet<>());
            }

            customer.getPhones().add(customerPhone);
        }


        for (EmailDTO emailDTO : dto.getEmails()) {
            final var customerEmail = new CustomerEmail();

            customerEmail.setCustomer(customer);
            customerEmail.setCreationAppUser(getPrincipal());
            customerEmail.setEmail(emailDTO.getEmail());
            customerEmail.setType(emailDTO.getContactType());

            if (customer.getEmails() == null) {
                customer.setEmails(new HashSet<>());
            }

            customer.getEmails().add(customerEmail);
        }


        return customer;
    }

    public CustomerDTO convert(Customer customer) {

        var dto = new CustomerDTO();

        dto.setActive(customer.getActive());
        dto.setDocument(customer.getDocument());
        dto.setName(customer.getName());
        dto.setType(customer.getType());

        for (CustomerAddress address : customer.getAddresses()) {
            final var customerAddress = new AddressDTO();

            customerAddress.setCity(address.getCity());
            customerAddress.setCountry(address.getCountry());
            customerAddress.setState(address.getState());
            customerAddress.setStreet(address.getStreet());
            customerAddress.setNumber(address.getNumber());
            customerAddress.setZipCode(address.getZipCode());

            if (customer.getAddresses() == null) {
                dto.setAddresses(new ArrayList<AddressDTO>());
            }

            dto.getAddresses().add(customerAddress);
        }

        for (CustomerPhone customerPhone : customer.getPhones()) {
            final var phoneDTO = new PhoneDTO();

            phoneDTO.setAreaCode(customerPhone.getAreaCode());
            phoneDTO.setCountryCode(customerPhone.getCountryCode());
            phoneDTO.setPhoneNumber(customerPhone.getPhoneNumber());
            phoneDTO.setContactType(customerPhone.getType());

            if (dto.getPhones() == null) {
                dto.setPhones(new ArrayList<>());
            }

            dto.getPhones().add(phoneDTO);
        }


        for (CustomerEmail customerEmail : customer.getEmails()) {
            final var emailDTO = new EmailDTO();


            emailDTO.setEmail(customerEmail.getEmail());
            emailDTO.setContactType(customerEmail.getType());

            if (dto.getEmails() == null) {
                dto.setEmails(new ArrayList<>());
            }

            dto.getEmails().add(emailDTO);
        }

        return dto;
    }

    public CustomerDTO save(CustomerDTO dto) throws AppNotFoundException {

        var customer = convert(dto);

        customer.setAccount(getLoggedAccount());
        customer.setCreationAppUser(getPrincipal());

        customer = repository.save(customer);

        return convert(customer);
    }

    public void remove(Long code) throws AppNotFoundException {
        var customer = find(code);
        repository.delete(customer);
    }

    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(getLoggedAccount().getId(), pageable);
    }

    public Customer find(Long code) throws AppNotFoundException {
        return repository.findByCode(getLoggedAccount().getId(), code).orElseThrow(AppNotFoundException::new);
    }

    public CustomerDTO findByCode(Long code) throws AppNotFoundException {
        var customer = find(code);

        return convert(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public CustomerDTO update(Long code, CustomerDTO customerDTO) throws AppNotFoundException {

        var customer = find(customerDTO.getCode());

        customer = convert(customerDTO, customer);

        return convert(customer);
    }
}
