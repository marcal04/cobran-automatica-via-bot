# 🧪 Exemplos de Uso da API

Exemplos práticos de como usar a API REST da Cobrança PIX.

---

## 📝 Setup Inicial

### Variáveis Úteis (substitua com seus valores)

```bash
# Configurações
BASE_URL="http://localhost:8080"
USERNAME="admin"
PASSWORD="admin"
TOKEN="abc123def456"  # Token da cobrança

# Dados de teste
EMAIL_COMPRADOR="comprador@example.com"
VALOR="150.00"
CHAVE_PIX="user@example.com"
LINK_ANUNCIO="https://site.com/anuncio/123"
```

---

## 🎯 Exemplos com cURL

### 1. Buscar Cobrança por Token

Busca os dados de uma cobrança usando seu token único.

```bash
curl -i -u $USERNAME:$PASSWORD \
  "$BASE_URL/api/cobranca/$TOKEN"
```

**Resposta (200 OK):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "token": "abc123def456",
  "chavePix": "user@example.com",
  "valor": 150.00,
  "emailComprador": "comprador@example.com",
  "linkAnuncio": "https://site.com/anuncio/123",
  "nomeComprador": null,
  "cpfComprador": null,
  "telefoneComprador": null,
  "status": "PENDENTE",
  "criadoEm": "2024-03-31T10:30:00",
  "pagoEm": null
}
```

**Erros possíveis:**
- `404 Not Found` - Token não existe
- `400 Bad Request` - Cobrança não está pendente

---

### 2. Confirmar Pagamento

Marca uma cobrança como paga e notifica o vendedor.

```bash
curl -i -X PATCH \
  -u $USERNAME:$PASSWORD \
  "$BASE_URL/api/cobranca/$TOKEN/pagar"
```

**Resposta (200 OK):**
```
HTTP/1.1 200 OK
```

**O que acontece:**
- ✅ Status muda para `PAGO`
- ✅ Campo `pagoEm` é preenchido com data/hora
- ✅ Vendedor recebe notificação no Telegram

---

### 3. Salvar Dados do Comprador

Atualiza informações do comprador na cobrança.

```bash
curl -i -X PATCH \
  -u $USERNAME:$PASSWORD \
  -H "Content-Type: application/json" \
  -d '{
    "nomeComprador": "João Silva",
    "cpfComprador": "12345678900",
    "telefoneComprador": "11999999999"
  }' \
  "$BASE_URL/api/cobranca/$TOKEN/comprador"
```

**Resposta (200 OK):**
```
HTTP/1.1 200 OK
```

**Dados atualizados:**
```json
{
  "nomeComprador": "João Silva",
  "cpfComprador": "12345678900",
  "telefoneComprador": "11999999999"
}
```

---

### 4. Buscar Preview de Anúncio

Extrai informações (título, descrição, imagem) de uma URL.

```bash
curl -i -u $USERNAME:$PASSWORD \
  "$BASE_URL/api/cobranca/preview?url=https://site.com/anuncio/123"
```

**Resposta (200 OK):**
```json
{
  "title": "iPhone 13 Pro 256GB Dourado",
  "description": "Telefone praticamente novo, usado por apenas 2 meses. Acompanha caixa original e todos os acessórios.",
  "image": "https://site.com/images/iphone-13-pro.jpg"
}
```

---

## 🔧 Exemplos com Postman

### Importar Coleção

Crie uma nova coleção no Postman com essas requisições:

#### **GET - Buscar Cobrança**
```
Method: GET
URL: {{base_url}}/api/cobranca/{{token}}
Auth: Basic Auth
  Username: {{username}}
  Password: {{password}}
```

#### **PATCH - Confirmar Pagamento**
```
Method: PATCH
URL: {{base_url}}/api/cobranca/{{token}}/pagar
Auth: Basic Auth
  Username: {{username}}
  Password: {{password}}
