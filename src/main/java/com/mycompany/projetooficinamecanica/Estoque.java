/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class Estoque {
    public static List<Produto> listaDeProdutos = new ArrayList<>();

    public static List<Produto> getListaDeProdutos() {
        return listaDeProdutos;
    }

    public static void setListaDeProdutos(List<Produto> listaDeProdutos) {
        Estoque.listaDeProdutos = listaDeProdutos;
    }
    
    public static void adicionarProduto(Produto produto) throws IOException{
        listaDeProdutos.add(produto);
        JsonUtil.salvar("data/produtos.json", listaDeProdutos);
    }
    
    public static void removerProduto(int codigo) throws IOException{
        boolean removido = listaDeProdutos.removeIf(c -> c.getCodigo() == codigo);
        
        if(removido){
            System.out.println("Produto removido com sucesso!");
            JsonUtil.salvar("data/produtos.json", listaDeProdutos);
        }else{
            System.out.println("ERRO - Produto não encontrado");
        }

        JsonUtil.salvar("data/produtos.json", Estoque.listaDeProdutos);
    }
    
    public static Produto buscarProdutoPorCodigo(int codigo){
        for(Produto c: listaDeProdutos){
            if(c.getCodigo() == codigo){
                return c;
            }
        }
        return null;
    }

    public static void subtrairQuantidadeEstoque(Produto produto, int quantidade) throws IOException{
        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);
        JsonUtil.salvar("data/produtos.json", Estoque.listaDeProdutos);
    }
    
    public static void listarProdutos(){
        for(Produto c: listaDeProdutos){
            System.out.println(c);
        }
    }
    
    public static boolean verificarQuantidadeEstoque(Produto produto){
        if(produto.getQuantidadeEmEstoque()==0){
            return false;
        }else{
            return true;
        }
    }
}
