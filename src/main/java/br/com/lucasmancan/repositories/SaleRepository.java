package br.com.lucasmancan.repositories;

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

	@Query(value = "SELECT p FROM Sale p WHERE p.account.id =:accountId")
	Page<SaleDTO> findAll(@Param("accountId") Long accountId, Pageable pageable);
}
