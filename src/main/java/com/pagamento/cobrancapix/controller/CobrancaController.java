package com.pagamento.cobrancapix.controller;

import com.pagamento.cobrancapix.model.Cobranca;
import com.pagamento.cobrancapix.service.CobrancaService;
import com.pagamento.cobrancapix.service.PreviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pagamento.cobrancapix.dto.DadosCompradorDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cobranca")
@CrossOrigin(origins = "*")
public class CobrancaController {

    private final CobrancaService cobrancaService;
    private final PreviewService previewService;

    // Um único construtor recebendo os dois services
    public CobrancaController(CobrancaService cobrancaService,
                              PreviewService previewService) {
        this.cobrancaService = cobrancaService;
        this.previewService = previewService;
    }

    // GET /api/cobranca/{token}
    @GetMapping("/{token}")
    public ResponseEntity<Cobranca> buscarPorToken(@PathVariable String token) {

        Optional<Cobranca> cobranca = cobrancaService.buscarPorToken(token);

        if (cobranca.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!cobranca.get().getStatus().equals("PENDENTE")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(cobranca.get());
    }

    // PATCH /api/cobranca/{token}/pagar
    @PatchMapping("/{token}/pagar")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable String token) {

        Optional<Cobranca> cobranca = cobrancaService.buscarPorToken(token);

        if (cobranca.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cobrancaService.confirmarPagamento(token);
        return ResponseEntity.ok().build();
    }

    // GET /api/cobranca/preview?url=https://...
    @GetMapping("/preview")
    public ResponseEntity<Map<String, String>> buscarPreview(@RequestParam String url) {
        return ResponseEntity.ok(previewService.buscarPreview(url));
    }

    // PATCH /api/cobranca/{token}/comprador
    @PatchMapping("/{token}/comprador")
    public ResponseEntity<Void> salvarDadosComprador(
            @PathVariable String token,
            @RequestBody DadosCompradorDTO dados) {

        Optional<Cobranca> cobranca = cobrancaService.buscarPorToken(token);

        if (cobranca.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cobrancaService.salvarDadosComprador(token, dados);
        return ResponseEntity.ok().build();
    }
}