package br.com.lucasmancan.exceptions;

public class AppSecurityContextException extends Exception {

    private static final String DEFAULT_MESSAGE = "Variável de usuário fora do contexto de autenticação.";
    public AppSecurityContextException(String message){
        super(message);
    }
    public AppSecurityContextException(){
        super(DEFAULT_MESSAGE);
    }

}
