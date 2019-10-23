package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AccountDTO;
import br.com.lucasmancan.dtos.RegisterForm;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.PasswordDoenstMatchException;
import br.com.lucasmancan.exceptions.RegisterAlreadyExistsException;
import br.com.lucasmancan.repositories.UserRepository;
import br.com.lucasmancan.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserRepository personRepository;
	
	@ResponseBody
	public String getUsers() {
		return "{\"users\":[{\"name\":\"Lucas\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"Jackie\",\"country\":\"China\"}]}";
	}

    @GetMapping("/")
    @ResponseBody
    public List<AccountDTO> list() {

		var accounts = accountService.findAll();


        return accounts;
    }


    @PostMapping("/")
    public void create(RegisterForm form) throws RegisterAlreadyExistsException, PasswordDoenstMatchException, AppNotFoundException {
          accountService.register(form);
    }


}
