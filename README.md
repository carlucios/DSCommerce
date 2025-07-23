# 🛒 DSCommerce API

API RESTful desenvolvida em Java com Spring Boot para gerenciar um sistema de comércio eletrônico, incluindo funcionalidades de produtos, pedidos, usuários e autenticação.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security (com suporte a JWT)
- PostgreSQL
- Flyway (para versionamento do banco de dados)
- Maven
- Docker + Docker Compose

---

## ⚙️ Requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://adoptium.net/)
- [Maven](https://maven.apache.org/)

---

## 🛠️ Configuração

### 1. Clone o projeto

```bash
git clone https://github.com/seu-usuario/dscommerce.git
cd dscommerce
```

### 2. Suba o banco de dados

O projeto usa PostgreSQL, que pode ser iniciado com Docker Compose:

```bash
docker-compose up -d
```

Isso criará um banco de dados PostgreSQL disponível em `localhost:5433` com as seguintes credenciais:

```
Banco: dscommerce
Usuário: postgres
Senha: postgres
Porta: 5433
```

> ⚠️ Verifique se a porta 5433 está livre em seu sistema.

### 3. Execute a aplicação

Com o banco rodando, você pode iniciar a aplicação usando:

```bash
mvn spring-boot:run
```

Isso:

- Iniciará o Spring Boot
- Aplicará automaticamente as **migrations do Flyway**
- Exporá a API em: `http://localhost:8080`

---

## ✅ Endpoints principais

# Endpoints - DSCommerce API

| Método | Caminho             | Acesso                        | Descrição                                      |
|--------|---------------------|-------------------------------|------------------------------------------------|
| GET    | /products           | Público                       | Lista todos os produtos com paginação         |
| GET    | /products/{id}      | Público                       | Retorna os detalhes de um produto específico  |
| POST   | /products           | Somente `ROLE_ADMIN`          | Cria um novo produto                           |
| PUT    | /products/{id}      | Somente `ROLE_ADMIN`          | Atualiza os dados de um produto                |
| DELETE | /products/{id}      | Somente `ROLE_ADMIN`          | Deleta um produto                              |
| GET    | /orders/{id}        | Somente `ROLE_ADMIN` ou Owner | Retorna os detalhes de um pedido  |
| POST   | /orders             | `ROLE_ADMIN` ou `ROLE_CLIENT` | Cria um novo pedido                            |
| GET    | /users/me           | `ROLE_ADMIN` ou `ROLE_CLIENT`        | Retorna os dados do usuário autenticado        |


---

## 🧪 Testes

Execute os testes com:

```bash
mvn clean test
```

---

## 🗃️ Migrations

As migrations são gerenciadas automaticamente com o [Flyway](https://flywaydb.org/). Elas estão localizadas no diretório:

```
src/main/resources/db/migration
```

---

## 🧑‍💻 Autor

Desenvolvido por Carlúcio Santos.


