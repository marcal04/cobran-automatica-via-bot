# 📊 Diagramas de Fluxo - Cobrança PIX

Visualização dos fluxos principais da aplicação.

---

## 🤖 Fluxo 1: Cadastro de Vendedor

```
┌─────────────┐
│   Usuario   │
└──────┬──────┘
       │ /start
       ↓
┌──────────────────────────────────────────┐
│   Bot Telegram                           │
│   "Bem-vindo ao PagPix!"                │
│   "Qual o seu nome completo?"            │
└──────┬───────────────────────────────────┘
       │ Nome: João Silva
       ↓
┌──────────────────────────────────────────┐
│   Bot Telegram                           │
│   "✅ Nome salvo!"                       │
│   "Qual o seu email?"                    │
└──────┬───────────────────────────────────┘
       │ Email: joao@example.com
       ↓
┌──────────────────────────────────────────┐
│   CobrancaBot (Java)                     │
│   - Valida email                         │
│   - Chama VendedorService.cadastrar()    │
└──────┬───────────────────────────────────┘
       │
       ↓
┌──────────────────────────────────────────┐
│   PostgreSQL - Tabela: vendedores        │
│   INSERT INTO vendedores(...)            │
│   id, chat_id, nome, email, plano, ...   │
└──────┬───────────────────────────────────┘
       │
       ↓
┌──────────────────────────────────────────┐
│   Bot Telegram                           │
│   "🎉 Cadastro concluído!"               │
│   "Bem-vindo, João Silva!"               │
└──────────────────────────────────────────┘
```

---

## 💳 Fluxo 2: Criar Cobrança

```
┌─────────────────────────────────────────────────────────────┐
│   Vendedor (Bot Telegram)                                   │
│   /nova_cobranca                                            │
└────┬────────────────────────────────────────────────────────┘
     │
     ├─ Email do comprador
     │  (comprador@example.com)
     │
     ├─ Valor
     │  (R$ 150.00)
     │
     ├─ Link do anúncio
     │  (https://site.com/anuncio/123)
     │
     ├─ Chave PIX
     │  (user@example.com)
     │
     ↓
┌─────────────────────────────────────────────────────────────┐
│   CobrancaBot (Java)                                        │
│   cobrancaService.criarCobranca()                           │
│   - Gera UUID como ID                                      │
│   - Gera Token único                                       │
│   - Salva dados                                            │
└────┬────────────────────────────────────────────────────────┘
     │
     ↓
┌─────────────────────────────────────────────────────────────┐
│   PostgreSQL - Tabela: cobrancas                           │
│   INSERT INTO cobrancas(...)                               │
│   id, token, valor, email_comprador, vendedor_id, ...     │
└────┬────────────────────────────────────────────────────────┘
     │
     ↓
┌─────────────────────────────────────────────────────────────┐
│   Bot Telegram                                              │
│   "✅ Cobrança criada!"                                     │
│   "Link: https://app.com/pagamento/abc123def456"           │
│   [Compartilhar Link]                                       │
└─────────────────────────────────────────────────────────────┘
```

---

## 👥 Fluxo 3: Comprador Clica no Link

```
┌──────────────────────────────────────────────┐
│   Comprador recebe link                      │
│   https://app.com/pagamento/abc123def456    │
└────┬─────────────────────────────────────────┘
     │ Clica no link
     ↓
┌──────────────────────────────────────────────┐
│   Aplicação Web (Frontend)                   │
│   GET /api/cobranca/{token}                  │
└────┬─────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Spring Boot - CobrancaController                       │
│   @GetMapping("/{token}")                               │
│   - Valida token                                        │
│   - Verifica status (deve ser PENDENTE)                 │
│   - Retorna dados da cobrança                           │
└────┬─────────────────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Frontend - Exibe Dados                                 │
│   ┌──────────────────────────────────┐                  │
│   │ iPhone 13 Pro - R$ 150,00        │                  │
│   │ Vendedor: João Silva             │                  │
│   │ Email: joao@example.com          │                  │
│   │                                  │                  │
│   │ Chave PIX: user@example.com      │                  │
│   │                                  │                  │
│   │ [Copiar Chave] [Confirmar Pago] │                  │
│   │                                  │                  │
│   │ Nome Comprador: [    ]           │                  │
│   │ CPF: [           ]               │                  │
│   │ Telefone: [         ]            │                  │
│   └──────────────────────────────────┘                  │
└────┬─────────────────────────────────────────────────────┘
     │ Comprador preenche dados
     │ e clica "Confirmar Pago"
     ↓
┌──────────────────────────────────────────────────────────┐
│   Frontend - PATCH /api/cobranca/{token}/comprador      │
│   Salva: nome, CPF, telefone                            │
└────┬─────────────────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Frontend - PATCH /api/cobranca/{token}/pagar          │
│   Marca cobrança como paga                              │
└────┬─────────────────────────────────────────────────────┘
```

