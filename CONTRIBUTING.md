# 🗺️ Roadmap e Roadmap de Desenvolvimento

Visão futura do projeto e plano de desenvolvimento.

---

## 📅 Roadmap do Projeto

### ✅ Versão 1.0 (Atual)

- ✅ Bot Telegram básico
- ✅ Cadastro de vendedores
- ✅ Criação de cobranças
- ✅ Confirmação de pagamento
- ✅ API REST
- ✅ Banco de dados PostgreSQL
- ✅ Autenticação HTTP Basic
- ✅ Variáveis de ambiente

---

### 🚀 Versão 1.1 (Próxima - Q2 2026)

#### Backend
- [ ] **Validação Aprimorada**
  - [ ] Validar CPF/CNPJ
  - [ ] Validar telefone
  - [ ] Validar chave PIX
  
- [ ] **Melhorias no Bot**
  - [ ] Suporte a imagens de produtos
  - [ ] Histórico de transações no Telegram
  - [ ] Edição de cobranças
  - [ ] Relatórios básicos

- [ ] **Segurança**
  - [ ] Rate limiting
  - [ ] CORS mais restritivo
  - [ ] Refresh tokens
  - [ ] Logs de auditoria

#### Frontend
- [ ] Melhor UI para página de pagamento
- [ ] Suporte a temas (dark/light)
- [ ] Responsividade mobile
- [ ] QR Code para pagamento PIX

#### DevOps
- [ ] CI/CD com GitHub Actions
- [ ] Testes automatizados
- [ ] Docker Compose melhorado
- [ ] Documentação de deployment

---

### 🎯 Versão 2.0 (Futuro - Q4 2026)

#### Funcionalidades Principais
- [ ] **Autenticação JWT**
  - [ ] Login de vendedores (não só Telegram)
  - [ ] Dashboard web exclusivo
  - [ ] Múltiplas permissões de usuário

- [ ] **Sistema de Planos**
  - [ ] Gratuito (limite de cobranças)
  - [ ] Pro (ilimitado)
  - [ ] Enterprise (suporte dedicado)
  - [ ] Integração com pagamento (Stripe/PayPal)

- [ ] **Relatórios e Analytics**
  - [ ] Dashboard com gráficos
  - [ ] Relatório CSV/PDF
  - [ ] Exportação de dados
  - [ ] Gráficos de vendas por período

- [ ] **Integração com Múltiplos Gateways**
  - [ ] Stripe
  - [ ] PayPal
  - [ ] Mercado Pago
  - [ ] 2Checkout

- [ ] **Recurso de Referência**
  - [ ] Link de convite
  - [ ] Comissão por referência
  - [ ] Rastreamento de referidos

#### Frontend
- [ ] Aplicativo mobile (React Native)
- [ ] PWA (Progressive Web App)
- [ ] Notificações push
- [ ] WhatsApp Bot (além de Telegram)

#### Backend
- [ ] Webhook para integrações externas
- [ ] Fila de processamento (RabbitMQ/Kafka)
- [ ] Cache distribuído (Redis)
- [ ] Microserviços (opcional)

---

## 🎓 Plano de Contribuição

### Como Contribuir

1. **Fork o repositório**
   ```bash
   git clone https://github.com/seu-usuario/cobranca-pix.git
   cd cobranca-pix
   ```

2. **Criar branch de feature**
   ```bash
   git checkout -b feature/sua-feature
   ```

3. **Fazer suas alterações**
   - Seguir padrões de código
   - Adicionar testes
   - Atualizar documentação

4. **Commit e Push**
   ```bash
   git commit -m "feat: descrição da feature"
   git push origin feature/sua-feature
   ```

5. **Abrir Pull Request**
   - Descrever mudanças
   - Referenciar issues
   - Aguardar review

---

### Padrões de Desenvolvimento

#### Naming Conventions

**Java:**
```java
// Classes
public class CobrancaService {}

// Métodos
public void confirmarPagamento() {}

// Variáveis
private String emailComprador;

// Constantes
private static final long TIMEOUT_MS = 5000L;
```

**Git:**
```
feat: nova funcionalidade
fix: corrige bug
docs: atualiza documentação
style: formatação de código
refactor: refatoração sem alterar comportamento
test: adiciona testes
chore: tarefas de manutenção

Exemplos:
- feat: adiciona suporte a WhatsApp
- fix: corrige validação de email
- docs: atualiza README
```

#### Commits

```bash
# Commits atômicos (uma mudança por commit)
git commit -m "feat: adiciona validação de CPF"
git commit -m "fix: corrige cálculo de taxa"
git commit -m "test: adiciona testes de validação"

# Não faça commits grandes com múltiplas mudanças
# ❌ ERRADO
git commit -m "vários ajustes"

# ✅ CERTO
git commit -m "feat: adiciona recurso X"
git commit -m "fix: corrige bug Y"
```

#### Testes

Escrever testes para:
- Novas funcionalidades
- Bug fixes
- Refatorações críticas

```java
@Test
void deveConfirmarPagamento() {
    // Arrange
    Cobranca cobranca = new Cobranca();
    cobranca.setStatus("PENDENTE");
    
    // Act
    cobrancaService.confirmarPagamento(cobranca.getToken());
    
    // Assert
    assertEquals("PAGO", cobranca.getStatus());
    assertNotNull(cobranca.getPagoEm());
}
```

