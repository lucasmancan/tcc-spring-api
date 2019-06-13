package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.UserAddress;
import br.com.lucasmancan.models.UserPhone;
import org.springframework.data.repository.CrudRepository;

public interface UserPhoneRepository extends CrudRepository<UserPhone, Long> {

}
