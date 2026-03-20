package com.pagamento.cobrancapix.service;

import com.pagamento.cobrancapix.model.Cobranca;
import com.pagamento.cobrancapix.repository.CobrancaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;

    // Spring injeta o repository automaticamente
    public CobrancaService(CobrancaRepository cobrancaRepository) {
        this.cobrancaRepository = cobrancaRepository;
    }

    // Cria uma nova cobrança e retorna o link de pagamento
    public String criarCobranca(String linkAnuncio, String emailComprador,
                                String chavePix, BigDecimal valor) {

        // Monta o objeto — o @PrePersist vai gerar token, status e data
        Cobranca cobranca = new Cobranca();
        cobranca.setLinkAnuncio(linkAnuncio);
        cobranca.setEmailComprador(emailComprador);
        cobranca.setChavePix(chavePix);
        cobranca.setValor(valor);

        // Salva no banco — aqui o @PrePersist é chamado automaticamente
        cobrancaRepository.save(cobranca);

        // Retorna o link de pagamento com o token gerado
        return "https://seudominio.com/pagamento/" + cobranca.getToken();
    }

    // Busca uma cobrança pelo token do link
    public Optional<Cobranca> buscarPorToken(String token) {
        return cobrancaRepository.findByToken(token);
    }

    // Marca a cobrança como paga
    public void confirmarPagamento(String token) {
        cobrancaRepository.findByToken(token).ifPresent(cobranca -> {
            cobranca.setStatus("PAGO");
            cobrancaRepository.save(cobranca);
        });
    }
}