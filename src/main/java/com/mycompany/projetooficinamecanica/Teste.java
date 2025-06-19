/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */

public class Teste {
    private int id;
    private String nome;
    
    private static List<Teste> listaTeste = new ArrayList<>();

    public Teste(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public static void listarTeste(){
        for(Teste t : listaTeste){
            System.out.println(t);
        }
    }

    public static List<Teste> getListaTeste() {
        return listaTeste;
    }

    public static void setListaTeste(List<Teste> listaTeste) {
        Teste.listaTeste = listaTeste;
    }
    
    
    public static void adicionar(Teste teste){
        listaTeste.add(teste);
    }
    
    @Override 
    public String toString(){
        return "ID: " + getId() + "\nNome: " + getNome();
    }
}
