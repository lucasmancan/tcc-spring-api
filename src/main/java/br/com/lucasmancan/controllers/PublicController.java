package br.com.lucasmancan.controllers;

import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.AccountRepository;
import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/public")
public class PublicController {


    @Autowired
    private UserRepository repository;


    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String getMessage() {
        return "API is running...";
    }

    @GetMapping("/save")
    @ResponseBody
    public ResponseEntity saveUser() {


        var account = new Account();
        account.setActive(true);
        account.setCreatedAt(new Date());

        account = accountRepository.save(account);

        var user = new AppUser("lucas",
                new BCryptPasswordEncoder().encode("root"));

        user.setAccount(account);
        user.setActive(true);
        user.setExpired(false);
        user.setAdmin(true);

        return new ResponseEntity(repository.save(user), HttpStatus.CREATED);
    }
}