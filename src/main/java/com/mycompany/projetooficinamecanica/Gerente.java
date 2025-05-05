package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */

/**
 * A classe Gerente representa um funcionário do tipo gerente.
 * Ela herda propriedades e métodos da classe Funcionario.
 */

public class Gerente extends Funcionario{
    /**
     * Construtor da classe Gerente.
     *
     * @param nome  O nome do funcionário.
     * @param cpf   O CPF do funcionário.
     * @param login O login do funcionário.
     * @param senha A senha do funcionário.
     */

    Gerente(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
        this.cargo = "Gerente";
    }
   
}
