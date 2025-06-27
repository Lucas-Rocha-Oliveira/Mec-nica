/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;
import java.io.IOException;
/**
 *
 * @author joaop
 */
public class Venda {
    // Crie um novo arquivo chamado PontoDeVenda.java
    /**
     * Realiza a venda de um produto específico.
     * Este método é o coração da lógica de venda.
     * * @param codigoProduto O código do produto a ser vendido.
     * @param quantidade A quantidade a ser vendida.
     * @return true se a venda foi bem-sucedida, false caso contrário.
     * @throws IOException Se houver um erro ao salvar o estado do estoque.
     */
    public static boolean realizarVenda(int codigoProduto, int quantidade) throws IOException {
        
        Produto produto = Estoque.buscarProdutoPorCodigo(codigoProduto);

        
        if (produto == null) {
            System.out.println("ERRO: Produto com código " + codigoProduto + " não encontrado.");
            return false;
        }

        
        if (produto.getQuantidadeEmEstoque() < quantidade) {
            System.out.println("ERRO: Estoque insuficiente para o produto '" + produto.getNome() + "'.");
            System.out.println("Disponível: " + produto.getQuantidadeEmEstoque() + ", Pedido: " + quantidade);
            return false;
        }

        
        System.out.println("Venda autorizada. Processando...");

        
        int novoEstoque = produto.getQuantidadeEmEstoque() - quantidade;
        produto.setQuantidadeEmEstoque(novoEstoque);

        
        for (int i = 0; i < quantidade; i++) {
            Relatorio.adicionarVendaProduto(produto);
        }

        
        JsonUtil.salvar("data/produtos.json", Estoque.getListaDeProdutos());

        System.out.printf("Venda de %d unidade(s) do produto '%s' realizada com sucesso!\n", quantidade, produto.getNome());
        return true;
    }
}

