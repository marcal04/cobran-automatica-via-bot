# Configuração de Variáveis de Ambiente

Este projeto foi refatorado para usar **variáveis de ambiente** em vez de credenciais hardcoded no código. Isso melhora significativamente a segurança.

## 📋 Variáveis Necessárias

As seguintes variáveis de ambiente precisam ser configuradas:

### Telegram Bot
- `TELEGRAM_BOT_TOKEN`: Token do seu bot Telegram (obter em @BotFather)
- `TELEGRAM_BOT_USERNAME`: Nome de usuário do bot

### Banco de Dados (PostgreSQL)
- `DATASOURCE_URL`: URL de conexão do PostgreSQL (padrão: `jdbc:postgresql://localhost:5432/cobrancapix`)
- `DATASOURCE_USERNAME`: Usuário do PostgreSQL (padrão: `postgres`)
- `DATASOURCE_PASSWORD`: Senha do PostgreSQL (⚠️ OBRIGATÓRIO)

### Segurança
- `SECURITY_USER_NAME`: Usuário para acesso HTTP Basic (padrão: `admin`)
- `SECURITY_USER_PASSWORD`: Senha para acesso HTTP Basic (⚠️ OBRIGATÓRIO)

## 🚀 Como Configurar

### Opção 1: Arquivo `.env` (Desenvolvimento)

1. Copie o arquivo de exemplo:
```bash
cp .env.example .env
```

2. Edite o arquivo `.env` com seus valores:
```env
TELEGRAM_BOT_TOKEN=seu_token_aqui
TELEGRAM_BOT_USERNAME=seu_username_aqui
DATASOURCE_PASSWORD=sua_senha_postgres
SECURITY_USER_PASSWORD=sua_senha_admin
```

3. Para usar o arquivo `.env` com Spring Boot, você pode:
   - Usar a biblioteca `dotenv-java` (adicionar ao `pom.xml`)
   - Ou carregar manualmente via classe configuration

### Opção 2: Variáveis de Sistema (Windows)

```powershell
[Environment]::SetEnvironmentVariable("TELEGRAM_BOT_TOKEN", "seu_token", "User")
[Environment]::SetEnvironmentVariable("TELEGRAM_BOT_USERNAME", "seu_username", "User")
[Environment]::SetEnvironmentVariable("DATASOURCE_PASSWORD", "sua_senha_postgres", "User")
[Environment]::SetEnvironmentVariable("SECURITY_USER_PASSWORD", "sua_senha_admin", "User")
```

### Opção 3: Variáveis no Docker

Se usar Docker, passe as variáveis:
```bash
docker run -e TELEGRAM_BOT_TOKEN=token -e DATASOURCE_PASSWORD=senha ...
```

### Opção 4: arquivo `application-local.properties` (Desenvolvimento)

1. Crie um arquivo `application-local.properties` (será ignorado pelo git):
```properties
telegram.bot.token=seu_token_aqui
telegram.bot.username=seu_username_aqui
spring.datasource.password=sua_senha_postgres
spring.security.user.password=sua_senha_admin
```

2. Configure a profile ativa no `application.properties`:
```properties
spring.profiles.active=local
```

## ⚠️ Segurança

- **Nunca** faça commit do arquivo `.env` (já está no `.gitignore`)
- **Nunca** compartilhe suas credenciais
- Use senhas fortes em produção
- Considere usar um gerenciador de secrets como:
  - AWS Secrets Manager
  - HashiCorp Vault
  - Azure Key Vault
  - Google Secret Manager

## ✅ Verificação

Para verificar se as variáveis estão sendo lidas corretamente, o aplicativo inicializará com logs mostrando as configurações carregadas.

## 🔗 Referências

- [Spring Boot Environment Variables](https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-3)
- [12factor - Config](https://12factor.net/config)

