package com.pagamento.cobrancapix.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "cobrancas")
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Token público — usado na URL do link de pagamento
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String chavePix;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String emailComprador;

    private String linkAnuncio;

    private String nomeComprador;

    private String cpfComprador;

    // Status: PENDENTE, PAGO, EXPIRADO
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime pagoEm;

    // Executado automaticamente antes de salvar no banco
    @PrePersist
    public void prePersist() {
        this.token = UUID.randomUUID().toString();
        this.status = "PENDENTE";
        this.criadoEm = LocalDateTime.now();
    }
}
