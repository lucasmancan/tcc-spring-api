package br.com.lucasmancan.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomRepository {
    void detachEntity(Object u);

    void refresh(Object u);
}
