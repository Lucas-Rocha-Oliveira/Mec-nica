package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */

/**
 * A classe Caixa representa um funcionário do tipo caixa.
 * Ela herda propriedades e métodos da classe Funcionario.
 */

public class Caixa extends Funcionario{
    
    Caixa(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
        this.cargo = "Caixa";
    }
}
