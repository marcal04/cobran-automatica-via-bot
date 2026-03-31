package com.pagamento.cobrancapix.service;

import com.pagamento.cobrancapix.model.Vendedor;
import com.pagamento.cobrancapix.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    // Verifica se o vendedor já está cadastrado
    public boolean existeVendedor(Long chatId) {
        return vendedorRepository.existsByChatId(chatId);
    }

    // Busca o vendedor pelo chatId
    public Optional<Vendedor> buscarPorChatId(Long chatId) {
        return vendedorRepository.findByChatId(chatId);
    }

    // Cadastra um novo vendedor
    public Vendedor cadastrar(Long chatId, String nome, String email) {
        Vendedor vendedor = new Vendedor();
        vendedor.setChatId(chatId);
        vendedor.setNome(nome);
        vendedor.setEmail(email);
        return vendedorRepository.save(vendedor);
    }
}