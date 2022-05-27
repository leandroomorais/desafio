# Desafio Solarz

Este aplicativo realiza uma requisição no site
http://esaj.tjrn.jus.br/cpo/pg/open.do para o processo de
número 0033765-49.2008.8.20.0001 e extrai as informações da página HTML do processo,
 salvando-as em banco de dados e disponibilizando-as para consumo através de API.



## Documentação da API

#### Retorna todos os itens
```http
  GET /api/
