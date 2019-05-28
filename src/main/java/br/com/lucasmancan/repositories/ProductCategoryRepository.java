package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {


    @Query("SELECT p FROM ProductCategory p JOIN FETCH p.account WHERE p.account.id =:accountId and p.code=:code")
    Optional<ProductCategory> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);
}
