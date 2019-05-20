package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppSecurityContextException;
import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public abstract class AbstractService<T> implements AppService<T> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private Authentication principal;

    private HashMap<String, Object> details;

    public AbstractService(){

    }

    public Account getLoggedAccount() throws AppSecurityContextException{
        return accountRepository.findById(Long.parseLong(this.getPrincipalDetails().get("account").toString())).orElseThrow(() -> new AppSecurityContextException());
    }

    public AppUser getLoggedUser() throws AppSecurityContextException {
        return userRepository.findById(Long.parseLong(this.getPrincipalDetails().get("user").toString())).orElseThrow(() -> new AppSecurityContextException());
    }

    public Authentication getPrincipal(){
        if(this.principal == null)
            this.principal = SecurityContextHolder.getContext().getAuthentication();

        return this.principal;
    }

    public HashMap getPrincipalDetails(){
        return (HashMap) this.getPrincipal().getDetails();
    }

}
