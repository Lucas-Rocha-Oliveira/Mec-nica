/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;


/**
 *
 * @author Lucas
 */
public class Produto {
    
    public enum CategoriaDoProduto{
        PEÇA,
        CONSUMIVEL;
    }
    
    private int codigo;
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;
    private CategoriaDoProduto categoria;
    private static final double margemLucro = 0.15;
    
    public Produto(){}
    
    public Produto(int codigo, String nome, double preco, int quantidadeEmEstoque, CategoriaDoProduto categoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.categoria = categoria;
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
    
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public CategoriaDoProduto getCategoria() {
        return categoria;
    }
    
    public void setQuantidadeEmEstoque(int quantidade){
        this.quantidadeEmEstoque = quantidade;
    }
    
    
    
    @Override
    public String toString(){
        return categoria + ": " + nome + "- R$" + preco + ", Em Estoque: " + quantidadeEmEstoque;
    }
}
