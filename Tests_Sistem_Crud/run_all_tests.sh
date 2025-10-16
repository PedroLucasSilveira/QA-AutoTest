#!/bin/bash

# Define a pasta raiz do projeto para referências
PROJECT_ROOT="$(dirname "$(readlink -f "$0")")"
LIB_DIR="$PROJECT_ROOT/../lib" # Assumindo que 'lib' está em QA-AutoTest/lib

# Corrigindo problema de execução: o Bash não procura por scripts no diretório atual (PATH)
# Definimos a variável PATH para procurar scripts na pasta atual
export PATH=$PATH:$PROJECT_ROOT

echo "=========================================="
echo "=== INICIANDO SUITE DE TESTES DE CRUD  ==="
echo "=========================================="
echo

# Garante que o código Java está compilado
echo "Compilando código Java..."

# Construindo o CLASSPATH:
# 1. Inclui todas as JARs na pasta 'lib' (Selenium, JUnit, etc.)
# 2. Inclui a própria pasta de código-fonte para dependências internas
CLASSPATH="$LIB_DIR/*:$PROJECT_ROOT"

# Comando de compilação: Usamos o CLASSPATH e compilamos todos os arquivos .java
# dentro do diretório de testes.
# Nota: Você deve garantir que 'lib' contem todos os JARs.
javac -cp "$CLASSPATH" "$PROJECT_ROOT"/*.java
COMPILATION_EXIT_CODE=$?

if [ $COMPILATION_EXIT_CODE -ne 0 ]; then
    echo "ERRO FATAL: Falha na compilacao do codigo Java (javac). Verifique as dependências CLASSPATH e os erros de sintaxe."
    # Exibe a linha de compilação para facilitar a depuração
    echo "Comando: javac -cp \"$CLASSPATH\" \"$PROJECT_ROOT\"/*.java" 
    exit 1
fi

echo "Compilacao bem-sucedida."
echo

# Lista de todos os scripts de teste (Estão na mesma pasta do script principal)
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
# Nota: O 'cd' é feito para que os scripts encontrem os arquivos .class compilados.
cd "$PROJECT_ROOT" || exit 1 # Navega para o diretório dos testes

for script in "${TEST_SCRIPTS[@]}"; do
    if [ -f "$script" ]; then
        # Executa o script de teste
        bash ./"$script"
        
        # Verifica o código de saída do script executado
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

# Volta para o diretório original
cd - > /dev/null

# Resumo final
echo "=========================================="
echo "===             RESUMO DOS TESTES      ==="
echo "=========================================="
echo "Testes Executados: ${#TEST_SCRIPTS[@]}"
echo "Testes com SUCESSO: $PASSED_COUNT"
echo "Testes com FALHA: $FAILED_COUNT"
echo "=========================================="

# Saída final
if [ $FAILED_COUNT -gt 0 ]; then
    echo
    echo "Os seguintes testes falharam: $FAILED_TESTS"
    exit 1
else
    echo
    echo "Todos os testes passaram com sucesso!"
    exit 0
fi