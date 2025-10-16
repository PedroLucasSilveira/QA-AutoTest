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

Recomendo usar Maven para build e testes automáticos. A raiz do projeto já contém um `pom.xml`.

1. Verifique sua versão do Java compatível (11+):

```bash
java -version
```

2. Rodar os testes unitários com Maven:

```bash
mvn -B test
```

3. Para executar a aplicação console (modo antigo) diretamente via Maven/Exec plugin, você pode compilar e executar apontando stdin:

```bash
mvn -q -Dexec.mainClass="com.example.UserCrud" compile exec:java -Dexec.args="< input.txt"
```

Observação: os scripts antigos em `Tests_Sistem_Crud` foram mantidos para referência, mas o fluxo recomendado agora é via Maven/JUnit.

### UI tests (Selenium)

Este projeto inclui testes de UI baseados em Selenium que exercitam uma pequena UI web (Spring Boot + Thymeleaf).

- Localmente: é necessário ter um navegador Chrome/Chromium instalado. Se quiser forçar a execução dos UI tests localmente, exporte a variável de ambiente `RUN_UI_TESTS=1` antes de rodar os testes:

```bash
export RUN_UI_TESTS=1
mvn -B test
```

- No CI: o workflow do GitHub Actions já define `RUN_UI_TESTS=1` e instala Chromium, inicia a aplicação e executa os testes. Os UI tests são escritos para rodar headless no CI.

Se os UI tests falharem por falta de navegador ou driver, eles serão automaticamente pulados localmente para não quebrar o fluxo de desenvolvimento.

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


