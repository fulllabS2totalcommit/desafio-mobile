# desafio-mobile

First step: Fazer fork desse projeto e iniciar teu desafio! :)

Usar como referência os seguintes apps:
https://play.google.com/store/apps/details?id=com.mobfiq.epocacosmeticos&hl=pt_BR

https://apps.apple.com/br/app/%C3%A9poca-cosm%C3%A9ticos-e-perfumes/id1418608927


## Desafio
Em Android gerar uma .apk.
Em React Native utilizar a versão 0.58 ou superior e gerar uma .apk
Não utilizar framework/libs que engessem o app como por exemplo EXPO, TypeScript.
### 1) Criar uma vitrine, com duas colunas e utilizando os dados retornados da API

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
   - Deve haver uma barra de busca, onde o usuário pode procurar produtos. O texto digitado pelo usuário será enviado na propriedade ```Query```. A busca deve ser iniciada após o usuário digitar o terceiro caractere.Por exemplo se quiser buscar por Cafeteira a busca iniciará quando digitar "Caf".

### 2) Implementar árvore de categorias

(Headers: “Content-Type : application/json”)  
[GET]  
https://desafio.mobfiq.com.br/StorePreference/CategoryTree  

![alt text](http://i.imgur.com/W5GlHjz.png)

Requisitos:

1. Exibir a lista de categorias.
2. Ao clicar em uma categoria, o usuário deve ser direcionado para as subcategorias.
3. O layout da tela de subcategorias é igual à tela de categorias.
4. O título da tela da subcategoria deve ser o nome de sua categoria pai.

** Não é necessário usar ícones, mas será um diferencial.

#### REGRAS:

- Tudo o que foi solicitado no desafio é fácil de ser realizado, será diferencial o que for implementado a mais ou melhoria.
- NÃO FAÇA TUDO EM  APENAS UM COMMIT.
- Tratamento de erros será um grande diferencial.

#### O QUE ESTAMOS AVALIANDO:

- O aplicativo funcionando, portanto nos envie uma apk.
- Código deve ser versionado no git.
- Código bem estruturado, com divisão de responsabilidades.
- Uso de bibliotecas adequadas, sem a sensação de "gambiarra".
- Código apresentável, ou seja, cuidado para não deixar código comentado, métodos vazios, prints perdidos, etc.
- Código legível.
- Ausência de bugs.
- Boa usabilidade e noção de estética.
- Mensagens de commit relevantes.

#### BOAS SURPRESAS:

- Apesar de não estar no escopo, o candidato é bem-vindo para implementar testes unitários, animações, outras funcionalidades ou qualquer outra coisa que enriqueça o projeto. Porém, é importante lembrar que isso só deve ser feito caso a entrega do escopo esteja garantida.
