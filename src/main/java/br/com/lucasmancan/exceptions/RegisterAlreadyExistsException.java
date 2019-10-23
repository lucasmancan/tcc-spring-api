package br.com.lucasmancan.exceptions;

public class RegisterAlreadyExistsException extends Exception {

    private String name;
    public RegisterAlreadyExistsException(String s, String name) {
        super(s);
        this.name = name;
    }
}
