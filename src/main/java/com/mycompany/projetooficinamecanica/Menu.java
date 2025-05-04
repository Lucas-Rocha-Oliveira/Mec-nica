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
        
        menuEpecializado();
    }
    
    //Metodo responsável pelo menu principal
    public static void menuPrincipal(){
        System.out.println("--- Sistema Oficina Mecânica ---");
        System.out.println("1. Login");
        System.out.println("0. Sair");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch(opcao){
            case 1 -> menuLogin();
            case 2 -> {
                System.out.println("Encerrando sistema ...");
                System.exit(0);
                break;
            }
        }
    }
    
    public static void menuEpecializado(){
        if(usuarioLogado!=null){
            if(usuarioLogado.getCargo().equals("Gerente")){
                menuGerente();
            }else if(usuarioLogado.getCargo().equals("Mecanico")){
                menuMecanico();
            }else if(usuarioLogado.getCargo().equals("Caixa")){
                menuCaixa();
            }
        }
    }
    
    //Metodo responsável pelo menu de gerente
    public static void menuGerente(){
        System.out.println("--- Menu Gerente ---");
        System.out.println("1. Clientes");
        System.out.println("2. Veiculos");
        System.out.println("3. Funcionarios");
        System.out.print("4. Ordens de Serviços");
        System.out.print("5. Logout");
        int opcao = scanner.nextInt();
        switch(opcao){
            case 1 -> menuClientes();
            case 2 -> menuVeiculo();
            case 3 -> menuUsuarios();
            case 4 -> menuOrdemServico();
            case 5 -> menuPrincipal();
        }
    }
    
    //Metodo responsável pelo menu de gerente
    public static void menuMecanico(){
        System.out.println("--- Menu Mecânico ---");
        System.out.println("1. Ver minhas ordens de serviço");
        System.out.println("2. Atualizar status de ordem");
        System.out.println("3. Veiculos");
        System.out.println("4. logout");
        int opcao = scanner.nextInt();
        switch(opcao){
            case 1 -> menuListarOrdensServicosPorCpf(usuarioLogado.getCpf());
            case 2 -> menuAtualizarOrdemServicoPorMecanico();
            case 3 -> menuVeiculo();
            case 4 -> menuPrincipal();
        }
    }
    
    
    public static void menuAtualizarOrdemServicoPorMecanico(){
        System.out.println("--- Atualizar Ordem de Serviço ----");
        while(true){ 
            System.out.print("ID: ");
            int id = scanner.nextInt();
            if(OrdemServico.verificarOrdemServico(id, usuarioLogado.getCpf())){
                System.out.print("Novo Status: ");
                String novo = scanner.nextLine();
                OrdemServico.atualizarStatusOrdemPorId(id, novo);
                System.out.println("Deseja atualizar outra ordem de serviço?[S/N]: ");
                String opcao = scanner.nextLine().toUpperCase();
                if(opcao.equals("S")){
                    menuAtualizarOrdemServicoPorMecanico();
                }else if(opcao.equals("N")){
                    menuEpecializado();
                }else{
                    System.out.println("Entrada inválida, indo para o menu principal.");
                    menuEpecializado();
                }
            }else{
                System.out.println("Tente novamente.");
            }
        }
        
    }
    
    //Metodo responsável pelo menu d usuário com cargo Caixa
    public static void menuCaixa(){
        System.out.println("--- Menu Caixa ---");
        System.out.println("1. Clientes");
        System.out.println("2. Ordem de serviço");
        System.out.println("3. Veiculos");
        System.out.println("4. logout");
        int opcao = scanner.nextInt();
        switch(opcao){
            case 1 -> menuClientes();
            case 2 -> menuOrdemServico();
            case 3 -> menuVeiculo();
            case 4 -> menuPrincipal();
        }
    }
    
    public static void menuListarOrdensServicosPorCpf(String cpf){
        System.out.println("--- Ordens de serviços ---");
        System.out.println("--- " + usuarioLogado.getNome() + " ---");
        OrdemServico.listarOrdensServicosPorCpf(cpf);
        menuMecanico();
    }
    
    public static void menuOrdemServico(){
        System.out.print("--- Ordens de serviço ---");
        System.out.print("1. Criar ordem de serviço");
        System.out.print("2. Consultar ordens de serviços");
        System.out.print("3. Atualizar status ordem");
        int opcao = scanner.nextInt();
        switch(opcao){
            case 1 -> menuCriarOrdemServico();
            case 2 -> {
                OrdemServico.listarOrdensServico();
                menuOrdemServico();
            }
            case 3 -> menuAtualizarOrdemServico();
        } 
    }
    
    public static void menuAtualizarOrdemServico(){
        System.out.println("=== ATUALIZAR STATUS DA ORDEM DE SERVIÇO ===");
        System.out.print("Digite o ID da ordem de serviço: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        System.out.print("Digite o novo status: ");
        String novoStatus = scanner.nextLine();
        OrdemServico.atualizarStatusOrdemPorId(id, novoStatus);
        System.out.print("Deseja atualizar outra ordem? (S/N): ");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuAtualizarOrdemServico();
        }else if(opcao.equals("N")){
            menuEpecializado();
        }else{
            System.out.print("Opção inválida, voltando para o menu  principal.");
            menuEpecializado();
        }
    }
    
    public static void menuCriarOrdemServico(){
       // Obter CPF do cliente e validar
        Cliente cliente = null;
        while (cliente == null) {
            System.out.print("Digite o CPF do cliente: ");
            String cpfCliente = scanner.nextLine();
            cliente = Cliente.buscarClientePorCpf(cpfCliente); // método que você pode criar na classe Cliente para buscar o cliente pelo CPF
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        }

        // Obter CPF do mecânico e validar
        Mecanico mecanico = null;
        while (mecanico == null) {
            System.out.print("Digite o CPF do mecânico: ");
            String cpfMecanico = scanner.nextLine();
            mecanico = Mecanico.buscarMecanicoPorCPF(cpfMecanico); // método que você pode criar na classe Mecanico para buscar o mecânico pelo CPF
            if (mecanico == null) {
                System.out.println("Mecânico não encontrado ou não é mecânico. Tente novamente.");
            }
        }

        // Obter descrição do problema
        System.out.print("Digite a descrição do problema: ");
        String descricaoProblema = scanner.nextLine();

        // Obter solução (pode ser uma entrada inicial ou deixar em branco)
        System.out.print("Digite a solução (opcional): ");
        String solucao = scanner.nextLine();

        // Obter status (ex: "Em andamento", "Concluída")
        System.out.print("Digite o status da ordem (Ex: Em andamento, Concluída): ");
        String status = scanner.nextLine();

        // Obter data
        System.out.print("Digite a data do serviço (dd/MM/yyyy): ");
        String data = scanner.nextLine();

        // Escolher o veículo para a ordem
        Veiculo veiculo = null;
        while (veiculo == null) {
            System.out.println("Selecione um veículo:");
            for (int i = 0; i < Veiculo.listaVeiculos.size(); i++) {
                Veiculo v = Veiculo.listaVeiculos.get(i);
                System.out.println((i + 1) + ". " + v.getPlaca() + " - " + v.getModelo());
            }
            System.out.print("Escolha o número do veículo: ");
            int veiculoEscolhido = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            if (veiculoEscolhido > 0 && veiculoEscolhido <= Veiculo.listaVeiculos.size()) {
                veiculo = Veiculo.listaVeiculos.get(veiculoEscolhido - 1);
            } else {
                System.out.println("Veículo inválido, tente novamente.");
            }
        }

        // Criando a ordem de serviço
        OrdemServico ordemServico = new OrdemServico(cliente, mecanico, descricaoProblema, solucao, status, data, veiculo);

        // Chama o método para adicionar a ordem de serviço na lista
        OrdemServico.criarOrdemDeServico(ordemServico);

        // Exibe os detalhes da ordem criada
        System.out.println("Ordem de serviço criada com sucesso!");
        System.out.println(ordemServico); // Exibe os detalhes da ordem criada
        System.out.print("Deseja criar outra ordem? (S/N): ");
        String opcao = scanner.nextLine().toUpperCase();
        if(opcao.equals("S")){
            menuCriarOrdemServico();
        }else if(opcao.equals("N")){
            menuEpecializado();
        }else{
            System.out.print("Opção inválida, voltando para o menu  principal.");
            menuEpecializado();
        }
    }
    
    public static void menuVeiculo(){
      System.out.println("--- Veiculos ---");
      System.out.println("1. Cadastrar veiculo");
      System.out.println("2. Remover veiculo");
      System.out.println("3. Listar");
      System.out.println("4. Menu Principal");
      int opcao = scanner.nextInt();
        switch(opcao){
            case 1 -> menuCadastrarVeiculo();
            case 2 -> menuRemoverVeiculo();
            case 3 -> {
                Veiculo.listarVeiculos();
                menuVeiculo();
            }
            case 4 -> menuEpecializado();
        }
    }
    
    public static void menuRemoverVeiculo(){
        System.out.println("--- Remover Veiculo ---");
        while (true){
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            if(Veiculo.buscarVeiculoPorPlaca(placa)){
                Veiculo.removerVeiculoPorPlaca(placa);
                System.out.print("Deseja remover outro Veículo?[S/N]: ");
                String opcao = scanner.nextLine().toUpperCase();
                if(opcao.equals("S")){
                    menuRemoverVeiculo();
                }else if(opcao.equals("N")){
                    menuEpecializado();
                }else{ 
                    System.out.println("Entrada inválida, voltando para o menu principal.");
                    menuEpecializado();
                }
            }else{
                System.out.println("Tente novamente.");
            }
        }
        
    };
    
    public static void menuCadastrarVeiculo(){
       System.out.println("--- Castrar Veículo ---");
       System.out.print("Placa: ");
       String placa = scanner.nextLine();
       System.out.print("Modelo: ");
       String modelo = scanner.nextLine();
       System.out.print("Marca: ");
       String marca = scanner.nextLine();
       System.out.print("Cor: ");
       String cor = scanner.nextLine();
       System.out.print("Ano: ");
       int ano = scanner.nextInt();
       scanner.nextLine();
       System.out.print("CPF do proprietário: ");
       String cpf = scanner.nextLine();
       
       boolean sucesso = Veiculo.cadastrarVeiculo(placa, modelo, marca, cor, ano, cpf);
       
       if(sucesso){
           System.out.println("Deseja Cadastrar outro veículo?[S/N]");
           String opcao = scanner.nextLine().toUpperCase();
           if(opcao.equals("S")){
               Veiculo.salvarVeiculos();
               menuCadastrarVeiculo();
           }else if(opcao.equals("N")){
               Veiculo.salvarVeiculos();
               menuEpecializado();
           }else{
               System.out.println("Entrada inválida, voltando para o menu pricipal.");
               Veiculo.salvarVeiculos();
               menuEpecializado();
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
            case 5 -> menuEpecializado();
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
            menuEpecializado();
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
            menuEpecializado();
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
                menuEpecializado();
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
            menuEpecializado();
        }else{
            System.out.println("Opção inválida, indo para o menu inicial.");
            menuEpecializado();
        }
    }
    
    //Metodo resposável pelu menu de verificação de usuário.
    public static String menuVerificarUsuarioPorCpf(){
        while (true){
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();
            if(cpf.equals("0")){
                menuEpecializado();
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
            
            case 5 -> menuEpecializado();
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
                menuContinuidadeEdicaoUsuario(cpf);
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
                menuEpecializado();
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
            menuEpecializado();
        }else{
            System.out.println("Opção inválida, voltando para o menu principal.");
            menuEpecializado();
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
        System.out.print("Cargo: \nG. Gerente \nM. Mecânico\nC. Caixa");
        String cargo = scanner.nextLine().toUpperCase();
        
        switch (cargo){
            case "G" -> {
                Gerente gerenteTemporario = new Gerente(nome, cpf, login, senha);
                SistemaLogin.adicionarUsuario(gerenteTemporario);
            }
            case "M" -> {
                Mecanico mecanicoTemporario = new Mecanico(nome, cpf, login, senha);
                SistemaLogin.adicionarUsuario(mecanicoTemporario);
            }
            case "C" -> {
                Caixa caixaTemporario = new Caixa(nome, cpf, login, senha);
                SistemaLogin.adicionarUsuario(caixaTemporario);
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
