package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.MessageTemplate;
import br.com.lucasmancan.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
