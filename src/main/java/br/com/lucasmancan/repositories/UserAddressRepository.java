package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {

}
