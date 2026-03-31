package com.pagamento.cobrancapix.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ChatId único do Telegram — é o identificador principal
    @Column(nullable = false, unique = true)
    private Long chatId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    // Plano do vendedor: GRATUITO, PRO
    @Column(nullable = false)
    private String plano;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.plano = "GRATUITO";
        this.ativo = true;
        this.criadoEm = LocalDateTime.now();
    }
}