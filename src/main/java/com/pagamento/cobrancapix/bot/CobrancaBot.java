package com.pagamento.cobrancapix.bot;

import com.pagamento.cobrancapix.service.CobrancaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CobrancaBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final CobrancaService cobrancaService;

    // Memória temporária de cada usuário
    private final Map<Long, String> estadoUsuario = new HashMap<>();
    private final Map<Long, Map<String, String>> dadosUsuario = new HashMap<>();

    public CobrancaBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            CobrancaService cobrancaService
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.cobrancaService = cobrancaService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        Long chatId = update.getMessage().getChatId();
        String texto = update.getMessage().getText().trim();
        String estado = estadoUsuario.getOrDefault(chatId, "INICIO");

        switch (estado) {

            case "INICIO":
                if (texto.equals("/start")) {
                    enviarMensagem(chatId, "👋 Olá! Vamos criar uma cobrança.\n\n📎 Envie o link do anúncio:");
                    estadoUsuario.put(chatId, "AGUARDANDO_LINK");
                }
                break;

            case "AGUARDANDO_LINK":
                salvarDado(chatId, "link", texto);
                enviarMensagem(chatId, "✅ Link salvo!\n\n📧 Agora envie o email do comprador:");
                estadoUsuario.put(chatId, "AGUARDANDO_EMAIL");
                break;

            case "AGUARDANDO_EMAIL":
                salvarDado(chatId, "email", texto);
                enviarMensagem(chatId, "✅ Email salvo!\n\n🔑 Qual é a sua chave Pix?");
                estadoUsuario.put(chatId, "AGUARDANDO_CHAVE_PIX");
                break;

            case "AGUARDANDO_CHAVE_PIX":
                salvarDado(chatId, "chavePix", texto);
                enviarMensagem(chatId, "✅ Chave Pix salva!\n\n💰 Qual o valor da cobrança? (ex: 150.00)");
                estadoUsuario.put(chatId, "AGUARDANDO_VALOR");
                break;

            case "AGUARDANDO_VALOR":
                salvarDado(chatId, "valor", texto);
                enviarMensagem(chatId, "⏳ Gerando sua cobrança...");

                Map<String, String> dados = dadosUsuario.get(chatId);

                try {
                    String linkPagamento = cobrancaService.criarCobranca(
                            dados.get("link"),
                            dados.get("email"),
                            dados.get("chavePix"),
                            new BigDecimal(dados.get("valor"))
                    );

                    enviarMensagem(chatId,
                            "✅ Cobrança criada!\n\n" +
                                    "📎 Link do anúncio: " + dados.get("link") + "\n" +
                                    "📧 Email: " + dados.get("email") + "\n" +
                                    "🔑 Chave Pix: " + dados.get("chavePix") + "\n" +
                                    "💰 Valor: R$ " + dados.get("valor") + "\n\n" +
                                    "🔗 Link de pagamento enviado ao comprador:\n" + linkPagamento
                    );

                } catch (NumberFormatException e) {
                    enviarMensagem(chatId, "❌ Valor inválido! Use o formato: 150.00");
                    estadoUsuario.put(chatId, "AGUARDANDO_VALOR");
                    return;
                }

                estadoUsuario.remove(chatId);
                dadosUsuario.remove(chatId);
                break;

            default:
                enviarMensagem(chatId, "Digite /start para começar.");
                break;
        }
    }

    private void salvarDado(Long chatId, String chave, String valor) {
        dadosUsuario.computeIfAbsent(chatId, k -> new HashMap<>()).put(chave, valor);
    }

    private void enviarMensagem(Long chatId, String texto) {
        SendMessage mensagem = new SendMessage();
        mensagem.setChatId(chatId.toString());
        mensagem.setText(texto);
        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}