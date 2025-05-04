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
    
    public static Mecanico buscarMecanicoPorCPF(String cpf) {
    for (Funcionario u : SistemaLogin.getListaUsuarios()) {
        if (u instanceof Mecanico && u.getCpf().equals(cpf)) {
            return (Mecanico) u; // Retorna o mecânico após fazer o cast
        }
    }
    return null;
    }
    
    
}
