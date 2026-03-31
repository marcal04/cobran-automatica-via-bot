# 🚀 Quick Start Guide - Cobrança PIX

Comece a usar a aplicação em **5 minutos**!

---

## ⚡ Início Rápido

### 1️⃣ Clonar e Preparar

```bash
git clone https://github.com/seu-usuario/cobranca-pix.git
cd cobranca-pix

# Criar banco de dados
createdb -U postgres cobrancapix
```

### 2️⃣ Configurar Variáveis

**Windows (PowerShell):**
```powershell
$env:TELEGRAM_BOT_TOKEN="seu_token_aqui"
$env:TELEGRAM_BOT_USERNAME="CobrancaPixBot"
$env:DATASOURCE_PASSWORD="boot"
$env:SECURITY_USER_PASSWORD="admin"
```

**Linux/Mac:**
```bash
export TELEGRAM_BOT_TOKEN="seu_token_aqui"
export TELEGRAM_BOT_USERNAME="CobrancaPixBot"
export DATASOURCE_PASSWORD="boot"
export SECURITY_USER_PASSWORD="admin"
```

### 3️⃣ Compilar e Executar

```bash
mvn clean install
mvn spring-boot:run
```

✅ Aplicação estará em: **http://localhost:8080**

---

## 🤖 Usar no Telegram

1. Abrir Telegram
2. Procurar pelo seu bot (ex: `@CobrancaPixBot`)
3. Digite `/start`
4. Seguir as instruções

---

## 🌐 Testar API

```bash
# Buscar cobrança
curl -u admin:admin http://localhost:8080/api/cobranca/token123

# Confirmar pagamento
curl -u admin:admin -X PATCH http://localhost:8080/api/cobranca/token123/pagar
```

---

## 📋 Variáveis Obrigatórias

| Variável | Valor Exemplo |
|----------|---------------|
| `TELEGRAM_BOT_TOKEN` | `8763635702:AAHYgCzGWV92...` |
| `TELEGRAM_BOT_USERNAME` | `CobrancaPixBot` |
| `DATASOURCE_PASSWORD` | `sua_senha_postgres` |
| `SECURITY_USER_PASSWORD` | `sua_senha_admin` |

**Como obter o token Telegram:**
1. Abra [@BotFather](https://t.me/botfather) no Telegram
2. `/newbot`
3. Siga as instruções
4. Copie o token gerado

---

## 🐛 Resolvendo Problemas

### Erro: "TELEGRAM_BOT_TOKEN cannot be null"
```bash
# Verificar se a variável está definida
echo $TELEGRAM_BOT_TOKEN

# Se vazio, executar novamente
export TELEGRAM_BOT_TOKEN="seu_token"
```

### Erro: "Connection refused" (Banco)
```bash
# Verificar se PostgreSQL está rodando
psql -U postgres

# Se não, iniciar:
# Windows: pg_ctl start
# Linux: sudo systemctl start postgresql
# Mac: brew services start postgresql
```

### Porta 8080 em uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

---

## 📚 Próximas Etapas

1. Ler o [README.md](./README.md) completo
2. Ver [ENV_CONFIGURATION.md](./ENV_CONFIGURATION.md) para mais detalhes
3. Explorar os endpoints da API
4. Integrar com seus sistemas

---

## 💡 Dicas Úteis

- Use `.env` em desenvolvimento
- Use variáveis de sistema em produção
- Mantenha suas credenciais seguras
- Consulte os logs para debug

---

**Tem dúvida?** Veja a [documentação completa](./README.md)


