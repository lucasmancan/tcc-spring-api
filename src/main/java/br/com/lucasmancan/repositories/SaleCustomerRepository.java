package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.SaleCustomer;
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
public interface SaleCustomerRepository extends JpaRepository<SaleCustomer, Long> {

	@Query("SELECT si FROM SaleCustomer si JOIN FETCH si.sale JOIN FETCH si.customer WHERE si.id=:id")
	Optional<SaleCustomer> findById(@Param("id") Long id);

	@Query(value = "SELECT si FROM SaleCustomer si JOIN FETCH si.sale s JOIN FETCH si.customer WHERE s.id=:saleId")
	List<SaleCustomer> findAll(@Param("saleId") Long saleId);

}