---

## ✅ Fluxo 4: Confirmação de Pagamento

```
┌─────────────────────────────────────────────┐
│   Comprador clica "Confirmar Pago"          │
│   Frontend: PATCH /api/cobranca/{token}/pagar
└────┬────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Spring Boot - CobrancaController                      │
│   @PatchMapping("/{token}/pagar")                       │
│   - Valida token                                       │
│   - Chama cobrancaService.confirmarPagamento()         │
└────┬─────────────────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   CobrancaService.confirmarPagamento()                  │
│   - Muda status para "PAGO"                            │
│   - Define pagoEm = NOW()                              │
│   - Salva em banco                                     │
│   - Chama cobrancaBot.notificarVendedor()              │
└────┬─────────────────────────────────────────────────────┘
     │
     ├─ UPDATE cobrancas SET status = 'PAGO', pagoEm = NOW()
     │  WHERE token = ?
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   PostgreSQL - Atualiza Tabela                          │
│   status: PENDENTE → PAGO                              │
│   pagoEm: 2024-03-31 14:30:00                          │
└────┬─────────────────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Telegram Bot - Notifica Vendedor                      │
│   cobrancaBot.notificarVendedor(chatId, ...)           │
│   SendMessage: "💰 Novo Pagamento Recebido!"            │
│   ├─ Comprador: João Silva                             │
│   ├─ Email: comprador@example.com                      │
│   ├─ CPF: 12345678900                                  │
│   ├─ Telefone: 11999999999                             │
│   └─ Valor: R$ 150,00                                  │
└────┬────────────────────────────────────────────────────┘
     │
     ↓
┌──────────────────────────────────────────────────────────┐
│   Telegram - Vendedor Recebe Notificação               │
│   ✅ Novo pagamento recebido com sucesso!               │
│   Dados do comprador são exibidos                      │
└──────────────────────────────────────────────────────────┘
```

---

## 🏗 Fluxo 5: Arquitetura de Camadas

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENT LAYER                           │
│                                                             │
│   ┌──────────────┐           ┌──────────────┐             │
│   │   Telegram   │           │   Browser    │             │
│   │     Bot      │           │    (HTTP)    │             │
│   └──────┬───────┘           └──────┬───────┘             │
│          │                          │                      │
└──────────┼──────────────────────────┼──────────────────────┘
           │                          │
           │  Telegram API            │  REST API
           │                          │
