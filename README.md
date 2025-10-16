# QA-AutoTest

Projeto de testes automatizados simples para operações CRUD em Java.

## Visão geral

Este repositório contém classes Java compiladas e scripts de shell que executam testes básicos (criar, ler, atualizar, deletar) contra uma implementação de CRUD localizada em `UserCrud.java`/`UserService.class`.

Estrutura principal:

- `User.class`, `UserCrud.class`, `UserService.class`: classes compiladas Java.
- `UserCrud.java`: código fonte Java do CRUD.
- `Tests_Sistem_Crud/`: scripts de testes em shell.
  - `run_all_tests.sh`: executa todos os testes.
  - `test_create_and_read_success.sh`, `test_update_success.sh`, `test_delete_success.sh`, etc.: testes individuais.

## Requisitos

- Java JDK 11+ (ou a versão usada para compilar as classes presentes).
- Bash (scripts de shell).

## Como executar os testes

1. Abra um terminal na raiz do repositório (`/workspaces/QA-AutoTest`).
2. Certifique-se de ter o Java disponível:

```bash
java -version
```

3. Torne os scripts executáveis (se necessário) e execute todos os testes:

```bash
cd Tests_Sistem_Crud
chmod +x run_all_tests.sh
./run_all_tests.sh
```

4. Para executar um teste individual, por exemplo o de criação/consulta:

```bash
./test_create_and_read_success.sh
```

## Desenvolvimento

Se quiser recompilar o código Java (é necessário ter o `UserCrud.java` atualizado):

```bash
javac UserCrud.java
```

Isso gerará/atualizará os arquivos `.class` que os scripts de teste usam.

## Estrutura dos testes

Os scripts em `Tests_Sistem_Crud` executam binários Java e verificam saídas simples. Inspecione os scripts para entender o que cada teste valida.

## Contribuição

1. Faça um fork/branch.
2. Adicione/edite código ou testes.
3. Compile (`javac`) e execute os scripts.
4. Abra um pull request com descrição clara do que foi alterado.

## Observações

- As classes `.class` já estão incluídas no repositório; se houver diferença de versão do JDK, recompile localmente.
- Se precisar que eu crie um passo a passo para configurar um ambiente de CI (GitHub Actions) para rodar esses testes automaticamente, diga qual runner prefere (Ubuntu/macOS) e eu gero o workflow.

---

Arquivo gerado automaticamente por assistente — atualize conforme necessário.
