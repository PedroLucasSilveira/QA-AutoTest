#!/bin/bash

# Script: Testa a tentativa de operar em um usuário inexistente.

echo "--- EXECUTANDO: test_error_read_nonexistent.sh ---"

# 1. Tenta ler o usuário 999 (não existe)
# 2. Tenta atualizar o usuário 999
# 3. Tenta deletar o usuário 999
# 6. Sair
OUTPUT=$(echo "2 999 3 999 Nome NovoEmail 4 999 6" | java UserCrud)

if echo "$OUTPUT" | grep -q "ERRO: Usuario com ID 999 nao encontrado." && \
   echo "$OUTPUT" | grep -q "ERRO: Nao e possivel atualizar. Usuario com ID 999 nao encontrado." && \
   echo "$OUTPUT" | grep -q "ERRO: Nao e possivel deletar. Usuario com ID 999 nao encontrado."; then
    echo ">>> PASSOU: O programa lidou corretamente com operacoes em um usuario inexistente."
    exit 0
else
    echo ">>> FALHOU: Mensagens de erro para usuario inexistente estao incorretas."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi