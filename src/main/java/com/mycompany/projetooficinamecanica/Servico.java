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
    TROCA_DE_PNEU("Troca de Pneu", 100, 45),
    ALINHAMENTO("Alinhamento", 200, 60),
    BALANCEMANTO("Balanceamento", 150, 60),
    REVISAO_DOS_FREIOS("Revisao dos freios", 160, 50),
    REVISAO_DO_SISTEMA_DE_ARREFECIMENTO("Revisão do sistema de arrefecimento", 130, 180),
    TROCA_DE_PASTILHAS("Troca de pastilhas", 125, 90),
    TROCA_CORREIA_DENTADA("Troca da correia dentada", 350, 60),
    DIAGNOSTICO_INICAL("Diagnostico Inicial", 80, 90);
    
    private final String descricao;
    private final double preco;  
    private final int duracaoEstimadaEmMinutos;
        
    Servico(String descricao, double preco, int duracaoEstimadaEmMinutos){
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEstimadaEmMinutos = duracaoEstimadaEmMinutos;
    }

    public int getDuracaoEstimadaEmMinutos() {
        return duracaoEstimadaEmMinutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
    
    public static void listarServicos(){
        System.out.println("--- Lista de Serviços ---");
        for(Servico s: Servico.values()){
            int indice = s.ordinal() + 1;
            System.out.println(indice + " - " + s);
        }
        System.out.println("-----------------------");
    }
    
    
    @Override
    public String toString(){
        return descricao + " - R$" + preco;
    }
}
