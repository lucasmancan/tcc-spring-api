package br.com.lucasmancan.controllers;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Client;
import br.com.lucasmancan.services.ClientService;
import br.com.lucasmancan.utils.AppPaginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ResponseBody
    @GetMapping
    public ResponseEntity getAll(@PageableDefault(page = 0, size = 30) @RequestParam("page") int page, @RequestParam("size") int size) {
        try {

            var products = clientService.findAll(new AppPaginator(page, size));

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @GetMapping("/{code}")
    public ResponseEntity getByCode(@PathVariable("code") Long code) {
        try {

            var product = clientService.findByCode(code);

            return ResponseEntity.ok(product);

        } catch (AppNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generate")
    public ResponseEntity generate() {

        for (var i = 0; i < 50; i++) {

            Client client = new Client();
            client.setName("Client teste " + 1);
            client.setActive(true);
            client.setDocument(String.valueOf(new Date().getTime()));

            clientService.save(client);
        }

        return ResponseEntity.ok().build();
    }
}
