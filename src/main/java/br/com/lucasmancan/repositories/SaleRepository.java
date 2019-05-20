package br.com.lucasmancan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lucasmancan.models.Sale;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Long> {
	
	   @Query("SELECT p FROM Sale p WHERE p.account.id =:accountId and p.code=:code")
	   public Optional<Sale> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);
	   
	   @Query("SELECT p FROM Sale p WHERE p.account.id =:accountId")
	   public List<Sale> findAll(@Param("accountId") Long accountId);
}
