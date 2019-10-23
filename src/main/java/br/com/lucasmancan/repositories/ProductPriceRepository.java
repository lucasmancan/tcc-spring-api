package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    List<ProductPrice> findAllByProductId(Long productId);
}
