@echo off
REM Script para configurar variáveis de ambiente em Windows (Desenvolvimento)
REM Nota: Estas variáveis serão persistentes apenas na sessão atual
REM Para persistência permanente, use Variáveis de Ambiente do Windows

echo Configurando variáveis de ambiente para desenvolvimento...
echo.

REM Exemplo com valores fictícios - SUBSTITUA PELOS SEUS VALORES REAIS
setlocal enabledelayedexpansion

set /p TELEGRAM_BOT_TOKEN="Digite seu TELEGRAM_BOT_TOKEN (ou pressione Enter para ignorar): "
if not "!TELEGRAM_BOT_TOKEN!"=="" (
    set "TELEGRAM_BOT_TOKEN=!TELEGRAM_BOT_TOKEN!"
) else (
    echo Aviso: TELEGRAM_BOT_TOKEN não definido
)

set /p TELEGRAM_BOT_USERNAME="Digite seu TELEGRAM_BOT_USERNAME (ou pressione Enter para ignorar): "
if not "!TELEGRAM_BOT_USERNAME!"=="" (
    set "TELEGRAM_BOT_USERNAME=!TELEGRAM_BOT_USERNAME!"
) else (
    echo Aviso: TELEGRAM_BOT_USERNAME não definido
)

set /p DATASOURCE_PASSWORD="Digite sua DATASOURCE_PASSWORD (ou pressione Enter para usar 'boot'): "
if not "!DATASOURCE_PASSWORD!"=="" (
    set "DATASOURCE_PASSWORD=!DATASOURCE_PASSWORD!"
) else (
    set "DATASOURCE_PASSWORD=boot"
)

set /p SECURITY_USER_PASSWORD="Digite sua SECURITY_USER_PASSWORD (ou pressione Enter para usar 'admin'): "
if not "!SECURITY_USER_PASSWORD!"=="" (
    set "SECURITY_USER_PASSWORD=!SECURITY_USER_PASSWORD!"
) else (
    set "SECURITY_USER_PASSWORD=admin"
)

REM Define as variáveis para a sessão atual
set TELEGRAM_BOT_TOKEN=!TELEGRAM_BOT_TOKEN!
set TELEGRAM_BOT_USERNAME=!TELEGRAM_BOT_USERNAME!
set DATASOURCE_URL=jdbc:postgresql://localhost:5432/cobrancapix
set DATASOURCE_USERNAME=postgres
set DATASOURCE_PASSWORD=!DATASOURCE_PASSWORD!
set SECURITY_USER_NAME=admin
set SECURITY_USER_PASSWORD=!SECURITY_USER_PASSWORD!

echo.
echo Variáveis configuradas para esta sessão!
echo.
echo Para usar permanentemente, execute como Administrador:
echo.
echo setx TELEGRAM_BOT_TOKEN "!TELEGRAM_BOT_TOKEN!"
echo setx DATASOURCE_PASSWORD "!DATASOURCE_PASSWORD!"
echo setx SECURITY_USER_PASSWORD "!SECURITY_USER_PASSWORD!"
echo.
echo Iniciando Maven...
echo.

mvn spring-boot:run

endlocal

