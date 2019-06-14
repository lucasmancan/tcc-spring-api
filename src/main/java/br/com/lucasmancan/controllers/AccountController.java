package br.com.lucasmancan.controllers;

import br.com.lucasmancan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AccountController {

	@Autowired
	private UserRepository personRepository;
	
	@ResponseBody
	public String getUsers() {
		return "{\"users\":[{\"name\":\"Lucas\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"Jackie\",\"country\":\"China\"}]}";
	}

//    @GetMapping("/me")
//    public ResponseEntity home() {
//        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication();
//        return new ResponseEntity(principal, HttpStatus.OK);
//    }
}
