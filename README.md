# Order
Projeto para lidar com os pedidos realizados.

### Arquitetura

* ECS
* MySql
* SNS
* API Gateway
* ECR

Basicamente esse projeto irá gravar os pedidos em um MySql (SQL) e postar criações e alterações deles nos tópicos SNS da AWS necessários.

Irá subir a image no ECR pela pipeline e atualizar a task do ECR.

A API Gateway irá redirecionar as chamadas para o ALB e também conta com autorização de token, para permitir somente usuários que possuem autorização.

### SAGA Pattern

A pattern escolhida foi a coreografada para não ter um serviço fazendo tudo e pelo risco que tem de ele parar e o processo inteiro parar também, fora níveis de complexidade para modificações, pois quanto mais responsabilidade maior será o desafio para futuras modificações.
A coreografia foi feita utilizando o SNS, sendo assim posta as mensagens necessárias nele e lê quem tem o interesse na informação, sendo possível efetuar ações que ache necessário.

![Diagram](https://github.com/Group76/order/blob/main/docs/order.drawio.png)

### Como rodar
Necessário subir a infraestrutura do projeto [AWS Live](https://github.com/Group76/aws-live) e adicionar no Parameter Store a configuração:
* /config/order-api_prod/mercadoPagoExternalPosId = Id external POS do Mercado Pago
* /config/order-api_prod/mercadoPagoTestToken = Token de teste do Mercado Pago
* /config/order-api_prod/mercadoPagoUserId = User id do do Mercado Pago
* /config/order-api_prod/sqlPassword = Senha do MySql
* /config/order-api_prod/sqlUrl = Url do MySql
* /config/order-api_prod/sqlUser = Usuário do MySql

Após infraestrutura configurada é só rodar a actions que a pipeline irá fazer push da image e atualizar o a task definition da ECS.
Para a pipeline funcionar na AWS necessário também configurar no github as secrets:
* AWS_ACCESS_KEY_ID
* AWS_SECRET_ACCESS_KEY

Ou se preferir rodar local é só ter configurado o MySql (pode subir usando o arquivo [docker-compose](https://github.com/Group76/order/blob/main/compose.yaml)), porém é necessário comentar os envios para o SNS.

### OWASP ZAP
Antes: <https://github.com/Group76/order/tree/main/docs/awasp-zap>

O relatório que gerou não acusou nada, então não teve o antes/depois, porém coloquei também as verificações que fez e requests, para ser possível verificar que de fato rodou.

### Swagger
Collection: <https://github.com/Group76/order/blob/main/docs/api-docs.json>

![Swagger](https://github.com/Group76/order/blob/main/docs/swagger-ui_index.html.png)
