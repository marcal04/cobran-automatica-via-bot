# 💳 Cobrança PIX - Sistema de Cobrança com Bot Telegram

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?style=flat-square&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=flat-square&logo=postgresql)
![Telegram Bot](https://img.shields.io/badge/Telegram%20Bot%20API-Latest-0088CC?style=flat-square&logo=telegram)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## 📋 Sumário

- [Visão Geral](#-visão-geral)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [Como Usar](#-como-usar)
- [API REST](#-api-rest)
- [Bot Telegram](#-bot-telegram)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Banco de Dados](#-banco-de-dados)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Deployment](#-deployment)
- [Troubleshooting](#-troubleshooting)
- [Contribuição](#-contribuição)

---

## 🎯 Visão Geral

**Cobrança PIX** é um sistema moderno de gerenciamento de cobranças via PIX, integrado com um bot Telegram. Permite que vendedores criem links de pagamento, recebam notificações em tempo real sobre pagamentos e gerenciem suas transações de forma simples e eficiente.

### Casos de Uso

- ✅ E-commerce com cobrança PIX
- ✅ Vendedores autônomos
- ✅ Plataformas de anúncios com pagamento
- ✅ Integração com sistemas de vendas
- ✅ Notificações em tempo real via Telegram

---

## ⚡ Funcionalidades

### 🤖 Bot Telegram
- ✅ **Cadastro de Vendedores** - Registro rápido via chat
- ✅ **Criação de Cobranças** - Gerar links de pagamento
- ✅ **Gerenciamento de Chaves PIX** - Adicionar/atualizar chaves
- ✅ **Notificações em Tempo Real** - Receber alertas de pagamentos
- ✅ **Histórico de Transações** - Consultar cobranças
- ✅ **Interface Amigável** - Botões e teclados interativos

### 🌐 API REST
- ✅ **GET /api/cobranca/{token}** - Buscar cobranças por token
- ✅ **PATCH /api/cobranca/{token}/pagar** - Confirmar pagamento
- ✅ **PATCH /api/cobranca/{token}/comprador** - Salvar dados do comprador
- ✅ **GET /api/cobranca/preview** - Buscar preview de anúncios

### 🔐 Segurança
- ✅ **Autenticação HTTP Basic** - Proteção de endpoints
- ✅ **Variáveis de Ambiente** - Sem credenciais no código
- ✅ **Tokens Únicos** - Cada cobrança tem token próprio
- ✅ **Validação de Email** - Verificação de dados

### 💼 Funcionalidades de Negócio
- ✅ **Planos de Vendedores** - Gratuito e Pro
- ✅ **Status de Cobranças** - PENDENTE, PAGO, EXPIRADO
- ✅ **Dados do Comprador** - Nome, CPF, telefone, email
- ✅ **Links de Anúncios** - Rastreamento de origem

---

## 🛠 Tecnologias

### Backend
| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 21 | Linguagem de programação |
| Spring Boot | 4.0.3 | Framework web |
| Spring Data JPA | Latest | ORM e acesso a dados |
| Spring Security | Latest | Autenticação e autorização |
| Spring Validation | Latest | Validação de dados |

### Banco de Dados
| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| PostgreSQL | 16+ | Banco de dados relacional |
| Hibernate | Latest | ORM |

### Integrações
| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Telegram Bot API | 6.9.7.1 | Bot Telegram |
| JSoup | 1.17.2 | Scraping de web pages |
| dotenv-java | 3.0.0 | Carregamento de .env |

### Build & DevOps
| Tecnologia | Descrição |
|------------|-----------|
| Maven | Gerenciador de dependências |
| Docker | Containerização |
| Git | Controle de versão |

---

## 🏗 Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                    CLIENTE/TELEGRAM                      │
├─────────────────────────────────────────────────────────┤
│                   Bot Telegram API                       │
├─────────────────────────────────────────────────────────┤
│              Spring Boot Application                     │
├─────────────────────────────────────────────────────────┤
│  Controller Layer     │  Service Layer    │  Repository  │
│  (REST API)          │  (Business Logic) │  (Data Access)│
├─────────────────────────────────────────────────────────┤
│                 PostgreSQL Database                      │
└─────────────────────────────────────────────────────────┘
```

### Camadas da Aplicação

```
com.pagamento.cobrancapix/
├── controller/          # Endpoints REST
├── service/             # Lógica de negócio
├── repository/          # Acesso a dados
├── model/               # Entidades JPA
├── dto/                 # Data Transfer Objects
├── bot/                 # Bot Telegram
└── config/              # Configurações (Security, Environment)
```

---

## 📋 Pré-requisitos

### Sistema Operacional
- Windows 10+, macOS 10.14+, ou Linux (Ubuntu 20.04+)

### Software Necessário
- **Java 21+** - [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **PostgreSQL 16+** - [Download](https://www.postgresql.org/download/)
- **Git** - [Download](https://git-scm.com/)

### Contas Online
- **Telegram Bot** - Criar em [@BotFather](https://t.me/botfather)
- **Chave PIX** - Para aceitar pagamentos

### Verificação de Pré-requisitos

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar PostgreSQL
psql --version
```

---

## 🚀 Instalação

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/cobranca-pix.git
cd cobranca-pix
```

### 2. Criar Banco de Dados PostgreSQL

```sql
-- Conectar ao PostgreSQL
psql -U postgres

-- Criar banco de dados
CREATE DATABASE cobrancapix;

-- Criar usuário (opcional)
CREATE USER cobranca_user WITH PASSWORD 'sua_senha_aqui';
ALTER ROLE cobranca_user WITH LOGIN;
ALTER DATABASE cobrancapix OWNER TO cobranca_user;
```

Ou usando command line:

```bash
createdb -U postgres cobrancapix
```

### 3. Instalar Dependências Maven

```bash
mvn clean install
```

### 4. Compilar o Projeto

```bash
mvn compile
```

---

## ⚙️ Configuração

### Variáveis de Ambiente Necessárias

#### 🔴 **Obrigatórias** (Sem padrão)

```env
# Telegram Bot
TELEGRAM_BOT_TOKEN=seu_token_aqui              # Token do @BotFather
TELEGRAM_BOT_USERNAME=seu_username             # Nome do bot

# Banco de Dados
DATASOURCE_PASSWORD=sua_senha_postgres         # Senha PostgreSQL

# Segurança
SECURITY_USER_PASSWORD=sua_senha_admin         # Senha HTTP Basic
```

#### 🟡 **Opcionais** (Com padrão)

```env
# Banco de Dados
DATASOURCE_URL=jdbc:postgresql://localhost:5432/cobrancapix
DATASOURCE_USERNAME=postgres

# Segurança
SECURITY_USER_NAME=admin
```

### Métodos de Configuração

#### **Opção 1: Arquivo `.env` (Recomendado para Dev)**

```bash
# Copiar arquivo de exemplo
cp .env.example .env

# Editar com seus valores
# Windows: notepad .env
# Linux/Mac: nano .env
```

Conteúdo do `.env`:
```env
TELEGRAM_BOT_TOKEN=8763635702:AAHYgCzGWV92-gj8zc187BuBjo7_KLz3vEA
TELEGRAM_BOT_USERNAME=CobrancaPixBot
DATASOURCE_URL=jdbc:postgresql://localhost:5432/cobrancapix
DATASOURCE_USERNAME=postgres
DATASOURCE_PASSWORD=boot
SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=admin
```

#### **Opção 2: Variáveis de Sistema Windows**

```powershell
# Em PowerShell (como Administrador)
[Environment]::SetEnvironmentVariable("TELEGRAM_BOT_TOKEN", "seu_token", "User")
[Environment]::SetEnvironmentVariable("DATASOURCE_PASSWORD", "sua_senha", "User")
[Environment]::SetEnvironmentVariable("SECURITY_USER_PASSWORD", "sua_senha", "User")

# Reiniciar IDE/Terminal
```

#### **Opção 3: Variáveis de Sistema Linux/Mac**

```bash
export TELEGRAM_BOT_TOKEN="seu_token"
export DATASOURCE_PASSWORD="sua_senha"
export SECURITY_USER_PASSWORD="sua_senha"

# Para persistência, adicionar ao ~/.bashrc ou ~/.zshrc
echo 'export TELEGRAM_BOT_TOKEN="seu_token"' >> ~/.bashrc
source ~/.bashrc
```

#### **Opção 4: IDE IntelliJ IDEA**

1. `Run` → `Edit Configurations`
2. Procurar a configuração `Spring Boot`
3. Aba `Environment variables`
4. Adicionar suas variáveis
5. OK

#### **Opção 5: Docker**

```bash
docker run -e TELEGRAM_BOT_TOKEN=token \
           -e DATASOURCE_PASSWORD=senha \
           -e SECURITY_USER_PASSWORD=senha \
           meu-app-image
```

### Validação de Configuração

Ao iniciar a aplicação, você deve ver logs com sucesso:

```
INFO  o.s.b.w.e.t.TomcatWebServer : Tomcat started on port(s): 8080
INFO  c.p.c.CobrancaPixApplication : Started CobrancaPixApplication
```

Se houver erro:
```
ERROR: TELEGRAM_BOT_TOKEN cannot be null
```
Significa que as variáveis não foram carregadas. Verifique a configuração.

---

## 💻 Como Usar

### Iniciando a Aplicação

```bash
# Terminal 1: Iniciar aplicação
mvn spring-boot:run

# A aplicação estará disponível em:
# API: http://localhost:8080
# Usuário: admin
# Senha: (valor de SECURITY_USER_PASSWORD)
```

### Acessando o Bot Telegram

1. Abra o Telegram
2. Procure pelo seu bot (ex: `@CobrancaPixBot`)
3. Digite `/start`
4. Siga as instruções

### Fluxo de Uso - Vendedor

```
/start
  ↓
Cadastro (nome, email)
  ↓
Menu Principal
  ├─ Criar Cobrança
  ├─ Minhas Cobranças
  ├─ Atualizar Chave PIX
  └─ Sair
  
Criar Cobrança
  ├─ Email do comprador
  ├─ Valor
  ├─ Link do anúncio
  └─ Link de pagamento gerado ✅
```

### Fluxo de Uso - Comprador

```
Receber link de pagamento
  ↓
Clicar no link
  ↓
Ver dados da cobrança
  ├─ Valor
  ├─ Chave PIX
  └─ Dados do vendedor
  
Realizar pagamento
  ↓
Confirmar pagamento
  ↓
Vendedor recebe notificação no Telegram ✅
```

---

## 🌐 API REST

### Autenticação

Todos os endpoints requerem autenticação HTTP Basic:

```bash
# Username: admin
# Password: (SECURITY_USER_PASSWORD)

curl -u admin:senha http://localhost:8080/api/cobranca/token123
```

### Endpoints

#### 1. **Buscar Cobrança por Token**

```http
GET /api/cobranca/{token}
Content-Type: application/json
Authorization: Basic admin:senha

Response: 200 OK
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "token": "abc123def456",
  "chavePix": "user@example.com",
  "valor": 150.00,
  "emailComprador": "comprador@example.com",
  "linkAnuncio": "https://site.com/anuncio/123",
  "nomeComprador": "João Silva",
  "cpfComprador": "12345678900",
  "telefoneComprador": "11999999999",
  "status": "PENDENTE",
  "criadoEm": "2024-03-31T10:30:00",
  "pagoEm": null
}
```

**Erros:**
- `404 Not Found` - Token não existe
- `400 Bad Request` - Cobrança não está pendente

---

#### 2. **Confirmar Pagamento**

```http
PATCH /api/cobranca/{token}/pagar
Authorization: Basic admin:senha

Response: 200 OK
```

**O que acontece:**
- Status muda para `PAGO`
- Timestamp `pagoEm` é preenchido
- Vendedor recebe notificação no Telegram

---

#### 3. **Salvar Dados do Comprador**

```http
PATCH /api/cobranca/{token}/comprador
Content-Type: application/json
Authorization: Basic admin:senha

{
  "nomeComprador": "João Silva",
  "cpfComprador": "12345678900",
  "telefoneComprador": "11999999999"
}

Response: 200 OK
```

---

#### 4. **Buscar Preview de Anúncio**

```http
GET /api/cobranca/preview?url=https://site.com/anuncio/123
Authorization: Basic admin:senha

Response: 200 OK
{
  "title": "iPhone 13 Pro",
  "description": "Praticamente novo, usado por 2 meses",
  "image": "https://..."
}
```

---

## 🤖 Bot Telegram

### Comandos Disponíveis

| Comando | Descrição |
|---------|-----------|
| `/start` | Iniciar bot e se cadastrar |
| `/menu` | Ver menu principal |
| `/nova_cobranca` | Criar nova cobrança |
| `/minhas_cobrancas` | Listar suas cobranças |
| `/atualizar_pix` | Atualizar chave PIX |
| `/sair` | Desconectar |

### Interações do Bot

```
Bot: 👋 Bem-vindo ao PagPix!
     Vou te cadastrar rapidinho.
     📝 Qual o seu nome completo?

Usuário: João Silva

Bot: ✅ Nome salvo!
     📧 Qual o seu email?

Usuário: joao@example.com

Bot: 🎉 Cadastro concluído!
     Bem-vindo, João Silva!
     
     Menu Principal:
     [📝 Nova Cobrança] [📊 Minhas Cobranças]
     [🔑 Atualizar PIX] [❌ Sair]
```

---

## 📊 Estrutura do Projeto

```
cobranca-pix/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/pagamento/cobrancapix/
│   │   │       ├── CobrancaPixApplication.java         # Classe main
│   │   │       ├── EnvironmentConfig.java              # Config de ambiente
│   │   │       ├── SecurityConfig.java                 # Segurança
│   │   │       ├── controller/
│   │   │       │   └── CobrancaController.java         # REST endpoints
│   │   │       ├── service/
│   │   │       │   ├── CobrancaService.java            # Lógica de cobranças
│   │   │       │   ├── VendedorService.java            # Lógica de vendedores
│   │   │       │   └── PreviewService.java             # Scraping de anúncios
│   │   │       ├── repository/
│   │   │       │   ├── CobrancaRepository.java         # Acesso: Cobrança
│   │   │       │   └── VendedorRepository.java         # Acesso: Vendedor
│   │   │       ├── model/
│   │   │       │   ├── Cobranca.java                   # Entidade Cobrança
│   │   │       │   └── Vendedor.java                   # Entidade Vendedor
│   │   │       ├── dto/
│   │   │       │   └── DadosCompradorDTO.java          # DTO do comprador
│   │   │       └── bot/
│   │   │           ├── CobrancaBot.java                # Bot principal
│   │   │           └── TelegramBotConfig.java          # Config do bot
│   │   └── resources/
│   │       ├── application.properties                  # Config principal
│   │       ├── static/
│   │       │   └── index.html                          # Página inicial
│   │       └── templates/                              # Thymeleaf (se usar)
│   └── test/
│       └── java/
│           └── CobrancaPixApplicationTests.java        # Testes
├── pom.xml                                              # Dependências Maven
├── mvnw / mvnw.cmd                                     # Maven Wrapper
├── .gitignore                                          # Git ignore
├── .env.example                                        # Exemplo de env
├── setup-env.sh / setup-env.bat                       # Scripts de setup
├── ENV_CONFIGURATION.md                               # Guia de env
├── README_VARIAVEIS_AMBIENTE.md                       # Guia português
├── SECURITY_REFACTOR.md                               # Alterações segurança
└── README.md                                           # Este arquivo
```

---

## 🗄️ Banco de Dados

### Tabelas

#### **vendedores**

```sql
CREATE TABLE vendedores (
    id UUID PRIMARY KEY,
    chat_id BIGINT NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    plano VARCHAR(50) DEFAULT 'GRATUITO',
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

**Campos:**
- `id` - UUID gerado automaticamente
- `chat_id` - ID do chat Telegram (único)
- `nome` - Nome do vendedor
- `email` - Email do vendedor
- `plano` - GRATUITO ou PRO
- `ativo` - Ativo/Inativo
- `criado_em` - Data de criação

---

#### **cobrancas**

```sql
CREATE TABLE cobrancas (
    id UUID PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    chave_pix VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    email_comprador VARCHAR(255) NOT NULL,
    link_anuncio VARCHAR(255),
    nome_comprador VARCHAR(255),
    cpf_comprador VARCHAR(11),
    telefone_comprador VARCHAR(20),
    vendedor_id UUID REFERENCES vendedores(id),
    status VARCHAR(50) DEFAULT 'PENDENTE',
    criado_em TIMESTAMP DEFAULT NOW(),
    pago_em TIMESTAMP
);
```

**Campos:**
- `id` - UUID gerado automaticamente
- `token` - Token único para compartilhamento
- `chave_pix` - Chave PIX para recebimento
- `valor` - Valor a cobrar
- `email_comprador` - Email do comprador
- `link_anuncio` - Link do anúncio original
- `nome_comprador` - Nome do comprador
- `cpf_comprador` - CPF do comprador
- `telefone_comprador` - Telefone do comprador
- `vendedor_id` - Foreign key para vendedores
- `status` - PENDENTE, PAGO, EXPIRADO
- `criado_em` - Data de criação
- `pago_em` - Data de pagamento

---

### Relacionamentos

```
Vendedor (1) ─────────────────── (N) Cobranca
     ↑                                  ↑
     └──────── vendedor_id ────────────┘
```

Um vendedor pode ter muitas cobranças, mas cada cobrança pertence a um vendedor.

---

## 🔐 Variáveis de Ambiente

### Referência Completa

| Variável | Obrigatória | Padrão | Descrição |
|----------|-------------|--------|-----------|
| `TELEGRAM_BOT_TOKEN` | ✅ Sim | - | Token do bot Telegram |
| `TELEGRAM_BOT_USERNAME` | ✅ Sim | - | Username do bot |
| `DATASOURCE_URL` | ❌ Não | `jdbc:postgresql://localhost:5432/cobrancapix` | URL PostgreSQL |
| `DATASOURCE_USERNAME` | ❌ Não | `postgres` | Usuário BD |
| `DATASOURCE_PASSWORD` | ✅ Sim | - | Senha BD |
| `SECURITY_USER_NAME` | ❌ Não | `admin` | Usuário HTTP Basic |
| `SECURITY_USER_PASSWORD` | ✅ Sim | - | Senha HTTP Basic |

### Segurança

⚠️ **IMPORTANTE:**
- Nunca faça commit do arquivo `.env`
- Nunca compartilhe suas credenciais
- Use senhas fortes em produção
- Arquivo `.env` está protegido no `.gitignore`

---

## 🚢 Deployment

### Docker

#### 1. **Criar Dockerfile**

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/cobranca-pix-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

#### 2. **Criar Docker Compose**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: cobrancapix
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      TELEGRAM_BOT_TOKEN: ${TELEGRAM_BOT_TOKEN}
      TELEGRAM_BOT_USERNAME: ${TELEGRAM_BOT_USERNAME}
      DATASOURCE_URL: jdbc:postgresql://postgres:5432/cobrancapix
      DATASOURCE_USERNAME: postgres
      DATASOURCE_PASSWORD: postgres
      SECURITY_USER_PASSWORD: ${SECURITY_USER_PASSWORD}
    depends_on:
      - postgres
    restart: unless-stopped

volumes:
  postgres_data:
```

#### 3. **Executar Docker Compose**

```bash
# Build
mvn clean package -DskipTests

# Criar arquivo .env
echo "TELEGRAM_BOT_TOKEN=seu_token" > .env

# Rodar
docker-compose up -d

# Ver logs
docker-compose logs -f app
```

---

### Deploy em Servidor Linux

```bash
# 1. Clonar projeto
git clone https://github.com/seu-usuario/cobranca-pix.git
cd cobranca-pix

# 2. Configurar variáveis
nano .env

# 3. Build
mvn clean package -DskipTests

# 4. Executar
java -jar target/cobranca-pix-0.0.1-SNAPSHOT.jar &

# 5. Monitorar
tail -f nohup.out
```

---

### Deploy no Heroku

```bash
# 1. Login no Heroku
heroku login

# 2. Criar app
heroku create seu-app-name

# 3. Adicionar banco PostgreSQL
heroku addons:create heroku-postgresql:hobby-dev

# 4. Configurar variáveis
heroku config:set TELEGRAM_BOT_TOKEN=seu_token
heroku config:set DATASOURCE_PASSWORD=sua_senha
heroku config:set SECURITY_USER_PASSWORD=sua_senha

# 5. Deploy
git push heroku main
```

---

## 🐛 Troubleshooting

### Erro: "TELEGRAM_BOT_TOKEN cannot be null"

**Causa:** Variável de ambiente não configurada

**Solução:**
```bash
# Verificar se a variável existe
echo $TELEGRAM_BOT_TOKEN

# Se vazio, configurar:
export TELEGRAM_BOT_TOKEN="seu_token"
```

---

### Erro: "Connection refused" (Banco de Dados)

**Causa:** PostgreSQL não está rodando

**Solução:**
```bash
# Iniciar PostgreSQL
# Windows: pg_ctl start
# Linux: sudo systemctl start postgresql
# macOS: brew services start postgresql

# Verificar conexão
psql -U postgres -h localhost
```

---

### Erro: "Porta 8080 já em uso"

**Causa:** Outra aplicação usando a porta

**Solução:**
```bash
# Windows: Achar processo na porta 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac: Achar processo na porta 8080
lsof -i :8080
kill -9 <PID>
```

---

### Erro: "Bot não responde"

**Verificar:**
1. Token do bot está correto no `.env`
2. Aplicação está rodando: `http://localhost:8080`
3. Banco de dados está ativo
4. Username do bot está correto

---

### Aplicação inicia mas endpoints retornam 401

**Causa:** Credenciais HTTP Basic incorretas

**Solução:**
```bash
# Usar credenciais padrão ou configurar:
SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=sua_senha

# Testar com curl:
curl -u admin:sua_senha http://localhost:8080/api/cobranca/token123
```

---

## 📝 Exemplo de Uso Completo

### Via Bot Telegram

```
1. Abrir Telegram
2. Procurar @CobrancaPixBot
3. /start
4. Seguir cadastro
5. /nova_cobranca
6. Fornecer dados
7. Receber link de pagamento
8. Compartilhar link
9. Comprador clica
10. Realiza pagamento
11. Vendedor recebe notificação
```

### Via API REST

```bash
# Buscar cobrança
curl -u admin:senha \
  http://localhost:8080/api/cobranca/abc123def456

# Confirmar pagamento
curl -u admin:senha -X PATCH \
  http://localhost:8080/api/cobranca/abc123def456/pagar

# Salvar dados do comprador
curl -u admin:senha -X PATCH \
  -H "Content-Type: application/json" \
  -d '{
    "nomeComprador": "João Silva",
    "cpfComprador": "12345678900",
    "telefoneComprador": "11999999999"
  }' \
  http://localhost:8080/api/cobranca/abc123def456/comprador
```

---

## 📚 Documentação Adicional

- [ENV_CONFIGURATION.md](./ENV_CONFIGURATION.md) - Guia detalhado de variáveis
- [README_VARIAVEIS_AMBIENTE.md](./README_VARIAVEIS_AMBIENTE.md) - Guia em português
- [SECURITY_REFACTOR.md](./SECURITY_REFACTOR.md) - Alterações de segurança
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Telegram Bot API](https://core.telegram.org/bots/api)

---

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Add nova-feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

### Padrões de Código

- Use Java conventions
- Adicione comentários para lógica complexa
- Escreva testes para novas funcionalidades
- Mantenha segurança em primeiro lugar

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](./LICENSE) para mais detalhes.

---

## 👥 Autores

- **Seu Nome** - [GitHub](https://github.com/seu-usuario)

---

## 💬 Suporte

Tem dúvidas? Abra uma [Issue](https://github.com/seu-usuario/cobranca-pix/issues) no GitHub.

---

## 📞 Contato

- 📧 Email: seu-email@example.com
- 💬 Telegram: [@seu-usuario](https://t.me/seu-usuario)

---

## 🙏 Agradecimentos

- Spring Boot team
- Telegram Bot API
- PostgreSQL community
- Comunidade Java

---

**Última atualização:** 31 de Março de 2026  
**Status:** ✅ Em Produção


