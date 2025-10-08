#!/bin/bash

# Script: Testa o fluxo de sucesso de atualização de usuário.

echo "--- EXECUTANDO: test_update_success.sh ---"

# 1. Cria usuário 202
# 2. Atualiza usuário 202 para "Carlos" e "carlos@novo.com"
# 3. Lê usuário 202 para confirmar a mudança
# 6. Sair
OUTPUT=$(echo "1 202 Jose jose@teste.com 3 202 Carlos carlos@novo.com 2 202 6" | java UserCrud)

if echo "$OUTPUT" | grep -q "SUCESSO: Usuario atualizado -> User ID: 202, Name: Carlos" && \
   echo "$OUTPUT" | grep -q "ENCONTRADO: User ID: 202, Name: Carlos, Email: carlos@novo.com"; then
    echo ">>> PASSOU: Usuario foi atualizado e verificado com sucesso."
    exit 0
else
    echo ">>> FALHOU: Falha ao atualizar ou verificar o usuario."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi