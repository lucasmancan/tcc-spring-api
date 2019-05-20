package br.com.lucasmancan.exceptions;

import lombok.Data;

@Data
public class AppNotFoundException extends Exception {
    private String message;
    private static final String DEFAULT_MESSAGE = "Nenhum registro encontrado.";


    public AppNotFoundException(){
        super(DEFAULT_MESSAGE);
    }


    public AppNotFoundException(String message){
        super(message);
    }

}
