package br.com.lucasmancan.services;

import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Data
public abstract class AbstractService<T> implements AppService<T> {


    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    protected Account getLoggedAccount() {
        return getPrincipal().getAccount();
    }

    protected AppUser getPrincipal() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication();
    }

}
