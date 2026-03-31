# 🔐 Refatoração de Segurança - Variáveis de Ambiente

## ✅ O que foi feito

### 1. **Arquivo de Configuração (`application.properties`)**
- ✅ Token do Telegram Telegram Bot removido e substituído por `${TELEGRAM_BOT_TOKEN}`
- ✅ Username do bot substituído por `${TELEGRAM_BOT_USERNAME}`
- ✅ Senha do banco de dados substituída por `${DATASOURCE_PASSWORD}`
- ✅ Usuário e senha de segurança substituídos por `${SECURITY_USER_NAME}` e `${SECURITY_USER_PASSWORD}`
- ✅ URLs mantêm valores padrão com fallback: `${DATASOURCE_URL:...}`

### 2. **Dependência Maven adicionada**
- ✅ `dotenv-java` (v3.0.0) - Para suportar arquivo `.env`

### 3. **Classe de Configuração criada**
- ✅ `EnvironmentConfig.java` - Carrega automaticamente variáveis do arquivo `.env`

### 4. **Arquivos de documentação**
- ✅ `.env.example` - Exemplo de variáveis necessárias
- ✅ `ENV_CONFIGURATION.md` - Guia completo de configuração
- ✅ `.gitignore` - Atualizado para proteger arquivos sensíveis

## 📋 Próximos Passos

1. **Configure as variáveis de ambiente:**
   ```bash
   cp .env.example .env
   # Edite o arquivo .env com seus valores reais
   ```

2. **Execute o Maven:**
   ```bash
   mvn clean install
   ```

3. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

## 🔒 Variáveis Obrigatórias

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `TELEGRAM_BOT_TOKEN` | Token do bot Telegram | `8763635702:AAHYg...` |
| `TELEGRAM_BOT_USERNAME` | Nome de usuário | `CobrancaPixBot` |
| `DATASOURCE_PASSWORD` | Senha PostgreSQL | `sua_senha` |
| `SECURITY_USER_PASSWORD` | Senha admin | `sua_senha_admin` |

## 📚 Variáveis com Padrão (Opcionais)

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/cobrancapix` | URL do banco |
| `DATASOURCE_USERNAME` | `postgres` | Usuário do BD |
| `SECURITY_USER_NAME` | `admin` | Usuário admin |

## 🚀 Métodos de Configuração

### Windows - Variáveis de Sistema
```powershell
$env:TELEGRAM_BOT_TOKEN="seu_token"
$env:DATASOURCE_PASSWORD="sua_senha"
```

### Linux/Mac - Variáveis de Ambiente
```bash
export TELEGRAM_BOT_TOKEN="seu_token"
export DATASOURCE_PASSWORD="sua_senha"
```

### Docker
```bash
docker run -e TELEGRAM_BOT_TOKEN=token -e DATASOURCE_PASSWORD=senha ...
```

### IDE (IntelliJ IDEA)
1. Run → Edit Configurations
2. Environment variables → insira as variáveis

## ⚠️ Segurança

- ✅ Arquivo `.env` está no `.gitignore`
- ✅ Nenhuma credencial em código
- ✅ Suporta múltiplos ambientes (dev, staging, prod)
- ✅ Compatível com Docker e CI/CD

## 📖 Para Mais Informações

Consulte o arquivo `ENV_CONFIGURATION.md`

