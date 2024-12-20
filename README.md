
# hyperativa-desafio

Desafio Hyperativa Back-end


## Compilar a aplicação

A aplicação tem suas dependências gerenciadas pelo maven. Para conveniência, no diretório raiz existe um script (apenas ambiente Linux) para essa finalidade, mas pode ser compilada manualmente rodando:
> mvn clean install

Para usar o script, rodar a partir da raiz do repositório:
> ./build.sh

Obs.: supõe-se que tanto o Maven quanto o JDK 17 estejam no PATH do sistema.


## Rodar a aplicação

1) Apenas com Java
A aplicação pode ser iniciada usando apenas o java:
> java -jar desafio-hyperativa-backend-0.0.1-SNAPSHOT.jar

Se for necessário reconfigurar alguma propriedade, editar o arquivo application.yml, e iniciar apontando para o arquivo da seguinte forma:
> java -jar desafio-hyperativa-backend-0.0.1-SNAPSHOT.jar --spring.config.additional-location=/caminho/para/application.yml

2) Usando docker
2.1) No diretório raiz existe um script que gera a imagem com a aplicação localmente:
> ./build_docker_image.sh

2.2) Após passo 2.1, a aplicação pode ser iniciada a partir do diretório /docs/docker do repositório, utilizando o plugin docker compose:
> docker compose up

Obs.: caso seja necessária a modificação de alguma propriedade, editar o arquivo docker-compose.yml antes de iniciar a aplicação.
Obs2.: supõe-se Docker instalado no sistema e com binário no PATH.

## Configurações padrão:
A aplicação sobe por padrão na porta 9001, e utiliza por padrão um banco de dados em memória (hsqldb). Para conexão com MySQL, deve ser configurado o driver correto e url, usuário e senha para a conexão na seção environment. Segue exemplo de configuração para MySQL no arquivo docker-compose.yml:
>- DATASOURCE_DRIVER=com.mysql.cj.jdbc.Driver
>- DATASOURCE_URL=jdbc:mysql://localhost:3306/mydatabase 
>- DATASOURCE_USERNAME=myuser
>- DATASOURCE_PASSWORD=mypassword

## Logs e Audits
Os logs da aplicação são escritos no arquivo server.log, no caminho configurado na variável LOG_PATH. Se for rodada com docker, indicar o ponto de montagem do volume para os logs no arquivo docker-compose.yml:
> **/tmp**:/opt/booter
As audits da aplicação, que consistem no registro de todas as requisições e respostas trafegadas, se encontram no arquivo audit.log, no mesmo local definido para os logs conforme acima.

Obs.: para audits utilizei o framework de logs Logbook, configurado para mascarar campos sensíveis.

## Premissas e considerações adotadas

- O campo pan é sempre guardado criptografado na base, e sempre é logado (em audits e logs, quando necessário) de forma mascarada, expondo apenas o bin (6 primeiros dígitos) e os últimos 4 dígitos.

- Considerei pelo enunciado do desafio que não seria necessário descer o pan em nenhuma consulta, apenas o id do registro do cartão, portanto, o dado criptografado nunca é aberto nos endpoints da aplicação, mas a implementação para tal existe, caso fosse necessário no futuro.

- Para o uso dos endpoints, é necessária uma chamada para obtenção de token de acesso no endpoint de autenticação, conforme descrito mais abaixo. Realizei uma implementação simples, que aceita apenas o usuário "usuario", senha "senha", e que devolve no token sempre o mesmo conjunto de roles. Em uma situação real de produção, essa implementação chamaria um gerenciador de autenticação, como Keycloak, onde seriam configurados os diversos usuários e os roles de cada um. No diretório /docs/postman se encontra uma collection Postman com todos os endpoints, incluindo o de autenticação com os valores corretos populados.

- Para otimização aos acessos do BD, optei por utilizar o tomcat-jdbc no lugar do hikariCP (padrão do spring-boot) por ter tido melhores resultados de performance em situações de alta demanda na minha experiêndia profissional. 

## TODO

- Configurar as propriedades de pool do banco de acordo com a performance esperada para um dado volume esperado transacional.
- Gerar certificado para o servidor, incluir em uma keystore e configurar na aplicação para que todas as comunicações sejam seguras com TLS.
- Validação dos dados na chegada das requests (BeanValidator e/ou validação manual)
- Testes unitários de todas as classes não POJO
- Criação de índice sobre as colunas *pan* e *bin* da tabela *Cartao* para otimizar consultas.
- Criação dos scripts sql para criação do schema (no momento aplicação está configurada para criar automaticamente a partir do scan das Entities).
- Chaves e senhas configuradas no arquivo application.yml e/ou no docker-compose.yml serem criptografadas e abertas em tempo de execução na subida da aplicação.

## API

Obs.: Todas as chamadas abaixo estão contidas em uma collection Postman em /docs/postman

### GET /desafio/auth/token
#### Request

1) **Request Headers:** 

|  Header         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|Authorization          | Basic Auth           | Deve conter usuário e senha no formato Basic Auth. Usuário e senha devem ser "usuario" e "senha" (outras combinações resultam em acesso não autorizado)

