package br.com.lucasmancan.repositories;

import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    List<MessageTemplate> findByName(String name);
}
