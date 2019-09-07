package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;

@RestControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AppNotFoundException.class, NoResultException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected AppResponse handleNotFound(
            Exception ex, WebRequest request) {
        log.warn("Not Found capturado: " + ex);
        return new AppResponse("Nenhum dado encontrado para consulta", null);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public AppResponse customHandleException(Exception ex, WebRequest request) {
        return AppResponse.OOPS;
    }
}