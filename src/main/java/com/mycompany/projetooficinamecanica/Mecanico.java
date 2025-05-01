/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public class Mecanico extends Funcionario{
    
    Mecanico(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
    }
    
    @Override
    public String getCargo(){
        return "Mecanico";
    }
}