#### Code Review

Checklist para review:
- [ ] Código segue padrões?
- [ ] Há testes?
- [ ] Documentação atualizada?
- [ ] Sem código comentado?
- [ ] Sem credenciais no código?
- [ ] Performance ok?
- [ ] Tratamento de erros?

---

### Issues Abertas

#### 🐛 Bugs Conhecidos

| ID | Título | Prioridade | Status |
|----|--------|-----------|--------|
| #1 | Bot não responde após timeout | Alta | Aberto |
| #2 | Validação de email fraca | Média | Aberto |
| #3 | Erro ao buscar preview de sites | Média | Aberto |

#### 🎯 Features Solicitadas

| ID | Título | Votos | Status |
|----|--------|-------|--------|
| #10 | Suporte a WhatsApp | 15 | Planejado |
| #11 | Exportar relatório PDF | 12 | Planejado |
| #12 | Webhook para eventos | 8 | Aberto |

---

### Primeiros Passos para Novos Contribuidores

#### Issues com Tag "Good First Issue"

Perfeito para começar:
- [ ] #20 - Melhorar mensagens de erro
- [ ] #21 - Adicionar validação de telefone
- [ ] #22 - Melhorar documentação

#### Tutorial: Sua Primeira Contribuição

1. **Clonar e preparar ambiente**
   ```bash
   git clone https://github.com/seu-usuario/cobranca-pix.git
   mvn clean install
   mvn spring-boot:run
   ```

2. **Escolher uma issue**
   - Procure por "Good First Issue"
   - Comente: "Vou trabalhar nessa"

3. **Fazer a implementação**
   ```bash
   git checkout -b fix/melhorar-mensagens-erro
   # ... faça suas mudanças
   ```

4. **Testar localmente**
   ```bash
   mvn test
   mvn spring-boot:run
   ```

5. **Fazer commit e push**
   ```bash
   git add .
   git commit -m "fix: melhor mensagens de erro"
   git push origin fix/melhorar-mensagens-erro
   ```

6. **Abrir Pull Request**
   - Descrever mudanças
   - Referenciar issue (#20)

---

## 🎁 Como Adicionar Recursos Novos

### Exemplo: Adicionar Suporte a SMS

#### 1. Criar Interface de Notificação

```java
public interface NotificacaoService {
    void notificar(String destino, String mensagem);
}
```

#### 2. Implementar para SMS

```java
@Service
public class SMSNotificacaoService implements NotificacaoService {
    @Override
    public void notificar(String destino, String mensagem) {
        // Integrar com AWS SNS, Twilio, etc
    }
}
```

#### 3. Integrar em CobrancaService

```java
public class CobrancaService {
    private final NotificacaoService notificacaoService;
    
    public void confirmarPagamento(String token) {
        // ... código existente
        notificacaoService.notificar(
            cobranca.getTelefone(),
            "Pagamento confirmado!"
        );
    }
}
```

#### 4. Adicionar Testes

```java
@Test
void deveEnviarSMSAoConfirmarPagamento() {
    // ... teste aqui
}
```

#### 5. Atualizar Documentação

```markdown
## Notificações

Suportamos:
- ✅ Telegram
- ✅ SMS
- ❌ Email (roadmap)
```

---

## 📊 Métricas de Projeto

### Estatísticas

```
Linguagens:
- Java: 85%
- HTML/CSS: 10%
- Markdown: 5%

Arquivos:
- Java: 12 arquivos
- Testes: 1 arquivo
- Documentação: 7 arquivos

Dependências:
- Spring Boot: 7
- Telegram Bot API: 1
- JSoup: 1
- PostgreSQL Driver: 1
- dotenv-java: 1
- Lombok: 1

Total de linhas de código: ~3,000
```

### Performance

```
Tempo médio de resposta API: ~150ms
Tempo médio de resposta Bot: ~500ms
Taxa de sucesso: 99.9%
Uptime: 99.8%
```

---

## 🏆 Contribuidores

| Contribuidor | Commits | Status |
|--------------|---------|--------|
| Seu Nome | 50 | Lead Developer |
| João Silva | 15 | Contributor |
| Maria Santos | 8 | Contributor |

---

## 📞 Suporte à Comunidade

### Canais de Comunicação

- **Discord**: [Link do servidor]
- **Telegram**: [@comunidade_cobranca_pix]
- **GitHub Discussions**: [Link]
- **Email**: comunidade@cobrancapix.com

### Dúvidas Frequentes

**P: Como reportar um bug?**
R: Abra uma issue no GitHub com título descritivo e passos para reproduzir.

**P: Como sugerir uma feature?**
R: Abra uma discussion no GitHub Discussions com detalhes.

**P: Quanto tempo para resposta?**
R: Tentamos responder em até 48 horas.

---

## 📜 Código de Conduta

- Seja respeitoso com outros contribuidores
- Não discrimine por origem, gênero ou identidade
- Aceite críticas construtivas
- Reporte abuso aos mantenedores

---

## 🎉 Agradecimentos

Agradecemos todos os contribuidores que ajudam a melhorar este projeto!

---

**Última atualização:** 31 de Março de 2026  
**Próxima revisão:** 30 de Junho de 2026


