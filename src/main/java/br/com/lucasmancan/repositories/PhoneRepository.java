package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Address, Long> {

}
