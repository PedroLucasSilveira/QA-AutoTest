#!/bin/bash

# Script: Testa a tentativa de criar um usuário com ID duplicado.

echo "--- EXECUTANDO: test_error_create_duplicate.sh ---"

# 1. Cria usuário 404
# 2. Tenta criar o mesmo usuário 404 de novo
# 6. Sair
OUTPUT=$(echo "1 404 David david@teste.com 1 404 Outro Outro@email.com 6" | java UserCrud)

if echo "$OUTPUT" | grep -q "ERRO: Usuario com ID 404 ja existe."; then
    echo ">>> PASSOU: O programa corretamente impediu a criacao de um usuario duplicado."
    exit 0
else
    echo ">>> FALHOU: O programa permitiu um ID duplicado ou a mensagem de erro foi incorreta."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi