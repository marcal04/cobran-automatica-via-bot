package com.pagamento.cobrancapix.service;

import com.pagamento.cobrancapix.bot.CobrancaBot;
import com.pagamento.cobrancapix.dto.DadosCompradorDTO;
import com.pagamento.cobrancapix.model.Cobranca;
import com.pagamento.cobrancapix.model.Vendedor;
import com.pagamento.cobrancapix.repository.CobrancaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;
    private final CobrancaBot cobrancaBot;

    public CobrancaService(CobrancaRepository cobrancaRepository,
                           @Lazy CobrancaBot cobrancaBot) {
        this.cobrancaRepository = cobrancaRepository;
        this.cobrancaBot = cobrancaBot;
    }

    public String criarCobranca(String linkAnuncio, String emailComprador,
                                String chavePix, BigDecimal valor,
                                Vendedor vendedor) {

        Cobranca cobranca = new Cobranca();
        cobranca.setLinkAnuncio(linkAnuncio);
        cobranca.setEmailComprador(emailComprador);
        cobranca.setChavePix(chavePix);
        cobranca.setValor(valor);
        cobranca.setVendedor(vendedor);

        cobrancaRepository.save(cobranca);

        return "https://seudominio.com/pagamento/" + cobranca.getToken();
    }

    public Optional<Cobranca> buscarPorToken(String token) {
        return cobrancaRepository.findByToken(token);
    }

    public void confirmarPagamento(String token) {
        cobrancaRepository.findByToken(token).ifPresent(cobranca -> {
            cobranca.setStatus("PAGO");
            cobranca.setPagoEm(LocalDateTime.now());
            cobrancaRepository.save(cobranca);

            if (cobranca.getVendedor() != null) {
                cobrancaBot.notificarVendedor(
                        cobranca.getVendedor().getChatId(),
                        cobranca.getNomeComprador(),
                        cobranca.getEmailComprador(),
                        cobranca.getTelefoneComprador(),
                        cobranca.getValor()
                );
            }
        });
    }

    public void salvarDadosComprador(String token, DadosCompradorDTO dados) {
        cobrancaRepository.findByToken(token).ifPresent(cobranca -> {
            cobranca.setNomeComprador(dados.getNome());
            cobranca.setCpfComprador(dados.getCpf());
            cobranca.setEmailComprador(dados.getEmail());
            cobranca.setTelefoneComprador(dados.getTelefone());
            cobrancaRepository.save(cobranca);
        });
    }
}