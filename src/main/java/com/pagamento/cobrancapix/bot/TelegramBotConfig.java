package com.pagamento.cobrancapix.bot;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import jakarta.annotation.PostConstruct;

@Configuration
public class TelegramBotConfig {

    private final CobrancaBot cobrancaBot;

    public TelegramBotConfig(CobrancaBot cobrancaBot) {
        this.cobrancaBot = cobrancaBot;
    }

    @PostConstruct
    public void iniciarBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(cobrancaBot);
            System.out.println("✅ Bot do Telegram registrado com sucesso!");
        } catch (TelegramApiException e) {
            System.err.println("❌ Erro ao registrar o bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}