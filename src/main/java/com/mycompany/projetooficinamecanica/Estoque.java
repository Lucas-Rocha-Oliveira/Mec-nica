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
    
    public static void adicionarProduto(Produto produto){
        listaDeProdutos.add(produto);
    }
    
    public static void removerProduto(int codigo) throws IOException{
        boolean removido = listaDeProdutos.removeIf(c -> c.getCodigo() == codigo);
        
        if(removido){
            System.out.println("Produto removido com sucesso!");
            JsonUtil.salvar("data/produtos.json", listaDeProdutos);
        }else{
            System.out.println("ERRO - Produto não encontrado");
        }
    }
    
    public static Produto buscarProdutoPorCodigo(int codigo){
        for(Produto c: listaDeProdutos){
            if(c.getCodigo() == codigo){
                return c;
            }
        }
        return null;
    }
    
    public static void listarProdutos(){
        for(Produto c: listaDeProdutos){
            System.out.println(c);
        }
    }
}
