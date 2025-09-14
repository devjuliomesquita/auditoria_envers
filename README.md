# Projeto de Demonstração de Auditoria com Hibernate Envers

## 1. Objetivo do Projeto

Este projeto é uma aplicação Spring Boot desenhada para demonstrar uma implementação robusta e avançada de auditoria de dados utilizando **Hibernate Envers**. O objetivo principal é mostrar como configurar um sistema de auditoria que não apenas rastreia o histórico de alterações das entidades, mas também o faz de maneira eficiente e organizada, utilizando uma arquitetura de schemas separados e estratégias avançadas do Envers.

## 2. Tecnologias Utilizadas

- **Backend:** Java 17+, Spring Boot 3+
- **Persistência de Dados:** Spring Data JPA, Hibernate
- **Auditoria:** Hibernate Envers
- **Banco de Dados:** PostgreSQL
- **Migrações de Schema:** Flyway
- **Containerização:** Docker e Docker Compose (para o ambiente de desenvolvimento)
- **Build:** Apache Maven
- **Documentação da API:** Springdoc OpenAPI (Swagger)

## 3. Conceitos e Funcionalidades Implementadas

Este projeto vai além do básico do Hibernate Envers e implementa os seguintes conceitos:

#### a. Arquitetura Dual-Schema
Para uma clara separação de responsabilidades, o banco de dados é organizado em dois schemas:
- `core_system`: Contém as tabelas da aplicação principal (negócio), como `livros`, `autores`, etc.
- `core_audit`: Dedicado exclusivamente a armazenar todas as tabelas geradas pelo Envers para o controle de auditoria (`livros_aud`, `revision_audited`, etc.).

#### b. Estratégia de Auditoria por Validade (`ValidityAuditStrategy`)
Em vez da estratégia padrão, utilizamos a `ValidityAuditStrategy`. Ela não cria apenas uma nova linha para cada alteração, mas mantém um "período de validade" para cada registro auditado, usando colunas como `rev_start` e `rev_end`. Isso torna consultas sobre o estado de uma entidade em um ponto específico no tempo muito mais eficientes.

#### c. Entidade de Revisão Personalizada (`RevisionAuditedEnt`)
Foi criada uma entidade de revisão customizada para enriquecer os dados de cada transação auditada. Além do timestamp e do número da revisão, armazenamos:
- O ID do usuário que realizou a ação.
- O nome do usuário.
- O endereço de IP de origem da requisição.

#### d. Rastreamento de Entidades Modificadas
A configuração `track_entities_changed_in_revision` está ativa, fazendo com que o Envers armazene em uma tabela (`modified_entity_names`) exatamente quais tipos de entidade foram modificados em cada revisão, facilitando a análise do histórico.

#### e. Geração de Migrações com Base no JPA
O projeto está configurado para que o Hibernate possa gerar automaticamente um script SQL (`db_update.sql`) com as alterações de schema necessárias com base nas entidades JPA. Este script serve como referência para a criação de novos arquivos de migração do Flyway, mantendo um processo de evolução de schema controlado e versionado.

## 4. Como Executar o Projeto

#### Pré-requisitos
- JDK 17 ou superior
- Apache Maven 3.8+
- Docker e Docker Compose

#### Passo 1: Iniciar o Ambiente
O ambiente de desenvolvimento, incluindo o banco de dados PostgreSQL, é gerenciado pelo Docker Compose.

```bash
# Navegue até a pasta docker
cd docker

# Inicie os containers em background
docker-compose up -d
```

#### Passo 2: Preparar e Migrar o Banco de Dados
O Flyway é responsável por criar e gerenciar o schema do banco de dados. Ao executar pela primeira vez ou após uma alteração nos scripts de migração, execute os seguintes comandos na raiz do projeto:

```bash
# (Opcional, mas recomendado) Limpa o banco de dados para uma instalação limpa
mvn flyway:clean

# Executa as migrações para criar as tabelas nos schemas corretos
mvn flyway:migrate
```

#### Passo 3: Executar a Aplicação
Use o Maven para iniciar a aplicação Spring Boot.

```bash
# Na raiz do projeto
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080/api`.

#### Passo 4: Acessar a Documentação da API
A API pode ser explorada e testada através do Swagger UI, que estará disponível no seguinte endereço:
- [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)
