package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public class Caixa extends Funcionario{
    Caixa(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
        this.cargo = "Caixa";
    }
}
