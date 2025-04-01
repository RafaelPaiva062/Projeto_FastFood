package com.example.fastfood.controllers;

public class Lanche  extends Produto{
   private  final boolean vegano;
   public Lanche(int id_produto, String nome, double preco, boolean vegano, String promocao){
       super(id_produto, nome, preco);
       this.vegano = vegano;
   }
   public boolean isVegano(){
       return vegano;
   }
   @Override
    public String toString(){
       return String.format("ID: \n"+ id_produto+"\n Lanche:"+ nome_produto+"\nPreço:"+preco_produto+"\nVegano:"+vegano);
   }
}
