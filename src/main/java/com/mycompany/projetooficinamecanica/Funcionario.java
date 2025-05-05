package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */

/**
 * Representa um funcionário, que é um tipo de pessoa no sistema.
 */

public class Funcionario extends Pessoa{
    String login;
    String senha;
    String cargo;
    
   /**
     * Construtor padrão da classe Funcionario.
     * Cria um funcionário com valores vazios.
     */


    Funcionario(){
        super("", "");
    }
    
    /**
     * Construtor da classe Funcionario.
     * 
     * @param nome  O nome do funcionário.
     * @param cpf   O CPF do funcionário.
     * @param login O login do funcionário.
     * @param senha A senha do funcionário.
     */

    
    Funcionario(String nome, String cpf, String login, String senha){
        super(nome, cpf);
        this.login = login;
        this.senha = senha;
    }
    /**
     * Obtém o login do funcionário.
     * 
     * @return O login do funcionário.
     */

    public String getLogin(){
        return this.login;
    }
    /**
     * Define um novo login para o funcionário.
     * 
     * @param login O novo login do funcionário.
     */

    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Define uma nova senha para o funcionário.
     * 
     * @param senha A nova senha do funcionário.
     */

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * Define o cargo do funcionário.
     * 
     * @param cargo O novo cargo do funcionário.
     */


    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
     /**
     * Obtém a senha do funcionário.
     * 
     * @return A senha do funcionário.
     */

    public String getSenha(){
        return this.senha;
    }
    /**
     * Obtém o cargo do funcionário.
     * 
     * @return O cargo do funcionário.
     */

    
    
    public String getCargo(){
        return this.cargo;
    }
    /**
     * Retorna a representação textual do funcionário,
     * incluindo as informações herdadas da classe Pessoa.
     * 
     * @return Uma string com os detalhes do funcionário.
     */

    @Override
    public String toString(){
        return super.toString() + "\nCargo: " + getCargo();
    }
   

}
