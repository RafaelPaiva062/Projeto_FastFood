package com.example.fastfood.entites;

import com.example.fastfood.controllers.Pedidos;
import com.example.fastfood.controllers.Produto;

import java.util.List;
public interface Server {
    List<String> historicoPedidos();
    List<Produto> lisTodos();
    List<Produto> buscarNome(String nome);
    List<Produto> listarProdutosT(String tipo);

    Produto buscarId(int id);

    void atualizar(Produto produto);

    void inserir(Produto produto);
    void  inserirHistorico(Pedidos pedido, String pagamento);

    void remover(int idproduto);
    List<String> pesquisarPedidos(String nomeUsuario, String data);

}
