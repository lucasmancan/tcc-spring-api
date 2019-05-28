package br.com.lucasmancan.repositories;

import br.com.lucasmancan.dtos.ProductDTO;
import br.com.lucasmancan.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<ProductDTO> findAll(@Param("accountId") Long accountId);

    @Query(value = "SELECT p FROM Product p WHERE p.account.id =:accountId")
    Page<ProductDTO> findAll(@Param("accountId") Long accountId, Pageable pageable);

}
