package com.example.fastfood.controllers;

public abstract class Produto {
    protected int id_produto;
    protected String nome_produto;
    protected double preco_produto;
    protected String promocao; 

    public Produto(int id_produto, String nome_produto, double preco_produto) {
        this.id_produto = id_produto;
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public double getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(double preco_produto) {
        this.preco_produto = preco_produto;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public abstract String toString();
}
