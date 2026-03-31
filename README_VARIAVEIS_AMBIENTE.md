# 🔐 Refatoração de Segurança - Variáveis de Ambiente

## 📌 Resumo da Refatoração

Todas as **senhas, tokens e credenciais** foram removidas do código e agora são carregadas através de **variáveis de ambiente**. Isso melhora drasticamente a segurança da aplicação.

---

## 🚀 Como Começar (Windows)

### 1. Abra o PowerShell e execute:

```powershell
# Defina as variáveis de ambiente (sessão atual)
$env:TELEGRAM_BOT_TOKEN="seu_token_aqui"
$env:TELEGRAM_BOT_USERNAME="seu_username_aqui"
$env:DATASOURCE_PASSWORD="sua_senha_postgres"
$env:SECURITY_USER_PASSWORD="sua_senha_admin"

# Para permanente (requer Admin):
[Environment]::SetEnvironmentVariable("TELEGRAM_BOT_TOKEN", "seu_token", "User")
[Environment]::SetEnvironmentVariable("DATASOURCE_PASSWORD", "sua_senha_postgres", "User")
[Environment]::SetEnvironmentVariable("SECURITY_USER_PASSWORD", "sua_senha_admin", "User")
```

### 2. Inicie a aplicação:

```powershell
mvn clean install
mvn spring-boot:run
```

---

## 🚀 Como Começar (Linux/Mac)

### 1. Abra o terminal e execute:

```bash
# Defina as variáveis de ambiente (sessão atual)
export TELEGRAM_BOT_TOKEN="seu_token_aqui"
export TELEGRAM_BOT_USERNAME="seu_username_aqui"
export DATASOURCE_PASSWORD="sua_senha_postgres"
export SECURITY_USER_PASSWORD="sua_senha_admin"

# Inicie a aplicação
mvn clean install
mvn spring-boot:run
```

### 2. Para persistência (opcional):

```bash
# Adicione ao ~/.bashrc ou ~/.zshrc
echo 'export TELEGRAM_BOT_TOKEN="seu_token"' >> ~/.bashrc
echo 'export DATASOURCE_PASSWORD="sua_senha"' >> ~/.bashrc
source ~/.bashrc
```

---

## 📂 Arquivos Criados/Modificados

| Arquivo | Tipo | Descrição |
|---------|------|-----------|
| `application.properties` | ✏️ Modificado | Senhas substituídas por variáveis |
| `EnvironmentConfig.java` | ✅ Novo | Carrega `.env` automaticamente |
| `.env.example` | ✅ Novo | Exemplo de variáveis |
| `.gitignore` | ✏️ Modificado | Protege `.env` e credenciais |
| `ENV_CONFIGURATION.md` | ✅ Novo | Guia completo |
| `setup-env.bat` | ✅ Novo | Script de configuração Windows |
| `setup-env.sh` | ✅ Novo | Script de configuração Linux/Mac |
| `pom.xml` | ✏️ Modificado | Adicionada dependência `dotenv-java` |

---

## 🔒 Variáveis Necessárias

### Obrigatórias ⚠️

```env
TELEGRAM_BOT_TOKEN=seu_token_telegram
DATASOURCE_PASSWORD=sua_senha_postgres
SECURITY_USER_PASSWORD=sua_senha_admin
```

### Opcionais (com padrão)

```env
TELEGRAM_BOT_USERNAME=CobrancaPixBot
DATASOURCE_URL=jdbc:postgresql://localhost:5432/cobrancapix
DATASOURCE_USERNAME=postgres
SECURITY_USER_NAME=admin
```

---

## 🔍 Antes vs Depois

### ❌ ANTES (Inseguro)
```properties
telegram.bot.token=8763635702:AAHYgCzGWV92-gj8zc187BuBjo7_KLz3vEA
spring.datasource.password=boot
spring.security.user.password=admin
```

### ✅ DEPOIS (Seguro)
```properties
telegram.bot.token=${TELEGRAM_BOT_TOKEN}
spring.datasource.password=${DATASOURCE_PASSWORD}
spring.security.user.password=${SECURITY_USER_PASSWORD}
```

---

## 💡 Métodos de Configuração (por preferência)

### 1️⃣ Arquivo `.env` (Recomendado para Desenvolvimento)
```bash
cp .env.example .env
# Edite o arquivo .env com seus valores
```

### 2️⃣ Variáveis de Sistema (Windows)
```powershell
setx TELEGRAM_BOT_TOKEN "token"
setx DATASOURCE_PASSWORD "senha"
```

### 3️⃣ IDE (IntelliJ IDEA)
- Run → Edit Configurations → Environment variables

### 4️⃣ Docker
```bash
docker run -e TELEGRAM_BOT_TOKEN=token ...
```

### 5️⃣ CI/CD (GitHub Actions, GitLab CI, etc)
```yaml
env:
  TELEGRAM_BOT_TOKEN: ${{ secrets.TELEGRAM_BOT_TOKEN }}
  DATASOURCE_PASSWORD: ${{ secrets.DATASOURCE_PASSWORD }}
```

---

## ✅ Checklist de Segurança

- ✅ Senhas removidas do código
- ✅ Arquivo `.env` protegido no `.gitignore`
- ✅ Arquivo `application-local.properties` protegido
- ✅ Suporta múltiplos ambientes
- ✅ Compatível com Docker
- ✅ Compatível com CI/CD
- ✅ Documentação completa
- ✅ Scripts de configuração automática

---

## 🐛 Troubleshooting

### Variáveis não carregam?
1. Verifique se as variáveis estão definidas: `echo $TELEGRAM_BOT_TOKEN`
2. Reinicie a aplicação
3. Verifique os logs da aplicação

### Erro de banco de dados?
```
java.sql.SQLException: The password is null or empty.
```
Solução: Defina `DATASOURCE_PASSWORD` e `SECURITY_USER_PASSWORD`

### Erro no bot Telegram?
```
Exception in thread "main" java.lang.IllegalArgumentException: Bot token cannot be empty.
```
Solução: Defina `TELEGRAM_BOT_TOKEN` e `TELEGRAM_BOT_USERNAME`

---

## 📚 Referências Úteis

- [Spring Boot - Externalized Configuration](https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-3)
- [12factor - Config](https://12factor.net/config)
- [OWASP - Sensitive Data Exposure](https://owasp.org/www-project-top-ten/)
- [dotenv-java GitHub](https://github.com/cdimascio/dotenv-java)

---

## 💬 Dúvidas?

Consulte os arquivos:
- `ENV_CONFIGURATION.md` - Guia detalhado
- `SECURITY_REFACTOR.md` - Resumo técnico
- `.env.example` - Exemplo de variáveis

---

**Data da Refatoração:** 2026-03-31  
**Status:** ✅ Concluído e Pronto para Uso