```

#### **PATCH - Salvar Comprador**
```
Method: PATCH
URL: {{base_url}}/api/cobranca/{{token}}/comprador
Auth: Basic Auth
  Username: {{username}}
  Password: {{password}}
Headers:
  Content-Type: application/json
Body (raw):
{
  "nomeComprador": "João Silva",
  "cpfComprador": "12345678900",
  "telefoneComprador": "11999999999"
}
```

#### **GET - Preview**
```
Method: GET
URL: {{base_url}}/api/cobranca/preview?url={{url_anuncio}}
Auth: Basic Auth
  Username: {{username}}
  Password: {{password}}
```

---

## 🐍 Exemplos com Python

### Instalação de Dependências

```bash
pip install requests
```

### Script de Teste

```python
import requests
from requests.auth import HTTPBasicAuth
import json

# Configuração
BASE_URL = "http://localhost:8080"
USERNAME = "admin"
PASSWORD = "admin"
TOKEN = "abc123def456"

auth = HTTPBasicAuth(USERNAME, PASSWORD)

# 1. Buscar cobrança
print("1. Buscando cobrança...")
response = requests.get(
    f"{BASE_URL}/api/cobranca/{TOKEN}",
    auth=auth
)
print(f"Status: {response.status_code}")
print(f"Resposta: {json.dumps(response.json(), indent=2)}")
print()

# 2. Confirmar pagamento
print("2. Confirmando pagamento...")
response = requests.patch(
    f"{BASE_URL}/api/cobranca/{TOKEN}/pagar",
    auth=auth
)
print(f"Status: {response.status_code}")
print()

# 3. Salvar dados do comprador
print("3. Salvando dados do comprador...")
dados_comprador = {
    "nomeComprador": "João Silva",
    "cpfComprador": "12345678900",
    "telefoneComprador": "11999999999"
}
response = requests.patch(
    f"{BASE_URL}/api/cobranca/{TOKEN}/comprador",
    auth=auth,
    json=dados_comprador,
    headers={"Content-Type": "application/json"}
)
print(f"Status: {response.status_code}")
print()

# 4. Buscar preview
print("4. Buscando preview de anúncio...")
url_anuncio = "https://site.com/anuncio/123"
response = requests.get(
    f"{BASE_URL}/api/cobranca/preview",
    params={"url": url_anuncio},
    auth=auth
)
print(f"Status: {response.status_code}")
print(f"Resposta: {json.dumps(response.json(), indent=2)}")
```

### Executar Script

```bash
python script_teste.py
```

---

## 🟢 Exemplos com Node.js

### Instalação

```bash
npm install axios
```

### Script de Teste

```javascript
const axios = require('axios');

// Configuração
const BASE_URL = "http://localhost:8080";
const USERNAME = "admin";
const PASSWORD = "admin";
const TOKEN = "abc123def456";

const api = axios.create({
    baseURL: BASE_URL,
    auth: {
        username: USERNAME,
        password: PASSWORD
    }
});

async function testarAPI() {
    try {
        // 1. Buscar cobrança
        console.log("1. Buscando cobrança...");
        let response = await api.get(`/api/cobranca/${TOKEN}`);
        console.log("Status:", response.status);
        console.log("Dados:", JSON.stringify(response.data, null, 2));
        console.log();

        // 2. Confirmar pagamento
        console.log("2. Confirmando pagamento...");
        response = await api.patch(`/api/cobranca/${TOKEN}/pagar`);
        console.log("Status:", response.status);
        console.log();

        // 3. Salvar dados do comprador
        console.log("3. Salvando dados do comprador...");
        const dadosComprador = {
            nomeComprador: "João Silva",
            cpfComprador: "12345678900",
            telefoneComprador: "11999999999"
        };
        response = await api.patch(
            `/api/cobranca/${TOKEN}/comprador`,
            dadosComprador
        );
        console.log("Status:", response.status);
        console.log();

        // 4. Buscar preview
        console.log("4. Buscando preview de anúncio...");
        response = await api.get('/api/cobranca/preview', {
            params: {
                url: "https://site.com/anuncio/123"
            }
        });
        console.log("Status:", response.status);
        console.log("Dados:", JSON.stringify(response.data, null, 2));

    } catch (error) {
        console.error("Erro:", error.response?.status || error.message);
        if (error.response?.data) {
            console.error("Resposta:", error.response.data);
        }
    }
}

