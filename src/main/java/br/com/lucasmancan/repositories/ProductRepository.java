package br.com.lucasmancan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lucasmancan.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	   @Query("SELECT p FROM Product p WHERE p.account.id =:accountId and p.code=:code")
	   public Optional<Product> findById(@Param("accountId") Long accountId, @Param("code") Long Code);
	   
	   @Query("SELECT p FROM Product p WHERE p.account.id =:accountId")
	   public List<Product> findAll(@Param("accountId") Long accountId);
}
