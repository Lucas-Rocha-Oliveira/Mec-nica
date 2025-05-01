package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public class Gerente extends Funcionario{
    
    Gerente(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
    }
    
    @Override
    public String getCargo(){
        return "Gerente";
    }
}
