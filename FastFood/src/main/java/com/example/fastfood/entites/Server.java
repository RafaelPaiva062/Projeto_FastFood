package com.example.fastfood.entites;

import com.example.fastfood.controllers.Pedidos;
import com.example.fastfood.controllers.Produto;

import java.util.List;
public interface Server {
    List<String> historicoPedidos();//listarHistoricoPedidos
    List<Produto> lisTodos();//listarTodos
    List<Produto> buscarNome(String nome);//buscarPorNome
    List<Produto> listarProdutosT(String tipo);//listarPorTipo

    Produto buscarId(int id);//buscarPorId

    void atualizar(Produto produto);//atualizar

    void inserir(Produto produto);//adicionar
    void  inserirHistorico(Pedidos pedido, String pagamento);//adicionarHistoricoPedido

    void remover(int idproduto);//remover
    List<String> pesquisarPedidos(String nomeUsuario, String data);

}