2) **Request Body:**
Nenhum (body vazio)

#### Response

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|token          | AN            | Token jwt codificado em Base64, que deverá ser utilizado para acesso aos outros endpoints.| 

Exemplo de resposta:
```
{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDEiLCJzdWIiOiJ1c3VhcmlvIiwiZXhwIjoxNzM0NzM5NDk5LCJpYXQiOjE3MzQ3MzU4OTksInNjb3BlIjpbInJvbGUxIiwicm9sZTIiLCJHVUVTVCJdfQ.pkGN8TmT5gFpAP50nBHUuS2lErGLCh7Rp-Hcvex8jKo"
}
```


### POST /desafio/lotes

1) **Request Headers:** 

|  Header         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|Authorization          | Bearer token            | Deve conter o token válido gerado na API /desafio/auth/token| 
|Content-Type          | multipart/form-data            | indica que esta API receberá um arquivo binário (txt) com informações do lote| 

2) **Request Body:**
Tipo form-data.

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|arquivo          | binário / txt            | Arquivo com dados do lote a ser importado. Exemplo disponível em /docs/enunciado/input.txt| 

#### Response

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|msgResp          | AN            | Mensagem indicando resultado da operação.| 
|codResp          | N            | Código de resposta da aplicação.| 
|cartoes          | array            | Array de objetos *cartao*| 
|cartoes[].idCartao          | ANS            | Código identificador do registro do cartão inserido na base| 
|lote.nome          | AN            | Nome do lote | 
|lote.data          | N            | String representando a data do lote, no formato AAAAMMDD | 
|lote.identificadorLote          | AN            | Código de identificação do lote | 
|lote.quantidadeRegistros          | N            | Número de registros presentes no lote | 


Exemplo de resposta:
```
{
  "msgResp": "Lote inserido com sucesso",
  "codResp": "00",
  "cartoes": [
    {
      "idCartao": "284e5b25-575e-4e2f-badd-4ca07621a635"
    },
    {
      "idCartao": "a12aeccc-1236-4638-ade9-478b82160309"
    },
    {
      "idCartao": "284e5b25-575e-4e2f-badd-4ca07621a635"
    },
    {
      "idCartao": "fc32b1a1-c1b6-4927-b47b-cec11bbd8283"
    },
    {
      "idCartao": "841c7c33-11ea-4de6-ab74-d3839a677743"
    },
    {
      "idCartao": "47a27702-7057-4eeb-ae97-ba722fe752e2"
    },
    {
      "idCartao": "ce905464-c8a2-4045-8e4e-7e3e41dfc265"
    },
    {
      "idCartao": "4d01724e-d0fd-4ea6-9405-308dea3d2c7a"
    },
    {
      "idCartao": "5c19fdcd-b3c0-437e-a50f-2997b84a1f5a"
    },
    {
      "idCartao": "4d01724e-d0fd-4ea6-9405-308dea3d2c7a"
    }
  ],
  "lote": {
    "nome": "DESAFIO-HYPERATIVA           ",
    "data": "20180524",
    "identificadorLote": "LOTE0001",
    "quantidadeRegistros": 10
  }
}
```

### POST /desafio/cartoes

1) **Request Headers:** 

|  Header         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|Authorization          | Bearer token            | Deve conter o token válido gerado na API /desafio/auth/token| 
|Content-Type          |          | deve conter o valor **application/json**| 

2) **Request Body:**
Tipo form-data.

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|pan          | N            | Número do cartão a ser cadastrado |

Exemplo de requisição:
```
{
	"pan": "4456897999999999124"
}
```

#### Response

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|msgResp          | AN            | Mensagem indicando resultado da operação.| 
|codResp          | N            | Código de resposta da aplicação.| 
|cartoes          | array            | Array de objetos *cartao*| 
|cartoes[].idCartao          | ANS            | Código identificador do registro do cartão inserido na base| 



Exemplo de resposta:
```
{
  "msgResp": "Cartao inserido com sucesso",
  "codResp": "00",
  "cartoes": [
    {
      "idCartao": "50defd68-ce16-42cc-b3a1-c68ca32321be"
    }
  ]
}
```

### GET /desafio/cartoes/{pan}

1) **Request Headers:** 

|  Header         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|Authorization          | Bearer token            | Deve conter o token válido gerado na API /desafio/auth/token| 


2) **Request URL Params:**

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|pan          | N            | Número do cartão a ser consultado |

Exemplo de URI:
```
http://localhost:9001/desafio/cartoes/4456897999999999124
```

#### Response

|  Campo         |Formato                          |Descrição                         |
|----------------|-------------------------------|-----------------------------|
|msgResp          | AN            | Mensagem indicando resultado da operação.| 
|codResp          | N            | Código de resposta da aplicação.| 
|cartoes          | array            | Array de objetos *cartao*| 
|cartoes[].idCartao          | ANS            | Código identificador do registro do cartão inserido na base| 



Exemplo de resposta:
```
{
  "msgResp": "Cartao existe na base",
  "codResp": "00",
  "cartoes": [
    {
      "idCartao": "50defd68-ce16-42cc-b3a1-c68ca32321be"
    }
  ]
}
```