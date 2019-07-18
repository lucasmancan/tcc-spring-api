package br.com.lucasmancan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    @Query("SELECT a FROM Address a " +
            "where a.zipCode =:zipCode " +
            "and a.number =:number " +
            "and a.street like :street")
    Optional<Address> findByZipCodeAndStreetAndNumber(Integer zipCode, String street, Integer number);
}
