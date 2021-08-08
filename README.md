Notes about my project:
Things i should have done:
- There could be a ProgressBar to show the user that there is data being downloaded.
- I should have implemented cache in jsonRequests.
- I should have encapsulated some methods, like jsonRequests.
- Maybe it is possible to reuse the categoryAdapter to be used in the subCategoryAdapter.

# desafio-mobfiq

First step: Do fork from this project and start the challenge! :)

Use these apps as a reference:
https://play.google.com/store/apps/details?id=com.root.polishop.Activities&hl=pt_BR

https://itunes.apple.com/br/app/polishop-eletrodom%C3%A9sticos-produtos-exclusivos/id517957706?mt=8 


## Challenge

### 1) Create a "vitrine", with two columns and using the data returned from the API

API Search  
[POST]  
(Headers: “Content-Type : application/json”)  
https://desafio.mobfiq.com.br/Search/Criteria  
```
{
  "Query" : "" (string),
  "Offset": 0 (int),
  "Size": 10 (int)
} 
```

![alt text](http://i.imgur.com/ebjy0C6.png)
![alt text](http://i.imgur.com/k2w9h6S.png)

 
Requisitos:

1. Executar search de 10 produtos
   - Ao abrir o App, o usuário deve ver a home, que exibe 10 produtos no carrosel. Os produtos da home são o resultado da consulta à API omitindo o parâmetro ```Query```.
2. Implementar scroll infinito
   - Quando o usuário rolar até o final da tela, os 10 próximos produtos devem ser exibidos.
3. Exibir imagem, preço de tabela, preço final, melhor opção de parcelamento e desconto
   - Estas informações devem ser a do SKU/Seller com o menor preço final
4. Implementar busca por texto
   - Deve haver uma barra de busca, onde o usuário pode procurar produtos. O texto digitado pelo usuário será enviado na propriedade ```Query```.

### 2) implement category tree

(Headers: “Content-Type : application/json”)  
[GET]  
https://desafio.mobfiq.com.br/StorePreference/CategoryTree  

![alt text](http://i.imgur.com/W5GlHjz.png)

Requisitos:

1. Exibir a lista de categorias.
2. Ao clicar em uma categoria, o usuário deve ser direcionado para as subcategorias.
3. O layout da tela de subcategorias é igual à tela de categorias.
4. O título da tela da subcategoria deve ser o nome de sua categoria pai.

** Não é necessário usar ícones.

#### Rules:

- PRAZO: 4 DIAS PARA ENTREGAR O QUE CONSEGUIR ATÉ LÁ
- NÃO FAÇA TUDO EM  APENAS UM COMMIT

#### What we are looking for:

- Código bem estruturado, com divisão de responsabilidades.
- Uso de bibliotecas adequadas, sem a sensação de "gambiarra".
- Código apresentável, ou seja, cuidado para não deixar código comentado, métodos vazios, prints perdidos, etc.
- Código legível.
- Ausência de bugs.
- Boa usabilidade e noção de estética.
- Mensagens de commit relevantes.

#### Good surprises:

- Apesar de não estar no escopo, o candidato é bem-vindo para implementar testes unitários, animações, outras funcionalidades ou qualquer outra coisa que enriqueça o projeto. Porém, é importante lembrar que isso só deve ser feito caso a entrega do escopo esteja garantida.
