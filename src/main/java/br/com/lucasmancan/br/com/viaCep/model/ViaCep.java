package br.com.lucasmancan.br.com.viaCep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaCep implements Serializable {

    @JsonProperty("cep")
    public String cep;
    @JsonProperty("logradouro")
    public String logradouro;
    @JsonProperty("complemento")
    public String complemento;
    @JsonProperty("bairro")
    public String bairro;
    @JsonProperty("localidade")
    public String localidade;
    @JsonProperty("uf")
    public String uf;
    @JsonProperty("unidade")
    public String unidade;
    @JsonProperty("ibge")
    public String ibge;
    @JsonProperty("gia")
    public String gia;

}