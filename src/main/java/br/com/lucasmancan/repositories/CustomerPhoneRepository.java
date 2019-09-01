package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.CustomerPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, Long> {

}
