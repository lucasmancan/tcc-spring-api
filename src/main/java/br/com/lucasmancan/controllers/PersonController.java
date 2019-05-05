package br.com.lucasmancan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasmancan.repositories.PersonRepository;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
}
