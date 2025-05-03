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
        System.out.println("3. Remover");
        System.out.println("4. Editar");
        System.out.println("5. Menu Principal");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();//Captura o resto da ultima leitura
        
        switch (opcao){
            case 1 -> {
                Cliente.listarClientes();
                menuClientes();
            }
            case 2 -> menuCadastrarCliente();
            case 3 -> menuRemoverCliente();
            case 4 -> menuEditarCliente();
            case 5 -> menuPrincipal();
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
            menuClientes();
        } else{
            System.out.println("Opção inválida, voltando para o menu principal");
            Cliente.salvarClientes();
            menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de remoção de cliente.
    public static void menuRemoverCliente(){
        System.out.println("--- Remover Cliente ---");
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente.removerClientePorCpf(cpf);
        System.out.print("Deseja remover outro cliente?[S/N]: ");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuRemoverCliente();
        }else if(opcao.equals("N")){
            menuClientes();
        }else{
            System.out.println("Opção inávila. Voltando para o menu principal.");
            menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de edição de dados do cliente.
    public static void menuEditarCliente(){
        System.out.println("--- Editar Cliente ---");
        String cpf = menuVerificarUsuarioPorCpf();
        menuOpcoesEdicaoCliente(cpf);
    }
    
    //Metodo responsável pelo menu opções de Edição de cliente.
    public static void menuOpcoesEdicaoCliente(String cpf){
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Endereço");
        System.out.println("4. Telefone");
        System.out.println("5. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();//limpando o resto da ultima leitura.
        switch(opcao){
            case 1 -> {
                System.out.print("Novo Nome: ");
                String nome = scanner.nextLine();
                Cliente.editarNomeCliente(cpf, nome);
                menuContinuidadeEdicaoCliente(cpf);
            }
            
            case 2 -> {
                System.out.print("Novo cpf: ");
                String novocpf = scanner.nextLine();
                Cliente.editarCpfCliente(cpf, novocpf);
                cpf = novocpf; // Atualiza o valor de referência
                menuContinuidadeEdicaoCliente(cpf);
            }
            
            case 3 -> {
                System.out.print("Novo Endereço: ");
                String endereco = scanner.nextLine();
                Cliente.editarEnderecoCliente(cpf, endereco);
                menuContinuidadeEdicaoCliente(cpf);
            }
            
            case 4 -> {
                System.out.print("Novo Telefone: ");
                String telefone = scanner.nextLine();
                Cliente.editarTelefoneCliente(cpf, telefone);
                menuContinuidadeEdicaoCliente(cpf);
            }
            
            case 5 -> {
                menuPrincipal();
            }
        }
    }
    
    //Metodo responsável por confirmar a continuação de edição do mesmo cliente
    public static void menuContinuidadeEdicaoCliente(String cpf){
        System.out.println("Continuar com a edição do cliente atual?\n"
                + "S. Sim\nN. Editar outro usuário\n0. Menu principal");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuOpcoesEdicaoCliente(cpf);
        }else if(opcao.equals("N")){
            menuEditarCliente();
        }else if(opcao.equals("0")){
            menuPrincipal();
        }else{
            System.out.println("Opção inválida, indo para o menu inicial.");
            menuPrincipal();
        }
    }
    
    //Metodo resposável pelu menu de verificação de usuário.
    public static String menuVerificarUsuarioPorCpf(){
        while (true){
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();
            if(cpf.equals("0")){
                menuPrincipal();
            }else{
                if(Cliente.verificarClientePorCpf(cpf)){
                    return cpf;
            } else{
                System.out.print("Tente de novo ou digite 0 para voltar para o menu principal.");
              }
            }
        } 
    }
    
    //Metodo responsável pelo menu de usuários.
    public static void menuUsuarios(){
        System.out.println("--- Usuários ---");
        System.out.println("1. Listar");
        System.out.println("2. Cadastrar");
        System.out.println("3. Remover");
        System.out.println("4. Editar");
        System.out.println("5. Menu Principal");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();//Recebe o resto da ultima leitura
        
        switch (opcao){
            case 1 -> {
            SistemaLogin.listarUsuarios();
            menuUsuarios();
            }
            case 2 -> menuCadastrarUsuario();
            
            case 3 -> menuRemoverUsuario();
            
            case 4 -> menuEdicaoUsuario();
            
            case 5 -> menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de edição de Usuário
    public static void menuEdicaoUsuario(){
        System.out.println("--- Edição Usuário ---");
        String cpf = menuVerificarUsuario();
        menuOpcoesEdicaoUsuario(cpf);
    }
    
    //Metodo responsável pelo menu de opções de edição do usuário.
    public static void menuOpcoesEdicaoUsuario(String cpf){
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Login");
        System.out.println("4. Senha");
        System.out.println("5. Cargo");
        System.out.println("6. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();//limpando o resto da ultima leitura.
        switch(opcao){
            case 1 -> {
                System.out.print("Novo Nome: ");
                String nome = scanner.nextLine();
                SistemaLogin.alterarNomeUsuarioPorCpf(cpf, nome);
                menuContinuidadeEdicaoCliente(cpf);
            }
            
            case 2 -> {
                System.out.print("Novo cpf: ");
                String novocpf = scanner.nextLine();
                SistemaLogin.alterarCpfUsuarioPorCpf(cpf, novocpf);
                cpf = novocpf; // Atualiza o valor de referência
                menuContinuidadeEdicaoUsuario(cpf);
            }
            
            case 3 -> {
                System.out.print("Novo Login: ");
                String login = scanner.nextLine();
                SistemaLogin.alterarLoginUsuarioPorCpf(cpf, login);
                menuContinuidadeEdicaoUsuario(cpf);
            }
            
            case 4 -> {
                System.out.print("Novo Senha: ");
                String senha = scanner.nextLine();
                SistemaLogin.alterarSenhaUsuarioPorCpf(cpf, senha);
                menuContinuidadeEdicaoUsuario(cpf);
            }
            
            case 5 -> {
                System.out.print("Novo Cargo: ");
                String cargo = scanner.nextLine();
                SistemaLogin.alterarCargoUsuarioPorCpf(cpf, cargo);
                menuContinuidadeEdicaoUsuario(cpf);
            }
            
            case 6 -> {
                menuPrincipal();
            }
        }
    }
    
    //Metodo responsável pelo menu de continuidade de edição de usuários.
    public static void menuContinuidadeEdicaoUsuario(String cpf){
        System.out.print("Deseja continuar a editar o usuário atual?\n"
                + "S. Sim\nN. Editar outro usuário\n0. Menu Principal");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuOpcoesEdicaoUsuario(cpf);
        }else if(opcao.equals("N")){
            menuEdicaoUsuario();
        }else if(opcao.equals("0")){
            menuPrincipal();
        }else{
            System.out.println("Opção inválida, voltando para o menu principal.");
            menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de remoção de usuário.
    public static void menuRemoverUsuario(){
        System.out.println("--- Remove Usuário ---");
        String cpf = menuVerificarUsuario();
        SistemaLogin.removerUsuarioPorCpf(cpf);
    }
    
    //Metodo responsável pela menu de verificação de existência do usuário
    public static String menuVerificarUsuario(){
        System.out.print("Cpf: ");
        String cpf = scanner.nextLine();
        if(SistemaLogin.verificarUsuarioPeloCpf(cpf)){
            return cpf;
        }else{
            System.out.println("Tente novamente.");
            return menuVerificarUsuario();
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
