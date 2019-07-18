package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppUserDTO;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	public String getUsers() {
		return "{\"users\":[{\"name\":\"Lucas\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"Jackie\",\"country\":\"China\"}]}";
	}

	@ResponseBody
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppUserDTO save(@Valid @RequestBody AppUserDTO dto) throws AppNotFoundException {
		return userService.save(dto);
	}

	@ResponseBody
	@GetMapping("/{code}")
	public AppUserDTO getByCode(@PathVariable("code") Long code) throws AppNotFoundException {
		return userService.findByCode(code);
	}

	@ResponseBody
	@PutMapping("/{code}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppUserDTO update(@PathVariable("code") Long code, @Valid @RequestBody AppUserDTO appUserDTO) throws AppNotFoundException {
		return userService.update(code, appUserDTO);
	}
	
    @GetMapping("/me")
    public ResponseEntity home() {
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity(principal, HttpStatus.OK);
    }
}
