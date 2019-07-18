package br.com.lucasmancan.br.com.viaCep.model;

import br.com.lucasmancan.exceptions.WebRequestException;
import br.com.lucasmancan.models.Address;

public interface Proxy {
    String URL = "https://viacep.com.br/ws/01001000/json/";

    Address getByCep(Long cep) throws WebRequestException;
}
