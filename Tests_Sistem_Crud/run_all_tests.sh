#!/bin/bash

# Script MASTER que executa todos os scripts de teste DETAILS.

echo "=========================================="
echo "=== INICIANDO SUITE DE TESTES DE CRUD  ==="
echo "=========================================="
echo

# Garante que o código Java está compilado
echo "Compilando UserCrud.java..."
javac UserCrud.java
if [ $? -ne 0 ]; then
    echo "ERRO FATAL: Falha na compilacao do codigo Java. Abortando testes."
    exit 1
fi
echo "Compilacao bem-sucedida."
echo

# Lista de todos os scripts de teste
TEST_SCRIPTS=(
    "test_create_and_read_success.sh"
    "test_update_success.sh"
    "test_delete_success.sh"
    "test_error_create_duplicate.sh"
    "test_error_read_nonexistent.sh"
    "test_error_invalid_input.sh"
)

# Variáveis para contar sucessos e falhas
PASSED_COUNT=0
FAILED_COUNT=0
FAILED_TESTS=""

# Loop para executar cada script
for script in "${TEST_SCRIPTS[@]}"; do
    if [ -f "$script" ]; then
        # Executa o script de teste
        ./"$script"
        
        # Verifica o código de saída do script executado
        # Em shell, 0 significa sucesso, qualquer outro valor significa erro.
        if [ $? -eq 0 ]; then
            ((PASSED_COUNT++))
        else
            ((FAILED_COUNT++))
            FAILED_TESTS+="$script "
        fi
        echo
    else
        echo "AVISO: Script de teste $script nao encontrado!"
    fi
done

# Resumo final
echo "=========================================="
echo "===           RESUMO DOS TESTES        ==="
echo "=========================================="
echo "Testes Executados: ${#TEST_SCRIPTS[@]}"
echo "Testes com SUCESSO: $PASSED_COUNT"
echo "Testes com FALHA: $FAILED_COUNT"
echo "=========================================="

# Se houver falhas, lista quais foram e sai com erro
if [ $FAILED_COUNT -gt 0 ]; then
    echo
    echo "Os seguintes testes falharam: $FAILED_TESTS"
    exit 1
else
    echo
    echo "Todos os testes passaram com sucesso!"
    exit 0
fi