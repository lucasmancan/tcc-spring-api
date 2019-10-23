package br.com.lucasmancan.repositories;

import br.com.lucasmancan.dtos.AccountSummary;
import br.com.lucasmancan.dtos.AmountByItem;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.dtos.sql.AmountByEntity;
import br.com.lucasmancan.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT p FROM Sale p  WHERE p.account.id =:accountId and p.code=:code")
	Optional<Sale> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);

	@Query(value = "SELECT p FROM Sale p WHERE p.account.id =:accountId")
	Page<SaleDTO> findAll(@Param("accountId") Long accountId, Pageable pageable);

	@Query(value = "select sum(amount) as amount,sum(discount) as discount, sum(gross_amount) as grossAmount, sum(other_expenses) as otherExpenses, count(*) as total  from sales where account_id =:accountId group by account_id", nativeQuery = true)
	Object[][] getSummary(@Param("accountId") Long accountId);


}
