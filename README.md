A API recebe pela URL /api/pedidos um JSON contendo as informações do pedido, o JSON pode ter dois formato, um é contendo somente o Id de um endereço já salvo no banco e outro contendo um endereço novo para salvar no banco:

JSON com o Id do endereço:

{
  "cliente" : 1,
  "valorTotal" : 70.20,
  "enderecoId" : 2,
  "produtos" : [
      {
        "id" : 2,
        "quantidade" : 1
      },
      {
        "id" : 1,
        "quantidade" : 1
      }
    ]
}

JSON contendo um endereço novo:

{
  "cliente" : 10,
  "valorTotal" : 70.20,
  "endereco" : {
    "logradouro" : "Rua Zé",
    "numero" : 100,
    "complemento" : "",
    "cidade" : "Teste",
    "estado" : "Opa",
    "cep" : "86045-804"
  },
  "produtos" : [
      {
        "id" : 2,
        "quantidade" : 1
      },
      {
        "id" : 1,
        "quantidade" : 1
      }
    ]
}


Caso a API recebe um JSON tendo o Id de um endereço existente e os dados de um novo endereço, será retornado uma mensagem de erro para o usuário.

A API faz algumas validações dos dados recebidos:

- Conferência do valor total da compra -> O sistema calcula se o valor total informado no JSON está coerente, somando o valor de cada item da compra e conferindo o valor total informado. Caso esteja incoerente, a API retorna uma mensagem informando o erro.
- Validação do endereço - > O sistema verifica se endereço informado pertence ao cliente, isso no caso do JSON que tem somente o Id do endereço, caso tenha um novo endereço, será salvo e vinculado com o cliente. Se o endereço não pertencer ao cliente, será enviado uma mensagem sobre esse erro, e se o JSON conter tanto um endereço novo e o Id de um existente, ou, nenhuma das informações, uma mensagem de erro para o usuário será enviado.
- Outras validações -> O sistema verifica se o payload do JSON está correto, se as informações necessárias estão presentes, se há produtos na lista e se o tipo de dado está certo. Para cada tipo de erro, o sistema retorna uma mensagem indicado que tipo de erro ocorreu.

Informações adicionais:

- Utilizei o MySQL como banco de dados, mas a API utiliza o H2 caso não encontre o banco. O hibernate está configurado para criar o banco e popular com alguns dados pré definidos toda vez que o projeto for rodado, e também quando realizar os testes unitários.
- O envio de mensagem pelo rabbitMQ, tanto o produtor e o consumidor estão implementados no projeto, a mensagem somente será enviada se o pedido ser salvo no banco de dados.
- O teste unitário envolve testar os métodos que extraem os dados do JSON, como também envolve fazer requisições HTTP enviando um JSON pré definido e verificar se a resposta de requisição é o esperado.
- Para realizar as requisições, utilizei um software chamado Postman.
