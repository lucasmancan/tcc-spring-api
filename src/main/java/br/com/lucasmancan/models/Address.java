package br.com.lucasmancan.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    public String zipCode;
    public String street;
    public String additionalInformations;
    public String neighboorhood;
    public String city;
    public String state;
    public String unity;
    public String ibge;
    public String gia;

}
