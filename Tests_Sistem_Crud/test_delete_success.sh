#!/bin/bash

# Script: Testa a deleção de um usuário.

echo "--- EXECUTANDO: test_delete_success.sh ---"

# 1. Cria usuário 303
# 2. Deleta usuário 303
# 3. Tenta ler o usuário 303 (esperando um erro)
# 6. Sair
OUTPUT=$(echo "1 303 Bia bia@teste.com 4 303 2 303 6" | java UserCrud)

if echo "$OUTPUT" | grep -q "SUCESSO: Usuario deletado -> User ID: 303" && \
   echo "$OUTPUT" | grep -q "ERRO: Usuario com ID 303 nao encontrado."; then
    echo ">>> PASSOU: Usuario foi deletado e a leitura subsequente falhou como esperado."
    exit 0
else
    echo ">>> FALHOU: O fluxo de delecao nao funcionou como esperado."
    echo "Saida obtida:"
    echo "$OUTPUT"
    exit 1
fi