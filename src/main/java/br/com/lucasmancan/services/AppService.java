package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface AppService<T> {

    T save(T entity);

    void remove(T entity);

    <T> T findById(Long id) throws AppNotFoundException;

    <T> T findByCode(Long code) throws AppNotFoundException;

    Page<T> findAll(Pageable pageable);

    Collection<T> findAll();

}
