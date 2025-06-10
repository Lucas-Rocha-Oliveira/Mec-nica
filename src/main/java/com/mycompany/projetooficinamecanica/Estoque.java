/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class Estoque {
    public static List<Produto> listaDeProdutos = new ArrayList<>();
    
    /**
     * Carrega a lista de produtos do arquivo JSON.
     * Utiliza o GerenciadorJson para garantir que a instância correta do Gson seja usada.
     */
    public static void carregarProdutos() {
        Gson gson = GerenciadorJson.getGson(); 
        
        try(FileReader reader = new FileReader("data/produtos.json")){
            listaDeProdutos = gson.fromJson(reader, new TypeToken<List<Produto>>() {}.getType());
            if (listaDeProdutos == null) { 
                listaDeProdutos = new ArrayList<>();
            }
        } catch(IOException e){
            System.out.println("Arquivo de produtos não encontrado. Criando nova lista.");
            listaDeProdutos = new ArrayList<>();
        }
    }
    
    /**
     * Salva a lista de produtos no arquivo JSON.
     * Utiliza o GerenciadorJson para garantir uma serialização consistente.
     */
    public static void salvarProdutos(){
         Gson gson = GerenciadorJson.getGson();
         
         try(FileWriter writer = new FileWriter("data/produtos.json")){
             gson.toJson(listaDeProdutos, writer);
         }catch(IOException e){
             System.out.println("Erro ao escrever o arquivo produtos.json: " + e.getMessage());
         }
    }
    
    public static void adicionarProduto(Produto produto){
        listaDeProdutos.add(produto);
    }
    
    public static void removerProduto(int codigo){
        boolean removido = listaDeProdutos.removeIf(c -> c.getCodigo() == codigo);
        
        if(removido){
            System.out.println("Produto removido com sucesso!");
            salvarProdutos();
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