┌──────────┼──────────────────────────┼──────────────────────┐
│          ↓                          ↓                      │
│   ┌────────────────────────────────────────┐              │
│   │    Spring Boot Application             │              │
│   │    (PORT: 8080)                        │              │
│   └────────┬─────────────────────────────┬─┘              │
│            │                             │                │
│   PRESENTATION LAYER                    │                │
│   ┌────────────────────────────────────┐ │                │
│   │   CobrancaController (REST)        │ │                │
│   │   - GET /api/cobranca/{token}      │ │                │
│   │   - PATCH /api/cobranca/*/pagar    │ │                │
│   │   - PATCH /api/cobranca/*/comprador│ │                │
│   │   - GET /api/cobranca/preview      │ │                │
│   │                                    │ │                │
│   │   CobrancaBot                      │ │                │
│   │   - onUpdateReceived()             │ │                │
│   │   - enviarMensagem()               │ │                │
│   └────────┬─────────────────────────┬─┘ │                │
│            │                         │    │                │
│   BUSINESS LOGIC LAYER              │    │                │
│   ┌────────────────────────────────┐ │    │                │
│   │ CobrancaService                │ │    │                │
│   │ - criarCobranca()              │ │    │                │
│   │ - buscarPorToken()             │ │    │                │
│   │ - confirmarPagamento()         │ │    │                │
│   │ - salvarDadosComprador()       │ │    │                │
│   │                                │ │    │                │
│   │ VendedorService                │ │    │                │
│   │ - existeVendedor()             │ │    │                │
│   │ - buscarPorChatId()            │ │    │                │
│   │ - cadastrar()                  │ │    │                │
│   │                                │ │    │                │
│   │ PreviewService                 │ │    │                │
│   │ - buscarPreview()              │ │    │                │
│   └────────┬────────────────────┬──┘ │    │                │
│            │                    │     │    │                │
│   DATA ACCESS LAYER             │     │    │                │
│   ┌────────────────────────────┐ │     │    │                │
│   │ CobrancaRepository         │ │     │    │                │
│   │ - findByToken()            │ │     │    │                │
│   │ - save()                   │ │     │    │                │
│   │                            │ │     │    │                │
│   │ VendedorRepository         │ │     │    │                │
│   │ - findByChatId()           │ │     │    │                │
│   │ - existsByChatId()         │ │     │    │                │
│   │ - save()                   │ │     │    │                │
│   └────────┬────────────────────┘ │    │                │
│            │                      │    │                │
└────────────┼──────────────────────┼────┼────────────────┘
             │                      │    │
             │                      │    │
┌────────────┼──────────────────────┼────┼────────────────┐
│            ↓                      ↓    │                │
│       ┌────────────────────────────┐   │                │
│       │   PostgreSQL Database      │   │                │
│       │                            │   │                │
│       │  ┌──────────────────────┐  │   │                │
│       │  │ vendedores           │  │   │                │
│       │  │ - id (UUID)          │  │   │                │
│       │  │ - chat_id (BIGINT)   │  │   │                │
│       │  │ - nome (VARCHAR)     │  │   │                │
│       │  │ - email (VARCHAR)    │  │   │                │
│       │  │ - plano (VARCHAR)    │  │   │                │
│       │  └──────────────────────┘  │   │                │
│       │                            │   │                │
│       │  ┌──────────────────────┐  │   │                │
│       │  │ cobrancas            │  │   │                │
│       │  │ - id (UUID)          │  │   │                │
│       │  │ - token (VARCHAR)    │  │   │                │
│       │  │ - valor (DECIMAL)    │  │   │                │
│       │  │ - status (VARCHAR)   │  │   │                │
│       │  │ - vendedor_id (FK)   │  │   │                │
│       │  └──────────────────────┘  │   │                │
│       └────────────────────────────┘   │                │
│                                        │                │
└────────────────────────────────────────┼────────────────┘
                                         │
                                         └─────────────────→ ✅
```

---

## 🔄 Fluxo 6: Ciclo de Vida de uma Cobrança

```
┌─────────────────────────────────────────────────────────────┐
│                  CICLO DE VIDA - COBRANÇA                   │
└─────────────────────────────────────────────────────────────┘

     CREATE                           CHECK
       ↓                                ↓
   ┌───────┐                        ┌──────────┐
   │       │                        │          │
   │GERADA │                        │ PENDENTE │
   │       │                        │  (Espera)│
   └───────┘                        └──────────┘
       │                                │
       │ Vendedor solicita cobrança    │ Comprador paga
       │                                │
       ├──────────────────────────────→ └──→ ┌─────┐
       │                                     │     │
       │                                     │PAGO │ ✅
       │                                     │     │
       │                                     └─────┘
       │
       │ Nenhum pagamento + 30 dias
       │
       └──────────────────────────────→ ┌──────────┐
                                        │          │
                                        │ EXPIRADO │ ⏰
                                        │          │
                                        └──────────┘

