#!/bin/bash

# Script: Testa o fluxo de sucesso de criar e ler um usuário.

echo "--- EXECUTANDO: test_create_and_read_success.sh ---"

# Comandos a serem enviados para o programa Java:
# 1. Escolhe a opção 1 (Criar)
# 2. Informa os dados: ID 101, Nome "Ana", Email "ana@teste.com"
# 3. Escolhe a opção 2 (Ler)
# 4. Informa o ID a ser lido: 101
# 5. Escolhe a opção 6 (Sair)
OUTPUT=$(echo "1 101 Ana ana@teste.com 2 101 6" | java UserCrud)

# Verifica se a saída contém as strings de SUCESSO e os dados corretos
if echo "$OUTPUT" | grep -q "SUCESSO: Usuario criado -> User ID: 101, Name: Ana" && \
   echo "$OUTPUT" | grep -q "ENCONTRADO: User ID: 101, Name: Ana, Email: ana@teste.com"; then
    echo ">>> PASSOU: Usuario foi criado e lido com sucesso."
    exit 0
else
    echo ">>> FALHOU: O resultado esperado nao foi encontrado."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi