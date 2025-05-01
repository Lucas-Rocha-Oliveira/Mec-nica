package com.mycompany.projetooficinamecanica;

import java.util.Scanner; //Permite ler dados digitados pelo usuário.

/**
 *
 * @author Lucas
 */
public class Menu {
    //Criação do objeto do tipo Scanner.
    private static final Scanner scanner = new Scanner(System.in);    
    
    //Obejto responsável por armazenar o usuario logado no sistema
    public static Funcionario usuarioLogado;
    
    //Metodo responsável pelo processo de login do usuário no sistema.
    public static void menuLogin(){
        while (true){
            System.out.println("--- MECÂNICA ---");
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            
            usuarioLogado = SistemaLogin.verificarLogin(login, senha);
            
            if (usuarioLogado != null){
                break;
            }
        }
        
        menuPrincipal();
    }
    
    
    //Metodo responsável pelo menu principal
    public static void menuPrincipal(){
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Clientes");
        //Verifica se o usuário que esta logado é um Gerente, se mosta a opção de manipular dados de usuários
        if(usuarioLogado.getCargo().equals("Gerente")){
            System.out.println("2. Usuários");
        }
        
        int opcao = scanner.nextInt(); //recebe a entrada do usuario.
        scanner.nextLine(); //Limpa o "ENTER" da leitura passada.
        
        switch (opcao){
            case 1 -> menuClientes();
            case 2 -> {
                if(usuarioLogado.getCargo().equals("Gerente")){
                    menuUsuarios();
                }else{
                    System.out.print("Permição negada!");
                    menuPrincipal();
                }
            }
        }
    }
    
    //Metodo responsável pelo menu de clientes
    public static void menuClientes(){
        System.out.println("--- Clientes ---");
        System.out.println("1. Listar");
        System.out.println("2. Cadastrar");
        System.out.print("3. Menu Principal");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();//Captura o resto da ultima leitura
        
        switch (opcao){
            case 1 -> {
                Cliente.listarClientes();
                menuClientes();
            }
            case 2 -> menuCadastrarCliente();
            case 3 -> menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de cadastro de clientes
    public static void menuCadastrarCliente(){
        System.out.println("--- Cadastro de cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        Cliente clientePrecadastrado = new Cliente(nome, cpf, endereco, telefone);
        Cliente.adicionarCliente(clientePrecadastrado);
        
        System.out.print("Adicionar outro cliente?[S/N]: ");
        //pega a entrada do usuário e tranforma em maiúsculo.
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuCadastrarCliente();
        }else if (opcao.equals("N")){
            Cliente.salvarClientes();
            menuPrincipal();
        } else{
            System.out.println("Opção inválida, voltando para o menu principal");
            Cliente.salvarClientes();
            menuClientes();
        }
    }
    
    //Metodo responsável pelo menu de usuários.
    public static void menuUsuarios(){
        System.out.println("--- Usuários ---");
        System.out.println("1. Listar");
        System.out.println("2. Cadastrar");
        System.out.print("3. Menu Principal");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();//Recebe o resto da ultima leitura
        
        switch (opcao){
            case 1 -> {
            SistemaLogin.listarUsuarios();
            menuUsuarios();
            }
            case 2 -> menuCadastrarUsuario();
            
            case 3 -> menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de cadastro de usuários.
    public static void menuCadastrarUsuario(){
        System.out.println("--- Cadastro de Usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        
        switch (cargo){
            case "Gerente" -> {
                Gerente gerenteTemporario = new Gerente(nome, cpf, login, senha);
                SistemaLogin.adicionarUsuario(gerenteTemporario);
            }
            case "Mecanico" -> {
                Mecanico mecanicoTemporario = new Mecanico(nome, cpf, login, senha);
                SistemaLogin.adicionarUsuario(mecanicoTemporario);
            }
        }
        
        System.out.print("Quer cadastrar outro usuário?[S/N]: ");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            SistemaLogin.salvarUsuarios();
            menuCadastrarUsuario();
        }else if (opcao.equals("N")){
            SistemaLogin.salvarUsuarios();
            menuUsuarios();
        }
        
        
    }    
}
