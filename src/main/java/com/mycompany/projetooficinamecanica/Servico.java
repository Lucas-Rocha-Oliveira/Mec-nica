/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public enum Servico {
    TROCA_DE_PNEU("Troca de Pneu", 100),
    ALINHAMENTO("Alinhamento", 200),
    BALANCEMANTO("Balanceamento", 150),
    REVISAO_DOS_FREIOS("Revisao dos freios", 160),
    REVISAO_DO_SISTEMA_DE_ARREFECIMENTO("Revisão do sistema de arrefecimento", 130),
    TROCA_DE_PASTILHAS("Troca de pastilhas", 125),
    TROCA_CORREIA_DENTADA("Troca da correia dentada", 350);
    
    private final String descricao;
    private final double preco;   
        
    Servico(String descricao, double preco){
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
    
    @Override
    public String toString(){
        return descricao + " - R$" + preco;
    }
}
