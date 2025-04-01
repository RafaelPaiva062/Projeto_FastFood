package com.example.fastfood.controllers;

public class Bebida extends Produto {
    private  final boolean alcolico;
    public Bebida(int id_produto, String nome, double preco, boolean alcolico, String promocao){
        super(id_produto, nome, preco);
        this.alcolico = alcolico;
    }
    public boolean isAlcolico(){
        return alcolico;
    }
    @Override
    public String toString(){
        return String.format("ID: \n"+ id_produto+"\n Lanche:"+ nome_produto+"\nPreço:"+preco_produto+"\n Alcoolico:"+alcolico);
    }
}
