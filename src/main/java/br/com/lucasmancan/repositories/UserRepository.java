package br.com.lucasmancan.repositories;

import java.util.Optional;

import br.com.lucasmancan.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser, Long> {

	@Query("SELECT p FROM AppUser p WHERE p.email =:email")
	Optional<AppUser> findByEmailLogin(String email);

	Optional<AppUser> findByEmail(String email);
	
	@Query("SELECT p FROM AppUser p WHERE p.account.id =:accountId and p.code=:code")
	Optional<AppUser> findByCode(@Param("accountId") Long accountId, @Param("code") Long Code);

    Optional<AppUser> findByName(String name);

	@Query("SELECT p FROM AppUser p WHERE p.account.id =:id and p.email=:email")
	Optional<AppUser> findByAccountIdAndUserEmail(Long id, String email);
}
