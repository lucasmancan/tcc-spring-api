package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService extends AbstractService<Account> {

    @Autowired
    private AccountRepository repository;

    public Account save(Account entity) {
        return repository.save(entity);
    }

    public void remove(Account entity) {
        repository.delete(entity);
    }

    public Account findByCode(Long code) throws AppNotFoundException {
        return repository.findById(code).orElseThrow(AppNotFoundException::new);
    }

    public Page<Account> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) throws AppNotFoundException {
        return repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

}
