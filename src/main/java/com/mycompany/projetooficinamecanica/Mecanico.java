package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public class Mecanico extends Funcionario{
    
    Mecanico(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
        this.cargo = "Mecanico";
    }
}
