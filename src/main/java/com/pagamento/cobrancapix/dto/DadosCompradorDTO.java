package com.pagamento.cobrancapix.dto;

import lombok.Data;

@Data
public class DadosCompradorDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}