testarAPI();
```

### Executar Script

```bash
node script_teste.js
```

---

## 🟦 Exemplos com C#/.NET

### Instalação

```bash
dotnet add package System.Net.Http
```

### Script de Teste

```csharp
using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

class Program
{
    static async Task Main()
    {
        string BASE_URL = "http://localhost:8080";
        string USERNAME = "admin";
        string PASSWORD = "admin";
        string TOKEN = "abc123def456";

        // Criar HttpClient com autenticação Basic
        var handler = new HttpClientHandler();
        var credentials = new NetworkCredential(USERNAME, PASSWORD);
        handler.Credentials = credentials;

        using (var client = new HttpClient(handler))
        {
            client.BaseAddress = new Uri(BASE_URL);

            try
            {
                // 1. Buscar cobrança
                Console.WriteLine("1. Buscando cobrança...");
                var response = await client.GetAsync($"/api/cobranca/{TOKEN}");
                Console.WriteLine($"Status: {response.StatusCode}");
                var content = await response.Content.ReadAsStringAsync();
                var data = JsonConvert.DeserializeObject(content);
                Console.WriteLine($"Dados: {JsonConvert.SerializeObject(data, Formatting.Indented)}");
                Console.WriteLine();

                // 2. Confirmar pagamento
                Console.WriteLine("2. Confirmando pagamento...");
                response = await client.PatchAsync($"/api/cobranca/{TOKEN}/pagar", null);
                Console.WriteLine($"Status: {response.StatusCode}");
                Console.WriteLine();

                // 3. Salvar dados do comprador
                Console.WriteLine("3. Salvando dados do comprador...");
                var dadosComprador = new
                {
                    nomeComprador = "João Silva",
                    cpfComprador = "12345678900",
                    telefoneComprador = "11999999999"
                };
                var json = JsonConvert.SerializeObject(dadosComprador);
                var httpContent = new StringContent(json, Encoding.UTF8, "application/json");
                response = await client.PatchAsync($"/api/cobranca/{TOKEN}/comprador", httpContent);
                Console.WriteLine($"Status: {response.StatusCode}");

            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erro: {ex.Message}");
            }
        }
    }
}
```

---

## 📊 Teste de Carga

### Apache Bench

```bash
# Instalar (Ubuntu/Debian)
sudo apt-get install apache2-utils

# Teste 1000 requisições com concorrência de 10
ab -n 1000 -c 10 -A admin:admin http://localhost:8080/api/cobranca/token123
```

### wrk (Ferramenta moderna)

```bash
# Instalar (Ubuntu/Debian)
sudo apt-get install wrk

# Teste com 4 threads, 100 conexões, por 30 segundos
wrk -t4 -c100 -d30s -H "Authorization: Basic YWRtaW46YWRtaW4=" \
    http://localhost:8080/api/cobranca/token123
```

---

## ✅ Checklist de Teste

- [ ] Configurou variáveis de ambiente?
- [ ] PostgreSQL está rodando?
- [ ] Aplicação está iniciada em http://localhost:8080?
- [ ] Consegue buscar uma cobrança?
- [ ] Consegue confirmar pagamento?
- [ ] Consegue atualizar dados do comprador?
- [ ] Consegue buscar preview?
- [ ] Recebeu notificação no Telegram?

---

## 🔗 Referências

- [cURL Documentation](https://curl.se/docs/)
- [Postman Learning Center](https://learning.postman.com/)
- [Requests - Python HTTP Library](https://requests.readthedocs.io/)
- [Axios - Promise based HTTP client](https://axios-http.com/)


