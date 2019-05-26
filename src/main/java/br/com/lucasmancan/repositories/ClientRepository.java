package br.com.lucasmancan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lucasmancan.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	   @Query("SELECT p FROM Client p WHERE p.account.id =:accountId and p.code=:code")
	   Optional<Client> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);
	   
	   @Query("SELECT p FROM Client p WHERE p.account.id =:accountId")
	   Page<Client> findAll(@Param("accountId") Long accountId, Pageable pageble);
}
