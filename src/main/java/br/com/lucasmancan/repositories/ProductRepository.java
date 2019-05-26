package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p JOIN fetch p.category JOIN FETCH p.account JOIN FETCH p.creationAppUser WHERE p.account.id =:accountId and p.code=:code")
	   public Optional<Product> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);
	   
	   @Query("SELECT p FROM Product p WHERE p.account.id =:accountId")
	   public List<Product> findAll(@Param("accountId") Long accountId);
}
