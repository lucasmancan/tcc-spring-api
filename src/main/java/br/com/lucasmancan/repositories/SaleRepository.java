package br.com.lucasmancan.repositories;

import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT p FROM Sale p JOIN FETCH p.items  JOIN FETCH p.account a JOIN FETCH p.employee LEFT JOIN FETCH p.items WHERE a.id =:accountId and p.code=:code")
	Optional<Sale> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);

	@Query(value = "SELECT p FROM Sale p JOIN FETCH p.items  JOIN FETCH p.employee WHERE p.account.id =:accountId and (:status is null or p.status =:status) and (:customerName is null or p.employee.name =:customerName) and " +
            "((:minAmount is null or p.amount >=:minAmount) " +
			"and (:maxAmount is null or p.amount <=:maxAmount))", countQuery = "select COUNT(s) from Sale s")
	Page<Sale> findAll(@Param("accountId") Long accountId, Pageable pageable, @Param("status") String status, @Param("customerName") String customerName, @Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);

	@Query(value = "SELECT p FROM Sale p JOIN FETCH p.items WHERE p.account.id =:accountId", countQuery = "select COUNT(s) from Sale s")
	Page<SaleDTO> findAll(@Param("accountId") Long accountId, Pageable pageable);
}