ESTADOS:
- GERADA: Cobrança criada (transição interna)
- PENDENTE: Aguardando pagamento
- PAGO: Pagamento confirmado ✅
- EXPIRADO: Passou 30 dias sem pagamento ⏰
```

---

## 📱 Fluxo 7: Estados do Bot Telegram

```
┌────────────────────────────────────────────────────┐
│           MÁQUINA DE ESTADOS - BOT                 │
└────────────────────────────────────────────────────┘

ESTADOS:
├── INICIO
│   └─ Aguarda /start
│      ├─ Novo? → CADASTRO_NOME
│      └─ Já cadastrado? → MENU
│
├── CADASTRO_NOME
│   └─ Aguarda nome
│      └─ Válido? → CADASTRO_EMAIL
│
├── CADASTRO_EMAIL
│   └─ Aguarda email
│      ├─ Válido? → Salva vendor → MENU
│      └─ Inválido? → CADASTRO_EMAIL (repete)
│
├── MENU
│   └─ Exibe opções:
│      ├─ "Criar Cobrança" → NOVA_COBRANCA
│      ├─ "Minhas Cobranças" → MINHAS_COBRANCAS
│      ├─ "Atualizar PIX" → ATUALIZAR_PIX
│      └─ "Sair" → INICIO
│
├── NOVA_COBRANCA
│   ├─ Aguarda email → NOVA_COBRANCA_VALOR
│   └─ Aguarda valor → NOVA_COBRANCA_ANUNCIO
│       └─ Aguarda link → Salva → MENU
│
├── MINHAS_COBRANCAS
│   └─ Exibe lista → MENU
│
├── ATUALIZAR_PIX
│   └─ Aguarda chave PIX → Salva → MENU

TRANSIÇÕES:
/start     → INICIO
/menu      → MENU
/nova_cobranca → NOVA_COBRANCA
/sair      → INICIO
timeout    → MENU
```

---

## 🔐 Fluxo 8: Autenticação e Segurança

```
┌─────────────────────────────────────────────────┐
│          FLUXO DE AUTENTICAÇÃO HTTP              │
└─────────────────────────────────────────────────┘

CLIENT REQUEST:
┌────────────────────────────────────────────┐
│ GET /api/cobranca/token123                 │
│ Authorization: Basic YWRtaW46YWRtaW4=     │
│ (base64 encoded: admin:admin)              │
└────────┬─────────────────────────────────┘
         │
         ↓
┌────────────────────────────────────────────────┐
│ Spring Security - SecurityConfig              │
│ - Decodifica Authorization header             │
│ - Extrai username e password                  │
│ - Valida contra credenciais                   │
└────────┬─────────────────────────────────────┘
         │
         ├─ Válido? → Próximo
         │
         └─ Inválido? → 401 Unauthorized
                        └─ Retorna erro

CREDENCIAIS:
┌─────────────────────────────────────────────┐
│ Usuario: admin (SECURITY_USER_NAME)         │
│ Senha: admin (SECURITY_USER_PASSWORD)       │
│ Carregadas de: Variáveis de Ambiente        │
│ Não estão no código ✅                      │
└─────────────────────────────────────────────┘

FLUXO DE VARIÁVEIS:
┌───────────────────────────────────────────────┐
│ 1. Application inicia                         │
│ 2. EnvironmentConfig carrega .env (se existe) │
│ 3. Spring Boot lê application.properties      │
│ 4. Substitui ${VAR} pelas vars de ambiente   │
│ 5. SecurityConfig injeta credenciais         │
│ 6. Aplicação usa credenciais para auth       │
└───────────────────────────────────────────────┘
```

---

## 📊 Estatísticas de Fluxos

| Fluxo | Atores | Transações | Tempo Esperado |
|-------|--------|-----------|---|
| Cadastro | 1 | 2 | ~1 min |
| Criar Cobrança | 2 | 3 | ~2 min |
| Comprador Paga | 2 | 5 | ~5 min |
| Notificação | 1 | 1 | <1 seg |

---

**Última atualização:** 31 de Março de 2026


