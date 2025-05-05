package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */


/**
 * Representa um mecânico, que é um tipo de funcionário.
 */

public class Mecanico extends Funcionario{
    /**
     * Construtor da classe Mecanico.
     * 
     * @param nome  O nome do mecânico.
     * @param cpf   O CPF do mecânico.
     * @param login O login do mecânico.
     * @param senha A senha do mecânico.
     */

    
    Mecanico(String nome, String cpf, String login, String senha){
        super(nome, cpf, login, senha);
        this.cargo = "Mecanico";
    }
    
    public static Mecanico buscarMecanicoPorCPF(String cpf) {
    for (Funcionario u : SistemaLogin.getListaUsuarios()) {
        if (u instanceof Mecanico && u.getCpf().equals(cpf)) {
            return (Mecanico) u; // Retorna o mecânico após fazer o cast
        }
    }
    return null;
    }
    
    
}
