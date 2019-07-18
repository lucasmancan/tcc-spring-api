package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
@Log4j2
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private AppResponse response;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        log.log(Level.INFO, "" + ex.getMessage());
        return new ResponseEntity(
                AppResponse.OOPS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(AppNotFoundException ex, WebRequest request) {

        response.setMessage("Not Found Exception");
        log.log(Level.INFO, "" + ex.getMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

}
