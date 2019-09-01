package br.com.lucasmancan.controllers;

import br.com.lucasmancan.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ceps")
public class CepsController {

    @Autowired
    private AddressService addressService;
//
//    @ResponseBody
//    @GetMapping("/{cep}")
//    public ViaCepOb getByCode(@PathVariable("cep") Long cep) {
//        return addressService. (code);
//    }
//
//    @ResponseBody
//    @PutMapping("/{code}")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public AppUserDTO update(@PathVariable("code") Long code, @Valid @RequestBody AppUserDTO appUserDTO) throws AppNotFoundException {
//        return userService.update(code, appUserDTO);
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity home() {
//        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication();
//        return new ResponseEntity(principal, HttpStatus.OK);
//    }
}
