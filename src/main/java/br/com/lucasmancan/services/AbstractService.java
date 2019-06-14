package br.com.lucasmancan.services;

import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractService<T> implements AppService<T> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private AppUser user;

    public AbstractService(){
    }

    public Account getLoggedAccount() {
        return getPrincipal().getAccount();
    }

    public AppUser getPrincipal() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication();
    }


}