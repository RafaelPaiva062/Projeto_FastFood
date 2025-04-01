# Sistema Fast Food

## Visão Geral
Este sistema é um aplicativo de gerenciamento de pedidos de um fast food. Ele utiliza Java e está estruturado em classes que representam produtos, pedidos, cardápios e um sistema de login. A conexão com o banco de dados é feita utilizando JDBC com MySQL.

## Estrutura do Projeto
O projeto está dividido nas seguintes classes principais:

### 1. **ConexaoMy.java** (Gerenciamento de Conexão com o Banco de Dados)
- Classe responsável por estabelecer conexão com o banco de dados.
- Fornece métodos para conectar ao banco e verificar a conexão.

**Métodos:**
- `getConnection()`: Retorna uma conexão ativa com o banco de dados.
- `conectar()`: Tenta conectar e imprime mensagens de erro, caso haja falha.

---

### 2. **Server.java** (Interface de Servidor)
- Define os métodos obrigatórios para manipulação de produtos e pedidos.

**Métodos:**
- `historicoPedidos()`: Lista histórico de pedidos.
- `lisTodos()`: Retorna todos os produtos cadastrados.
- `buscarNome(String nome)`: Busca produtos por nome.
- `listarProdutosT(String tipo)`: Lista produtos por tipo.
- `buscarId(int id)`: Busca um produto pelo ID.
- `atualizar(Produto produto)`: Atualiza um produto existente.
- `inserir(Produto produto)`: Insere um novo produto.
- `inserirHistorico(Pedidos pedido, String pagamento)`: Adiciona um pedido ao histórico.
- `remover(int idproduto)`: Remove um produto pelo ID.
- `pesquisarPedidos(String nomeUsuario, String data)`: Pesquisa pedidos por usuário e data.

---

### 3. **Cordapios.java** (Gerenciamento do Cardápio)
- Implementa `Server` e gerencia produtos do cardápio.
- Insere, busca, atualiza e remove produtos do banco de dados.
- Manipula pedidos e seus registros históricos.

**Métodos:**
- `inserir(Produto produto)`: Adiciona um produto ao banco de dados.
- `buscarId(int id)`: Retorna um produto pelo ID.
- `atualizar(Produto produto)`: Atualiza um produto existente.
- `remover(int idproduto)`: Remove um produto pelo ID.
- `listarProdutosT(String tipo)`: Lista produtos de um determinado tipo (Lanche ou Bebida).
- `lisTodos()`: Retorna todos os produtos do cardápio.
- `inserirHistorico(Pedidos pedido, String formaPagamento)`: Registra um pedido no histórico.
- `historicoPedidos()`: Lista pedidos registrados.
- `buscarNome(String predicado)`: Busca produtos por nome.

---

### 4. **Produto.java** (Classe Base para Produtos)
- Classe abstrata que representa um produto genérico.

**Atributos:**
- `id_produto` *(int)* – Identificador único do produto.
- `nome_produto` *(String)* – Nome do produto.
- `preco_produto` *(double)* – Preço do produto.
- `promocao` *(String)* – Promoção associada ao produto.

**Métodos:**
- `getId_produto()` / `setId_produto(int id_produto)`
- `getNome_produto()` / `setNome_produto(String nome_produto)`
- `getPreco_produto()` / `setPreco_produto(double preco_produto)`
- `getPromocao()` / `setPromocao(String promocao)`
- `toString()` *(abstrato)* – Deve ser implementado pelas subclasses.

---

### 5. **Lanche.java** (Produto Específico - Lanche)
- Extende `Produto` e adiciona um atributo `vegano`.

**Atributos:**
- `vegano` *(boolean)* – Indica se o lanche é vegano.

**Métodos:**
- `isVegano()` – Retorna `true` se o lanche for vegano.
- `toString()` – Retorna uma string formatada com os detalhes do lanche.

---

### 6. **Bebida.java** (Produto Específico - Bebida)
- Extende `Produto` e adiciona um atributo `alcoolico`.

**Atributos:**
- `alcoolico` *(boolean)* – Indica se a bebida contém álcool.

**Métodos:**
- `isAlcoolico()` – Retorna `true` se a bebida contiver álcool.
- `toString()` – Retorna uma string formatada com os detalhes da bebida.

---

### 7. **Pedidos.java** (Gerenciamento de Pedidos)
- Classe que gerencia a adição de produtos a um pedido e seu total.
- Contém informações do usuário e métodos para listar produtos do pedido.

**Atributos:**
- `produtos` *(List<Produto>)* – Lista de produtos no pedido.
- `nomeUsuario` *(String)* – Nome do usuário que fez o pedido.

**Métodos:**
- `getNomeUsuario()` – Retorna o nome do usuário.
- `adicionarProduto(Produto produto)` – Adiciona um produto ao pedido.
- `getProdutos()` – Retorna a lista de produtos no pedido.
- `listarProdutos()` – Retorna uma string com a lista de produtos e o total do pedido.
- `calcularTotal()` – Calcula o valor total do pedido.

---

### 8. **Login.java** (Gerenciamento de Login)
- Implementa a funcionalidade de login para o sistema.
- Interface gráfica utilizando Java Swing.
- Conexão com banco de dados MySQL via JDBC.
- Validação de credenciais de login.

**Métodos:**
- `conectarBD()`: Estabelece a conexão com o banco de dados.
- `validarLogin()`: Verifica se o usuário e senha inseridos são válidos.

---

### 9. **FastFood.java** (Classe Principal)
- Classe principal para a execução do sistema.
- Implementa a lógica principal do sistema de fast food.
- Gerenciamento de pedidos e itens do menu.

**Métodos:**
- `adicionarPedido()`: Adiciona um novo pedido ao sistema.
- `calcularTotal()`: Calcula o valor total dos pedidos.
- `exibirMenu()`: Exibe os itens disponíveis no menu.

---

## Banco de Dados
O sistema utiliza um banco de dados MySQL com as tabelas:
- `cardapio (id, nome, preco, tipo, vegano, alcoolico, promocao)`
- `pedidos (id, produtos, total, forma_pagamento, nome_usuario, data)`

## Considerações Finais
Este projeto permite o gerenciamento de produtos, pedidos e autenticação de usuários em um fast food. Ele segue boas práticas de programação, como separação de classes e conexão segura com banco de dados via JDBC.

