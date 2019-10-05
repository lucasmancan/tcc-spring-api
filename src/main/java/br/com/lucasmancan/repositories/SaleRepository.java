package br.com.lucasmancan.repositories;

import br.com.lucasmancan.dtos.AccountSummary;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT p FROM Sale p JOIN FETCH p.account a JOIN FETCH p.employee LEFT JOIN FETCH p.items WHERE a.id =:accountId and p.code=:code")
	Optional<Sale> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);

//	@Query(value = "SELECT p FROM Sale p JOIN FETCH p.employee " +
//			"WHERE p.account.id =:accountId and " +
//			"(:status is null or p.state =:status) and " +
//			"(:customerName is null or p.employee.name =:customerName) and " +
//			"((:minAmount is null or p.amount >=:minAmount) and (:maxAmount is null or p.amount <=:maxAmount))", countQuery = "select count(s) from Sale s")
//	Page<SaleDTO> findAll(@Param("accountId") Long accountId, Pageable pageable, String status, String customerName, BigDecimal minAmount, BigDecimal maxAmount);

	@Query(value = "SELECT p FROM Sale p WHERE p.account.id =:accountId")
	Page<SaleDTO> findAll(@Param("accountId") Long accountId, Pageable pageable);


	@Query(value = "select sum(amount) as amount,sum(discount) as discount, sum(gross_amount) as grossAmount, sum(other_expenses) as otherExpenses, count(*) as total  from sales where account_id =:accountId group by account_id", nativeQuery = true)
	Object[][] getSummary(@Param("accountId") Long accountId);
}
