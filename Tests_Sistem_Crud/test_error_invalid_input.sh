#!/bin/bash

# Script: Testa o tratamento de erro para entrada inválida (não numérica).

echo "--- EXECUTANDO: test_error_invalid_input.sh ---"

# 1. Envia 'abc' como opção de menu, o que deve causar um erro de input.
# 6. Sair (para garantir que o programa termine)
OUTPUT=$(echo "abc 6" | java UserCrud)

if echo "$OUTPUT" | grep -q "ERRO: Entrada invalida."; then
    echo ">>> PASSOU: O programa tratou a entrada invalida (nao numerica) corretamente."
    exit 0
else
    echo ">>> FALHOU: O programa nao exibiu a mensagem de erro esperada para entrada invalida."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi