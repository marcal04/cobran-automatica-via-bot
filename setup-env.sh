#!/bin/bash

# Script para configurar variáveis de ambiente em Linux/Mac (Desenvolvimento)
# Uso: chmod +x setup-env.sh && ./setup-env.sh

echo "========================================"
echo "Configuração de Variáveis de Ambiente"
echo "========================================"
echo ""

# Função para ler entrada com padrão
read_input() {
    local prompt="$1"
    local default="$2"
    local var_name="$3"

    read -p "$prompt (padrão: $default): " input
    if [ -z "$input" ]; then
        eval "$var_name='$default'"
    else
        eval "$var_name='$input'"
    fi
}

# Leitura das variáveis
read_input "TELEGRAM_BOT_TOKEN" "vazio" TELEGRAM_BOT_TOKEN
read_input "TELEGRAM_BOT_USERNAME" "vazio" TELEGRAM_BOT_USERNAME
read_input "DATASOURCE_URL" "jdbc:postgresql://localhost:5432/cobrancapix" DATASOURCE_URL
read_input "DATASOURCE_USERNAME" "postgres" DATASOURCE_USERNAME
read_input "DATASOURCE_PASSWORD" "boot" DATASOURCE_PASSWORD
read_input "SECURITY_USER_NAME" "admin" SECURITY_USER_NAME
read_input "SECURITY_USER_PASSWORD" "admin" SECURITY_USER_PASSWORD

# Exporta as variáveis
export TELEGRAM_BOT_TOKEN
export TELEGRAM_BOT_USERNAME
export DATASOURCE_URL
export DATASOURCE_USERNAME
export DATASOURCE_PASSWORD
export SECURITY_USER_NAME
export SECURITY_USER_PASSWORD

echo ""
echo "========================================"
echo "Variáveis configuradas!"
echo "========================================"
echo ""

# Opção de adicionar ao ~/.bashrc ou ~/.zshrc
read -p "Deseja adicionar ao arquivo de perfil permanentemente? (s/n): " add_to_profile

if [ "$add_to_profile" = "s" ] || [ "$add_to_profile" = "S" ]; then
    SHELL_RC=""
    if [ -n "$ZSH_VERSION" ]; then
        SHELL_RC="$HOME/.zshrc"
    else
        SHELL_RC="$HOME/.bashrc"
    fi

    echo "" >> "$SHELL_RC"
    echo "# Variáveis de ambiente - Cobrança PIX" >> "$SHELL_RC"
    echo "export TELEGRAM_BOT_TOKEN='$TELEGRAM_BOT_TOKEN'" >> "$SHELL_RC"
    echo "export TELEGRAM_BOT_USERNAME='$TELEGRAM_BOT_USERNAME'" >> "$SHELL_RC"
    echo "export DATASOURCE_URL='$DATASOURCE_URL'" >> "$SHELL_RC"
    echo "export DATASOURCE_USERNAME='$DATASOURCE_USERNAME'" >> "$SHELL_RC"
    echo "export DATASOURCE_PASSWORD='$DATASOURCE_PASSWORD'" >> "$SHELL_RC"
    echo "export SECURITY_USER_NAME='$SECURITY_USER_NAME'" >> "$SHELL_RC"
    echo "export SECURITY_USER_PASSWORD='$SECURITY_USER_PASSWORD'" >> "$SHELL_RC"

    echo "Variáveis adicionadas a $SHELL_RC"
fi

echo ""
echo "Iniciando aplicação..."
echo ""

mvn spring-boot:run

