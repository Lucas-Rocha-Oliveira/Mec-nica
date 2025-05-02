package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */

public class Funcionario extends Pessoa{
    String login;
    String senha;
    String cargo;
    
    Funcionario(){
        super("", "");
    }
    
    Funcionario(String nome, String cpf, String login, String senha){
        super(nome, cpf);
        this.login = login;
        this.senha = senha;
    }
    
    public String getLogin(){
        return this.login;
    }
    
    public String getSenha(){
        return this.senha;
    }
    
    
    public String getCargo(){
        return this.cargo;
    }
    
    @Override
    public String toString(){
        return super.toString() + "\nCargo: " + getCargo();
    }

}
