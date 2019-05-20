package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.exceptions.AppSecurityContextException;

import java.util.List;

public interface AppService<T> {

    T save(T entity);

    void remove(T entity);

    <T> T findById(Long id) throws AppNotFoundException;

    <T> T findByCode(Long code) throws AppNotFoundException, AppSecurityContextException;

    List<T> findAll() throws AppSecurityContextException;

}
