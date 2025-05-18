# Loja Virtual Simplificada (API REST) - Projeto By Solution

Este projeto foi disponibilizado para estudo, desenvolvimento e para servir como base no trabalho com Java na By Solution.

---

## Nome da Loja: **OnHardware**

## Objetivo do Projeto

Desenvolver uma API REST para uma Loja Virtual Simplificada que permita:

- Cadastrar produtos
- Criar carrinho de compras
- Adicionar e remover produtos no carrinho
- Finalizar uma compra
- Rodar a aplicação com Docker (Spring Boot + banco de dados PostgreSQL)

---

## Funcionalidades da API

### Produto

- Criar produto
- Buscar todos os produtos
- Buscar produto por ID
- Atualizar produto
- Deletar produto

### Carrinho

- Criar carrinho
- Adicionar item (produto + quantidade)
- Remover item
- Visualizar itens do carrinho
- Visualizar total da compra
- Finalizar carrinho (limpar itens)

---

## Requisitos Técnicos

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL rodando via Docker
- Dockerfile e docker-compose para facilitar deploy local
- Maven como gerenciador de dependências
- Testes da API com Postman ou Insomnia

---

## Regras de Negócio e Boas Práticas

- Validações com anotações (`@NotNull`, `@Positive`, etc.)
- Mensagens de erro amigáveis para o usuário
- Logging básico para facilitar diagnóstico
- Aplicação dos pilares da Programação Orientada a Objetos (POO)
- Separação clara de responsabilidades entre Controller, Service e Repository
- Persistência e comunicação com banco de dados usando Spring Data JPA
- Deploy local automatizado via Docker e Docker Compose
- Testes funcionais com ferramentas como Postman ou Insomnia

---

## Criar APIs REST com boas práticas:

- Entender os pilares da OO aplicados
- Separação de responsabilidades (controller, service, repository)
- Persistência com Spring Data JPA
- Comunicação com banco de dados
- Deploy local com Docker e Docker Compose
- Testar com Postman/Insomnia
