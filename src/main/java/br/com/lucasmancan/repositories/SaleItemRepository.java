package br.com.lucasmancan.repositories;

import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

	@Query("SELECT si FROM SaleItem si JOIN FETCH si.sale JOIN FETCH si.product WHERE si.id=:id")
	Optional<SaleItem> findById(@Param("id") Long id);

	@Query(value = "SELECT si FROM SaleItem si JOIN FETCH si.sale s JOIN FETCH si.product WHERE s.id=:saleId")
	List<SaleItem> findAll(@Param("saleId") Long saleId);

}
