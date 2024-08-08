# Sistema de Processamento de Pagamentos

Microsserviço para processamento de pagamentos com cartão de crédito

Este repositório refere-se ao microsserviço de pagamentos para atender aos requisitos do desafio proposto pela
pós tech em Arquitetura com desenvolvimento Java. Este projeto envolve a comunicação entre microserviços.

## Principais recursos deste microserviço

Responsável por:
* Realizar pagamentos;
* Consultar pagamentos por CPF;

## Tecnologias

* Spring Boot para a estrutura do serviço
* Spring Data JPA para manipulação de dados dos pagamentos
* PostgreSQL para persistência

## Desenvolvedores

- [Aydan Amorim](https://github.com/AydanAmorim)
- [Danilo Faccio](https://github.com/DFaccio)
- [Erick Ribeiro](https://github.com/erickmatheusribeiro)
- [Isabela França](https://github.com/fysabelah)

## Como executar

Clone o projeto com o comando abaixo.

```
git clone https://github.com/DFaccio/payment-service.git
```

Crie um arquivo .env no diretório root do projeto. Um exemplo de arquivo pode ser visto abaixo. Os valores podem ser
alterados, porém as chaves devem ser mantidas.

```
PROFILE=prod
DATABASE_PASSWORD=i@mr00t
DATABASE_USERNAME=postgres
DATABASE=payments
DATABASE_HOST=localhost:6062

# CARD_FEIGN quando card-service está local : http://localhost:7074
# CARD_FEIGN quando card-service está em docker : http://card-service:7074
CARD_FEIGN=http://localhost:7074

SWAGGER_SERVER_ADDRESS=http://localhost:7075
```

## Operações suportadas pela API
### Inclusão de pagamento
A operação de inclusão de pagamento é realizada através do método POST `/api/pagamentos` e recebe como parâmetros o CPF do usuário, o número do cartão, a data de validade do cartão, o CVV do cartão, o valor da transação, descrição e método de pagamento.

**Exemplo Request:**
```json
{
  "cpf": "21910056081",
  "cardNumber": "5568872479420825",
  "expirationDate": "0625",
  "cvv": "545",
  "value": 19.99,
  "paymentDescription": "Compra de um livro",
  "paymentMethod": "Cartão de crédito"
}
```

**Exemplo de resposta positiva:**
```json
{
  "paymentId": "fcb829c9-f4f0-43b2-8647-8052806ba0b5",
  "cpf": "21910056081",
  "cardNumber": "5568872479420825",
  "paymentMethod": "Cartão de crédito",
  "paymentDescription": "Compra de um livro",
  "paymentValue": 19.99,
  "transactionDate": "2024-08-08T10:15:30",
  "cardTransactionId": "123456",
  "paymentStatus": "APPROVED"
}
```

**Exemplos de resposta negativa:**
```json
{
  "statusCode": 500,
  "error": "Data Error",
  "message": "Pagamento recusado. Dados do cartão inválidos.",
  "path": "/pagamentos"
}
```
```json
{
  "statusCode": 402,
  "error": "Limit error",
  "message": "Pagamento recusado. Limite indisponível.",
  "path": "/pagamentos"
}
```

### Consulta de pagamento
A operação deconsulta de pagamento é realizada através do método GET `/api/pagamentos/cliente/{cpf}` e recebe como variável o CPF do usuário.

**Exemplo Request:**
```json
{
  "paymentId": "fcb829c9-f4f0-43b2-8647-8052806ba0b5",
  "paymentValue": 19.99,
  "paymentDescription": "Compra de um livro",
  "paymentMethod": "Cartão de crédito",
  "paymentStatus": "APPROVED"
}
```

## Estrutura da base de dados
* Para registrar os pagamentos foi criada uma entidade Payment, que registra os dados do cartão usado no pagamento, o CPF do portador do cartão, detalhes do pagamento