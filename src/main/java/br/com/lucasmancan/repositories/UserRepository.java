package br.com.lucasmancan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lucasmancan.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM users where email =:email", nativeQuery = true)
	User findByEmail(String email);
}
