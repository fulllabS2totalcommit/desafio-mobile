# desafio-mobfiq

First step: Fazer fork desse projeto e iniciar teu desafio! :)

Usar como referência os seguintes apps:
https://play.google.com/store/apps/details?id=com.root.polishop.Activities&hl=pt_BR

https://itunes.apple.com/br/app/polishop-eletrodom%C3%A9sticos-produtos-exclusivos/id517957706?mt=8 


Desafio

1) Criar uma vitrine, com duas colunas e utilizando os dados retornados da API

"API Search
[POST]
(Headers: “Content-Type : application/json”)
https://desafio.mobfiq.com.br/Search/Criteria
{
  “Query” : ”” (string),
  “Offset”: 0 (int)
  “Size”: 10 (int)
}" 

![alt text](http://i.imgur.com/ebjy0C6.png)
![alt text](http://i.imgur.com/k2w9h6S.png)

 
Requisitos:
Executar search de 10 produtos 
implementar “Carregar mais” ao final da lista buscando mais 10 produtos
Exibir imagens, preço de tabela, preço final, melhor opção de parcelamento e desconto
Implementar busca por texto.
 
2) Implementar Pushwoosh https://www.pushwoosh.com/

Requisitos:
[IOS] Implementar sem gerenciador de dependências
O aplicativo deve receber pushs estando aberto ou em background

3) Implementar árvore de categorias

(Headers: “Content-Type : application/json”)
[GET]
https://desafio.mobfiq.com.br/StorePreference/CategoryTree

![alt text](http://i.imgur.com/W5GlHjz.png)


Não é necessário usar ícones.



NÃO FAÇA TUDO EM  APENAS UM COMMIT!
