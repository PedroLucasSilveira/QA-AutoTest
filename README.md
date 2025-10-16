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

Notas e dicas para executar e depurar os UI tests

- Requisitos locais:
  - Google Chrome ou Chromium (compatível com a versão do driver Selenium). No Ubuntu/Debian você pode instalar o `google-chrome-stable` ou `chromium-browser`.
  - JDK 17 (o projeto usa Java 17 para compatibilidade com Spring Boot 3.x).

- Executando localmente:
  1. Instale o Chrome/Chromium no seu sistema.
  2. Exporte a variável para habilitar UI tests e execute o Maven:

```bash
export RUN_UI_TESTS=1
mvn -B test
```

  Observação: os testes UI foram escritos para rodar headless quando detectam um ambiente CI, mas localmente o navegador poderá abrir uma janela (dependendo da configuração do driver). Para forçar headless localmente, exporte `HEADLESS=1` também.

- No CI (GitHub Actions):
  - O workflow define `RUN_UI_TESTS=1`, instala o Google Chrome (via repositório oficial) e inicia a aplicação Spring Boot antes de executar `mvn test`.
  - Se um teste UI falhar na Actions, os logs de execução e (quando disponível) screenshots/artefatos são preservados como artefatos do job — confira a aba de Actions > run > Artifacts.

- Depuração quando um UI test falha:
  1. Reproduza localmente com `RUN_UI_TESTS=1` e (opcional) `HEADLESS=0` para ver o navegador.
  2. Aumente timeouts no teste ou adicione prints/logs para entender o estado da página.
  3. Verifique os relatórios Surefire (target/surefire-reports) e arquivos gerados pela execução (screenshots em target/test-screenshots/ se existirem).

- Observações sobre compatibilidade:
  - O CI foi ajustado para instalar `google-chrome-stable` do repositório oficial usando `--no-install-recommends` para evitar dependências problemáticas em runners Ubuntu 24.04.
  - Caso o ambiente local use uma versão antiga do Chrome, atualize para a versão mais recente para evitar incompatibilidades com o Selenium driver.

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


