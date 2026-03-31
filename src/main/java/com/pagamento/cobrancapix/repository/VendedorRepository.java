package com.pagamento.cobrancapix.repository;

import com.pagamento.cobrancapix.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, UUID> {

    // Busca vendedor pelo chatId do Telegram
    Optional<Vendedor> findByChatId(Long chatId);

    // Verifica se o vendedor já existe
    boolean existsByChatId(Long chatId);
}