package br.com.lucasmancan.controllers;

import br.com.lucasmancan.dtos.AppResponse;
import br.com.lucasmancan.exceptions.AppException;
import br.com.lucasmancan.exceptions.AppNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
@Log4j2
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private AppResponse response;

    public AppExceptionHandler(AppResponse response) {
        this.response = response;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        log.log(Level.INFO, "" + ex.getMessage());
        return new ResponseEntity(
                AppResponse.OOPS, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //    @ExceptionHandler({HttpMessageConversionException.class}IllegalArgumentException.class)
    @ExceptionHandler({HttpMessageConversionException.class, IllegalArgumentException.class})
    public final ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        log.log(Level.INFO, "" + ex.getMessage());

        response.setMessage("Confira os dados informados e tente novamente");
        return new ResponseEntity(
                response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Class<?> type = e.getRequiredType();
        String message;
        if (type.isEnum()) {
            message = "The parameter " + e.getName() + " must have a value among : " + StringUtils.join(type.getEnumConstants(), ", ");
        } else {
            message = "The parameter " + e.getName() + " must be of type " + type.getTypeName();
        }

        e.printStackTrace();
        return new ResponseEntity(
                AppResponse.OOPS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(AppNotFoundException ex, WebRequest request) {

        response.setMessage("Resource not found");
        ex.printStackTrace();
        log.log(Level.INFO, "" + ex.getMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<Object> handleAppException(AppNotFoundException ex, WebRequest request) {

        response.setMessage("Some error ocurred.");
        ex.printStackTrace();
        log.log(Level.INFO, "" + ex.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
