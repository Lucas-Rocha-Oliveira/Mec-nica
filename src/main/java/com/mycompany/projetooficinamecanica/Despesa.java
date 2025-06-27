package com.mycompany.projetooficinamecanica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Despesa {

   private String descricao;
   private double custo;
   private LocalDate dataAtual = LocalDate.now();

   private static List<Despesa> listaDespesas = new ArrayList<>();

    public static List<Despesa> getListaDespesas() {
        return listaDespesas;
    }

    public static void setListaDespesas(List<Despesa> listaDespesas) {
        Despesa.listaDespesas = listaDespesas;
    }

   public static void adicionarDespesa(Despesa despesa){
       listaDespesas.add(despesa);
   }

    public Despesa(String descricao, double custo, LocalDate dataAtual) {
        this.descricao = descricao;
        this.custo = custo;
        this.dataAtual = dataAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public LocalDate getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(LocalDate dataAtual) {
        this.dataAtual = dataAtual;
    }



    public static void exibir(){

        for (Despesa d: listaDespesas){
            System.out.println(d);
        }

    }

    @Override
    public String toString() {
        return descricao + " R$ " + custo + "Data: " + dataAtual;


    }







}