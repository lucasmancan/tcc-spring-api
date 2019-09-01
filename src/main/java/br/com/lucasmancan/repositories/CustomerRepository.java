package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT p FROM Customer p JOIN FETCH p.account JOIN FETCH p.addresses JOIN FETCH p.emails JOIN FETCH p.phones WHERE p.account.id =:accountId and p.code=:code")
    Optional<Customer> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);

    @Query("SELECT p FROM Customer p WHERE p.account.id =:accountId")
    Page<Customer> findAll(@Param("accountId") Long accountId, Pageable pageble);
}
