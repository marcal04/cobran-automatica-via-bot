package com.pagamento.cobrancapix.bot;

import com.pagamento.cobrancapix.model.Vendedor;
import com.pagamento.cobrancapix.service.CobrancaService;
import com.pagamento.cobrancapix.service.VendedorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CobrancaBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final CobrancaService cobrancaService;
    private final VendedorService vendedorService;

    // Memória temporária de cada usuário
    private final Map<Long, String> estadoUsuario = new HashMap<>();
    private final Map<Long, Map<String, String>> dadosUsuario = new HashMap<>();

    public CobrancaBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            CobrancaService cobrancaService,
            VendedorService vendedorService
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.cobrancaService = cobrancaService;
        this.vendedorService = vendedorService;
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

            // ── CADASTRO ──────────────────────────────────────

            case "INICIO":
                if (texto.equals("/start")) {
                    if (vendedorService.existeVendedor(chatId)) {
                        // Vendedor já cadastrado → vai pro menu
                        mostrarMenu(chatId);
                    } else {
                        // Novo vendedor → inicia cadastro
                        enviarMensagem(chatId,
                                "👋 Bem-vindo ao *PagPix*!\n\n" +
                                        "Vou te cadastrar rapidinho.\n\n" +
                                        "📝 Qual o seu *nome completo*?"
                        );
                        estadoUsuario.put(chatId, "CADASTRO_NOME");
                    }
                } else {
                    enviarMensagem(chatId, "Digite /start para começar.");
                }
                break;

            case "CADASTRO_NOME":
                salvarDado(chatId, "nome", texto);
                enviarMensagem(chatId, "✅ Nome salvo!\n\n📧 Qual o seu *email*?");
                estadoUsuario.put(chatId, "CADASTRO_EMAIL");
                break;

            case "CADASTRO_EMAIL":
                if (!texto.contains("@") || !texto.contains(".")) {
                    enviarMensagem(chatId, "❌ Email inválido. Tente novamente:");
                    break;
                }
                salvarDado(chatId, "email", texto);

                Map<String, String> dadosCadastro = dadosUsuario.get(chatId);
                vendedorService.cadastrar(chatId, dadosCadastro.get("nome"), texto);

                dadosUsuario.remove(chatId);
                estadoUsuario.remove(chatId);

                enviarMensagem(chatId,
                        "🎉 Cadastro concluído!\n\n" +
                                "Bem-vindo, " + dadosCadastro.get("nome") + "!"
                );
                mostrarMenu(chatId);
                break;

            // ── MENU ──────────────────────────────────────────

            case "MENU":
                switch (texto) {
                    case "💰 Criar cobrança":
                        enviarMensagem(chatId, "📎 Envie o link do anúncio:");
                        estadoUsuario.put(chatId, "AGUARDANDO_LINK");
                        break;
                    case "📋 Minhas cobranças":
                        mostrarCobranças(chatId);
                        break;
                    case "⚙️ Minha conta":
                        mostrarConta(chatId);
                        break;
                    default:
                        mostrarMenu(chatId);
                        break;
                }
                break;

            // ── CRIAR COBRANÇA ────────────────────────────────

            case "AGUARDANDO_LINK":
                salvarDado(chatId, "link", texto);
                enviarMensagem(chatId, "✅ Link salvo!\n\n📧 Email do comprador:");
                estadoUsuario.put(chatId, "AGUARDANDO_EMAIL");
                break;

            case "AGUARDANDO_EMAIL":
                salvarDado(chatId, "email", texto);
                enviarMensagem(chatId, "✅ Email salvo!\n\n🔑 Sua chave Pix:");
                estadoUsuario.put(chatId, "AGUARDANDO_CHAVE_PIX");
                break;

            case "AGUARDANDO_CHAVE_PIX":
                salvarDado(chatId, "chavePix", texto);
                enviarMensagem(chatId, "✅ Chave Pix salva!\n\n💰 Valor da cobrança: (ex: 150.00)");
                estadoUsuario.put(chatId, "AGUARDANDO_VALOR");
                break;

            case "AGUARDANDO_VALOR":
                salvarDado(chatId, "valor", texto);
                enviarMensagem(chatId, "⏳ Gerando sua cobrança...");

                Map<String, String> dados = dadosUsuario.get(chatId);
                Optional<Vendedor> vendedor = vendedorService.buscarPorChatId(chatId);

                if (vendedor.isEmpty()) {
                    enviarMensagem(chatId, "❌ Vendedor não encontrado. Digite /start para se cadastrar.");
                    estadoUsuario.remove(chatId);
                    dadosUsuario.remove(chatId);
                    break;
                }

                try {
                    String linkPagamento = cobrancaService.criarCobranca(
                            dados.get("link"),
                            dados.get("email"),
                            dados.get("chavePix"),
                            new BigDecimal(dados.get("valor")),
                            vendedor.get()
                    );

                    enviarMensagem(chatId,
                            "✅ Cobrança criada!\n\n" +
                                    "📎 Anúncio: " + dados.get("link") + "\n" +
                                    "📧 Email: " + dados.get("email") + "\n" +
                                    "🔑 Chave Pix: " + dados.get("chavePix") + "\n" +
                                    "💰 Valor: R$ " + dados.get("valor") + "\n\n" +
                                    "🔗 Link de pagamento:\n" + linkPagamento
                    );

                } catch (NumberFormatException e) {
                    enviarMensagem(chatId, "❌ Valor inválido! Use o formato: 150.00");
                    estadoUsuario.put(chatId, "AGUARDANDO_VALOR");
                    return;
                }

                estadoUsuario.remove(chatId);
                dadosUsuario.remove(chatId);
                mostrarMenu(chatId);
                break;

            default:
                estadoUsuario.remove(chatId);
                mostrarMenu(chatId);
                break;
        }
    }

    // ── MÉTODOS AUXILIARES ────────────────────────────────────

    private void mostrarMenu(Long chatId) {
        estadoUsuario.put(chatId, "MENU");
        SendMessage mensagem = new SendMessage();
        mensagem.setChatId(chatId.toString());
        mensagem.setText("🏠 Menu principal — o que deseja fazer?");

        // Teclado de opções
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup teclado =
                new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();
        teclado.setResizeKeyboard(true);

        java.util.List<org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow> linhas = new java.util.ArrayList<>();

        org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow linha1 =
                new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow();
        linha1.add("💰 Criar cobrança");

        org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow linha2 =
                new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow();
        linha2.add("📋 Minhas cobranças");
        linha2.add("⚙️ Minha conta");

        linhas.add(linha1);
        linhas.add(linha2);
        teclado.setKeyboard(linhas);
        mensagem.setReplyMarkup(teclado);

        try {
            execute(mensagem);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void mostrarCobranças(Long chatId) {
        // Por enquanto mostra mensagem simples
        // Vamos implementar listagem depois
        enviarMensagem(chatId, "📋 Em breve você poderá ver suas cobranças aqui!");
        mostrarMenu(chatId);
    }

    private void mostrarConta(Long chatId) {
        vendedorService.buscarPorChatId(chatId).ifPresent(v -> {
            enviarMensagem(chatId,
                    "⚙️ Minha conta\n\n" +
                            "👤 Nome: " + v.getNome() + "\n" +
                            "📧 Email: " + v.getEmail() + "\n" +
                            "📦 Plano: " + v.getPlano() + "\n" +
                            "📅 Desde: " + v.getCriadoEm().toLocalDate()
            );
        });
        mostrarMenu(chatId);
    }

    public void notificarVendedor(Long chatId, String nomeComprador,
                                  String emailComprador, String telefone,
                                  BigDecimal valor) {
        String mensagem =
                "🎉 Pagamento confirmado!\n\n" +
                        "👤 Nome: " + (nomeComprador != null ? nomeComprador : "Não informado") + "\n" +
                        "📧 Email: " + (emailComprador != null ? emailComprador : "Não informado") + "\n" +
                        "📱 Telefone: " + (telefone != null ? telefone : "Não informado") + "\n" +
                        "💰 Valor: R$ " + valor;

        enviarMensagem(chatId, mensagem);
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