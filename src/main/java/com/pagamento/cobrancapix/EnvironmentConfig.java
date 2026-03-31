package com.pagamento.cobrancapix;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {
    
    static {
        // Carrega as variáveis do arquivo .env se existir
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
            
            // Define as variáveis de ambiente do .env como variáveis do sistema
            dotenv.entries().forEach(entry -> {
                if (System.getProperty(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });
        } catch (Exception e) {
            // Se não encontrar .env, continua normalmente
            System.out.println("Arquivo .env não encontrado. Usando variáveis de ambiente do sistema.");
        }
    }
}

