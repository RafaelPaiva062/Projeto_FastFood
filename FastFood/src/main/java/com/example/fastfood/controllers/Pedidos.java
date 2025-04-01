package com.example.fastfood.controllers;
import  java.util.List;
import  java.util.ArrayList;
public class Pedidos {
    private  final List<Produto> produtos = new ArrayList<>();
    private String nomeUsuario;
    public Pedidos(String nomeUsuario, List<Produto> produtos){
        this.nomeUsuario = nomeUsuario;
        this.produtos.addAll(produtos);
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public Pedidos(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }
    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }
    public List<Produto> getProdutos(){
        return produtos;
    }
    public String listarProdutos(){
        StringBuilder sb = new StringBuilder("Produtos: \n");
        for (Produto produto : produtos) {
            sb.append(produto.toString()).append("\n");
        }
        sb.append(String.format("Total:",calcularTotal()));
        return sb.toString();
    }
    public double calcularTotal(){
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco_produto();
        }
        return total;
    }

}
