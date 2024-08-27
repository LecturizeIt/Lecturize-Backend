# Lecturize It - Backend

Este repositório contém o código-fonte do backend do projeto **Lecturize It**. O frontend do projeto está disponível em um repositório separado, que pode ser encontrado [aqui](https://github.com/LecturizeIt/Lecturize-Frontend).

## Índice

- [Pré-requisitos](#pr)
- [Instalação](#instalacao)
- [Execução](#execucao)

## [Pré-requisitos](#pr)

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- **Java 21**
- **MySQL** (versão 8.0 ou superior)
- **Docker** (Opcional)

## [Instalação](#instalacao)

1.  Clone este repositório para o seu ambiente local:

    ```bash
    git clone https://github.com/LecturizeIt/Lecturize-Backend.git
    ```

2.  Navegue até o diretório do repositório:

    ```bash
    cd Lecturize-Backend
    ```


## [Execução](#execucao)

### Sem Docker

1.  Vá até o arquivo `application.properties` localizado em `src/main/resources/application.properties`
2.  Altere os campos `spring.datasource.username` e `spring.datasource.password` para os respectivos valores configurados no seu MySQL.
    - ![Capture](https://github.com/user-attachments/assets/fd2f9e77-d9fa-42bc-889f-ff33ce803454)
    - *Troque os valores no local sublinhado*
3.  Execute a aplicação.

### Com Docker

#### Para usuários do Windows:

- Execute o script PowerShell:

    ```powershell
    .\run_docker.ps1
    ```


#### Para usuários Linux/MacOS:

1.  Faça a construção da aplicação com a ferramenta *maven*:

    ```bash
    # Bash
    chmod +x mvnw
    ./mvnw clean install -DskipTests
    ```

2.  Inicie os containers da aplicação:

    ```bash
    # Bash
    docker compose up
    ```
