package com.pagamento.cobrancapix.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
public class PreviewService {

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> buscarPreview(String url) {
        Map<String, String> preview = new HashMap<>();

        try {
            // Chama a API do Microlink passando a URL do anúncio
            String resposta = restClient.get()
                    .uri("https://api.microlink.io?url=" + url)
                    .retrieve()
                    .body(String.class);

            // Faz o parse do JSON retornado
            JsonNode json = mapper.readTree(resposta);
            JsonNode data = json.get("data");

            String titulo    = pegarTexto(data, "title");
            String descricao = pegarTexto(data, "description")
                    .replaceAll("<[^>]*>", " ")  // remove tags HTML
                    .replaceAll("\\s+", " ")     // remove espaços duplos
                    .trim();
            String site      = pegarTexto(data, "publisher");

            // A imagem vem dentro de um objeto: data.image.url
            String imagem = "";
            if (data.has("image") && !data.get("image").isNull()) {
                imagem = pegarTexto(data.get("image"), "url");
            }

            // Fallback pro domínio se não tiver publisher
            if (site.isEmpty()) {
                try {
                    site = new java.net.URL(url).getHost().replace("www.", "");
                } catch (Exception e) {
                    site = url;
                }
            }

            preview.put("titulo",    titulo);
            preview.put("imagem",    imagem);
            preview.put("descricao", descricao);
            preview.put("site",      site);
            preview.put("url",       url);
            preview.put("sucesso",   "true");

        } catch (Exception e) {
            preview.put("sucesso",   "false");
            preview.put("erro",      e.getClass().getSimpleName() + ": " + e.getMessage());
            preview.put("titulo",    "Ver anúncio");
            preview.put("imagem",    "");
            preview.put("descricao", "");
            preview.put("site",      url);
            preview.put("url",       url);
        }

        return preview;
    }

    // Metodo auxiliar — pega texto de um nó JSON com segurança
    private String pegarTexto(JsonNode node, String campo) {
        if (node == null) return "";
        JsonNode valor = node.get(campo);
        if (valor == null || valor.isNull()) return "";
        return valor.asText();
    }
}