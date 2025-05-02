package com.mycompany.projetooficinamecanica;

/**
 *
 * @author Lucas
 */
public class ProjetoOficinaMecanica {

    public static void main(String[] args) {
        //Carrega os usuários armazenados no json para o array de usuarios
        SistemaLogin.carregarUsuarios();
        //Carrega os clientes armazenados no json para o array de clientes
        Cliente.carregarClientes();
        
        //testes
        Menu.menuLogin();
    }
}
