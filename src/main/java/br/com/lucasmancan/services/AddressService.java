package br.com.lucasmancan.services;

import br.com.lucasmancan.br.com.viaCep.model.Proxy;
import br.com.lucasmancan.br.com.viaCep.model.ViaCep;
import br.com.lucasmancan.exceptions.WebRequestException;
import br.com.lucasmancan.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService implements Proxy {


    @Autowired
    private RestTemplate restTemplate;

    public Address getByCep(Long code) throws WebRequestException {

        try {
            var respose = this.restTemplate.getForEntity(Proxy.URL, ViaCep.class);

            if (respose.getStatusCode().isError()) {
                throw new WebRequestException();
            }

            var viaCep = respose.getBody();
            return convert(viaCep);

        } catch (WebRequestException e) {
            throw e;
        }

    }

    private Address convert(ViaCep viaCep) {
        var address = new Address();

        address.setCity(viaCep.getLocalidade());
        address.setState(viaCep.getUf());
        address.setUnity(viaCep.getUnidade());
        address.setGia(viaCep.getGia());
        address.setIbge(viaCep.getIbge());
        address.setNeighboorhood(viaCep.getBairro());
        address.setStreet(viaCep.getLogradouro());
        address.setAdditionalInformations(viaCep.getComplemento());


        return address;
    }
}
