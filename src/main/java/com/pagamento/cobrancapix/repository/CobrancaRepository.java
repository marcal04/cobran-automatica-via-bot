package com.pagamento.cobrancapix.repository;

import com.pagamento.cobrancapix.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, UUID> {

    // Spring gera o SQL automaticamente pelo nome do metodo
    Optional<Cobranca> findByToken(String token);
}