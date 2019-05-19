package br.com.lucasmancan;

import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.repositories.UserRepository;
import br.com.lucasmancan.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class VendasApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(VendasApiApplication.class, args);




	}
	@GetMapping("/")
	public String teste() {

		return "index";
	}

	@GetMapping("/home")
	public ResponseEntity home(@AuthenticationPrincipal UserDetails user)
	{

		Authentication principal = SecurityContextHolder.getContext().getAuthentication();

		var teste = (HashMap) principal.getDetails();


		return new ResponseEntity(teste.get("user"), HttpStatus.OK);
	}

}
