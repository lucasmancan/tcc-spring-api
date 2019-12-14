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

    @Query("SELECT p FROM Product p LEFT JOIN fetch p.category LEFT JOIN FETCH p.account  WHERE p.account.id =:accountId and p.code=:code")
    Optional<Product> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);


    @Query("SELECT p FROM Product p  WHERE  p.id=:id")
    Optional<Product> findById(@Param("id") Long id);

//    @Query(value = "SELECT p FROM Product p JOIN FETCH p.category c " +
//            " WHERE p.account.id =:accountId AND (:name is null or p.name=:name) AND (:categoryName is null or c.description =:categoryName)", countQuery = "select cp) from Product p ")
//    Page<Product> findAll(@Param("accountId") Long accountId, Pageable pageable, @Param("name") String name, @Param("categoryName") String categoryName);

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.account LEFT JOIN FETCH p.category  c WHERE p.account.id =:accountId AND (:name is null or p.name like CONCAT('%',:name,'%')) AND (:categoryName is null or c.name like CONCAT('%',:categoryName,'%'))", countQuery = "SELECT count(p) from Product p")
    Page<Product> findAll(@Param("accountId") Long accountId, Pageable pageable, String name, String categoryName);

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.account LEFT JOIN FETCH p.category  c WHERE p.account.id =:accountId AND (:name is null or p.name like CONCAT('%',:name,'%')) AND (:categoryName is null or c.name like CONCAT('%',:categoryName,'%'))")
    List<Product> findAll(Long accountId, String name, String categoryName);
}
