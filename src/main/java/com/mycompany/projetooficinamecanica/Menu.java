package com.mycompany.projetooficinamecanica;

import static com.mycompany.projetooficinamecanica.Estoque.listaDeProdutos;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner; //Permite ler dados digitados pelo usuário.

/**
 *
 * @author Lucas
 */
public class Menu {
    /**
     * Criação do objeto do tipo Scanner.
     * 
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Obejto responsável por armazenar o usuario logado no sistema
     * 
     */
    public static Funcionario usuarioLogado;

    /**
     * Metodo responsável pelo processo de login do usuário no sistema.
     * 
     */
    public static void menuLogin() throws IOException {
        while (true) {
            System.out.println("--- MECÂNICA ---");
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuarioLogado = SistemaLogin.verificarLogin(login, senha);

            if (usuarioLogado != null) {
                break;
            }
        }

        menuEpecializado();
    }

    /**
     * Metodo responsável pelo menu principal
     * 
     */
    public static void menuPrincipal() throws IOException {
        System.out.println("--- Sistema Oficina Mecânica ---");
        System.out.println("1. Login");
        System.out.println("0. Sair");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuLogin();
            case 2 -> {
                System.out.println("Encerrando sistema ...");
                System.exit(0);
                break;
            }
        }
    }

    public static void menuEpecializado() throws IOException {
        if (usuarioLogado != null) {
            if (usuarioLogado.getCargo().equals("Gerente")) {
                menuGerente();
            } else if (usuarioLogado.getCargo().equals("Mecanico")) {
                menuMecanico();
            } else if (usuarioLogado.getCargo().equals("Caixa")) {
                menuCaixa();
            }
        }
    }

    /**
     * Metodo responsável pelo menu de gerente
     * 
     */
    public static void menuGerente() throws IOException {
        System.out.println("--- Menu Gerente ---");
        System.out.println("1. Clientes");
        System.out.println("2. Veiculos");
        System.out.println("3. Funcionarios");
        System.out.println("4. Ordens de Serviços");
        System.out.println("5. Estoque");
        System.out.println("6. Agenda");
        System.out.println("8. Relatório(s)");
        System.out.println("9. Logout");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuClientes();
            case 2 -> menuVeiculo();
            case 3 -> menuUsuarios();
            case 4 -> menuOrdemServico();
            case 5 -> menuEstoque();
            case 6 -> menuAgenda();
            case 8 -> menuRelatorio();
            case 9 -> menuPrincipal();
        }
    }

    /**
     * Metodo responsável pelo menu de gerente
     * 
     */
    public static void menuMecanico() throws IOException {
        System.out.println("--- Menu Mecânico ---");
        System.out.println("1. Ver minhas ordens de serviço");
        System.out.println("2. Atualizar status de ordem");
        System.out.println("3. Agenda");
        System.out.println("4. Veiculos");
        System.out.println("5. Estoque");
        System.out.println("6. logout");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuListarOrdensServicosPorCpf(usuarioLogado.getCpf());
            case 2 -> menuAtualizarOrdemServicoPorMecanico();
            case 3 -> menuAgenda();
            case 4 -> menuVeiculo();
            case 5 -> menuEstoque();
            case 6 -> menuPrincipal();
        }
    }

    public static void menuRelatorio() throws IOException {
        System.out.println("--- Menu Relatorio ---");
        System.out.println("1. Gerar relatório diario.");
        System.out.println("2. Gerar relatório mensal.");
        System.out.println("3. Menu Principal.");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                menuGerarRelatorioDiario();
                menuRelatorio();
                break;
            case 2:
                menuGerarRelatorioMensal();
                break;       
            case 3:
                menuEpecializado();
                break;
        }
    }
    
    public static void menuGerarRelatorioDiario(){
        System.out.println("--- Relatorio Diário ---");
        LocalDate dataRelatorio = null;
        while(true){
            System.out.println("Data do relatorio: ");
            String data = scanner.nextLine();
            try{
                dataRelatorio = Utilidades.gerarDataPorString(data);
                break;
            }catch(Exception e){
                System.out.println("Formato de data inexistente, tente novamente.");
            }
        }
        
        Relatorio relatorioHoje = Relatorio.getRelatorioDeHoje(dataRelatorio);
        
        
        if(relatorioHoje == null){
            System.out.println("Esse dia não houve relatório.");
        }else{
            System.out.println(relatorioHoje);
        }
    }
    
    
    public static void menuGerarRelatorioMensal(){
        System.out.println("---Relatório Mensal---");
        System.out.println("Digite o ano: ");
        int ano = scanner.nextInt();
        
        System.out.println("Digite o mês: ");
        int mes = scanner.nextInt();
        scanner.nextLine();
        
        Relatorio.getRelatorioMes(mes, ano);
    }

    public static void menuAgenda() throws IOException {
        System.out.println("--- Menu Agenda ---");
        System.out.println("1. Fazer um agendamento.");
        System.out.println("2. Ver Agenda.");
        System.out.println("3. Menu Principal.");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> menuAgendamentoPrevio();
            case 2 -> {
                Agenda.verAgenda();
                menuAgenda();
            }
            case 3 -> menuEpecializado();
        }

    }

    public static void menuCancelarAgendamento() {

    }

    public static void menuAgendamentoPrevio() throws IOException {
        System.out.println("--- Agendamento Prévio ---");
        Cliente cliente = null;
        while (true) {
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();
            if (Cliente.verificarClientePorCpf(cpf)) {
                cliente = Cliente.buscarClientePorCpf(cpf);
                break;
            }
        }

        Veiculo veiculo = null;
        while (true) {
            System.out.print("Placa do veiculo: ");
            String placa = scanner.nextLine();
            if (Veiculo.buscarVeiculoPorPlaca(placa)) {
                veiculo = Veiculo.retornaVeiculoPorPlaca(placa);
                break;
            }
        }

        System.out.print("Problema relatado pelo cliente: ");
        String descricaoProblema = scanner.nextLine();

        System.out.println("Qual motivo do Agendamento?");
        System.out.println("1. Diagnóstico\n2.Serviço especifico.");
        int opcao = 0;
        while (true) {
            System.out.print("Motivo: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao != 1 && opcao != 2) {
                System.out.println("Opção inválida tente novamente.");
            } else {
                break;
            }
        }
        Servico servicoPrincipal = null;
        switch (opcao) {
            case 1:
                servicoPrincipal = Servico.DIAGNOSTICO_INICIAL;
                break;
            case 2:
                Servico.listarServicos();
                int op = 0;
                while (true) {
                    System.out.println("Selecione um serviço: ");
                    op = scanner.nextInt();
                    if (op < 1 || op > 8) {
                        System.out.println("opção inválida.");
                    } else {
                        break;
                    }
                }
                switch (op) {
                    case 1:
                        servicoPrincipal = Servico.TROCA_DE_PNEU;
                        break;
                    case 2:
                        servicoPrincipal = Servico.ALINHAMENTO;
                        break;
                    case 3:
                        servicoPrincipal = Servico.BALANCIAMENTO;
                        break;
                    case 4:
                        servicoPrincipal = Servico.REVISAO_DOS_FREIOS;
                        break;
                    case 5:
                        servicoPrincipal = Servico.REVISAO_DO_SISTEMA_DE_ARREFECIMENTO;
                        break;
                    case 6:
                        servicoPrincipal = Servico.TROCA_DE_PASTILHAS;
                        break;
                    case 7:
                        servicoPrincipal = Servico.TROCA_CORREIA_DENTADA;
                        break;
                    case 8:
                        servicoPrincipal = Servico.DIAGNOSTICO_INICIAL;
                        break;
                }
        }

        int idElevadorProposto = 0;

        if (servicoPrincipal == Servico.BALANCIAMENTO || servicoPrincipal == Servico.ALINHAMENTO) {
            System.out.println("Serviço especializado, o agendamento será no elevado 3");
            idElevadorProposto = 3;
        } else {
            System.out.println("Serviço corriqueiro.");
            while (true) {
                System.out.print("Por favor, escolha um elevador de uso geral [1] ou [2]: ");
                idElevadorProposto = scanner.nextInt();
                scanner.nextLine();
                if (idElevadorProposto == 1 || idElevadorProposto == 2) {
                    break;
                } else {
                    System.out.println("Elevador inválido. Tente novamente.");
                }
            }
        }

        System.out.println("Elevador " + idElevadorProposto + " selecionado.");

        while (true) {
            System.out.println("--- Insira a hora e a data do agendamento ---");
            LocalDateTime dataHoraProposta = null;
            while (true) {
                System.out.print("Use o formato exato 'dd/MM/yyyy HH:mm': ");
                String dataHoraDigitada = scanner.nextLine();
                try {
                    dataHoraProposta = Utilidades.gerarDataHoraPorString(dataHoraDigitada);
                    System.out.println("Data e hora válidas: " + dataHoraProposta);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("ERRO: O formato da data inserido está incorreto.");
                }
            }
            int duracao = servicoPrincipal.getDuracaoEstimadaEmMinutos();

            boolean disponibilidade = Agenda.verificarDisponibilidade(dataHoraProposta, duracao, idElevadorProposto);

            if (disponibilidade) {
                OrdemServico novaOrdem = new OrdemServico.OrdemServicoBuilder()
                        .id(GerenciamentoIDs.proximoID("OrdemServico"))
                        .dataEHora(Utilidades.getDataEHoraAtuaisFormatadas())
                        .cliente(cliente)
                        .veiculo(veiculo)
                        .descricaoProblema(descricaoProblema)
                        .status("Aguardando Inspeção")
                        .adicionaServico(servicoPrincipal)
                        .build();

                Agenda.adicionarCompromisso(new Agendamento(novaOrdem, dataHoraProposta, idElevadorProposto));

                System.out.println("Agendamento Confirmado !!!");

                break;
            } else {
                System.out.println("Desculpe, este horário no elevador " + idElevadorProposto
                        + " já está ocupado. Por favor, tente outro horário.");
            }
        }
        JsonUtil.salvar("data/Agenda.json", Agenda.getCompromissos());
        menuAgenda();
    }

    public static void menuEstoque() throws IOException {
        System.out.println("--- Menu Estoque ---");
        System.out.println("1. Consultar Estoque completo");
        System.out.println("2. Adicionar um produto ao catálogo");// Permite cadastrar um item que a oficina nunca
                                                                  // vendeu antes.
        System.out.println("3. Registrar entrada de fornecedor");// Aumenta a quantidade de um produto já existente no
                                                                 // catálogo.
        System.out.println("4. Ajustar estoque manualmente");
        System.out.println("5. Gerar relatório de estoque baixo");
        System.out.println("6. Remover produto do catalogo");
        System.out.println("7. Voltar para o menu principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> Estoque.listarProdutos();
            case 2 -> menuAdicionarProduto();
            case 7 -> menuEpecializado();
        }
    }

    public static void menuAdicionarProduto() throws IOException {
        System.out.println("--- Adicionar Produto ---");
        System.out.print("Codigo: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: R$");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Quantidade em estoque: ");
        int qtdEmEstoque = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Selecione uma categoria:\n1 - Peça\n2 - Consumível");
        int categoria = scanner.nextInt();
        scanner.nextLine();
        Produto.CategoriaDoProduto categoriaEscolhida = null;
        switch (categoria) {
            case 1:
                categoriaEscolhida = Produto.CategoriaDoProduto.PEÇA;
                break;
            case 2:
                categoriaEscolhida = Produto.CategoriaDoProduto.CONSUMIVEL;
                break;
        }
        Produto novoProduto = new Produto(codigo, nome, preco, qtdEmEstoque, categoriaEscolhida);
        Estoque.adicionarProduto(novoProduto);
        System.out.println("Produto adicionado com sucesso!");
        System.out.print("Deseja adicionar outro produto? [S/N]");
        String opcao = scanner.nextLine().toUpperCase();
        switch (opcao) {
            case "S":
                menuAdicionarProduto();
            case "N":
                JsonUtil.salvar("data/produtos.json", listaDeProdutos);
                System.out.println("Voltando para o menu do Estoque.");
                menuEstoque();
        }
    }

    public static void menuAtualizarOrdemServicoPorMecanico() throws IOException {
        System.out.println("--- Atualizar Ordem de Serviço ----");
        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (OrdemServico.verificarOrdemServico(id, usuarioLogado.getCpf())) {
                System.out.print("Novo Status: ");
                String novo = scanner.nextLine();
                OrdemServico.atualizarStatusOrdemPorId(id, novo);
                System.out.println("Deseja atualizar outra ordem de serviço?[S/N]: ");
                String opcao = scanner.nextLine().toUpperCase();
                if (opcao.equals("S")) {
                    menuAtualizarOrdemServicoPorMecanico();
                } else if (opcao.equals("N")) {
                    menuEpecializado();
                } else {
                    System.out.println("Entrada inválida, indo para o menu principal.");
                    menuEpecializado();
                }
            } else {
                System.out.println("Tente novamente.");
            }
        }

    }

    /*
     * 8Metodo responsável pelo menu d usuário com cargo Caixa
     * 
     */
    public static void menuCaixa() throws IOException {
        System.out.println("--- Menu Caixa ---");
        System.out.println("1. Agenda");
        System.out.println("2. Clientes");
        System.out.println("3. Ordem de serviço");
        System.out.println("4. Veiculos");
        System.out.println("5. Estoque");
        System.out.println("6. logout");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuAgenda();
            case 2 -> menuClientes();
            case 3 -> menuOrdemServico();
            case 4 -> menuVeiculo();
            case 5 -> menuEstoque();
            case 6 -> menuPrincipal();
        }
    }

    public static void menuListarOrdensServicosPorCpf(String cpf) throws IOException {
        System.out.println("--- Ordens de serviços ---");
        System.out.println("--- " + usuarioLogado.getNome() + " ---");
        OrdemServico.listarOrdensServicosPorCpf(cpf);
        menuMecanico();
    }

    public static void menuOrdemServico() throws IOException {
        System.out.println("--- Ordens de serviço ---");
        System.out.println("1. Abrir ordem de serviço");
        System.out.println("2. Consultar ordens de serviços");
        System.out.println("3. Atualizar status ordem");
        System.out.println("4. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuCriarOrdemServico();
            case 2 -> {
                OrdemServico.listarOrdensServico();
                menuOrdemServico();
            }
            case 3 -> menuAtualizarOrdemServico();
            case 4 -> menuEpecializado();
        }
    }

    public static void menuAtualizarOrdemServico() throws IOException {
        System.out.println("=== ATUALIZAR STATUS DA ORDEM DE SERVIÇO ===");
        System.out.print("Digite o ID da ordem de serviço: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        System.out.print("Digite o novo status: ");
        String novoStatus = scanner.nextLine();
        OrdemServico.atualizarStatusOrdemPorId(id, novoStatus);
        System.out.print("Deseja atualizar outra ordem? (S/N): ");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuAtualizarOrdemServico();
        } else if (opcao.equals("N")) {
            menuEpecializado();
        } else {
            System.out.print("Opção inválida, voltando para o menu  principal.");
            menuEpecializado();
        }
    }

    public static void menuCriarOrdemServico() throws IOException {
        /**
         * Obter CPF do cliente e validar
         * 
         */
        Cliente cliente = null;
        while (cliente == null) {
            System.out.print("Digite o CPF do cliente: ");
            String cpfCliente = scanner.nextLine();
            cliente = Cliente.buscarClientePorCpf(cpfCliente); // método que você pode criar na classe Cliente para
                                                               // buscar o cliente pelo CPF
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        }

        /**
         * Obter CPF do mecânico e validar
         * 
         */
        Mecanico mecanico = null;
        while (mecanico == null) {
            System.out.print("Digite o CPF do mecânico: ");
            String cpfMecanico = scanner.nextLine();
            mecanico = Mecanico.buscarMecanicoPorCPF(cpfMecanico); // método que você pode criar na classe Mecanico para
                                                                   // buscar o mecânico pelo CPF
            if (mecanico == null) {
                System.out.println("Mecânico não encontrado ou não é mecânico. Tente novamente.");
            }
        }

        /**
         * Obter descrição do problema
         * 
         */
        System.out.print("Digite a descrição do problema: ");
        String descricaoProblema = scanner.nextLine();

        /**
         * Escolher o veículo para a ordem
         * 
         */
        Veiculo veiculo = null;
        while (veiculo == null) {
            System.out.println("Selecione um veículo:");
            for (int i = 0; i < Veiculo.getListaVeiculos().size(); i++) {
                Veiculo v = Veiculo.getListaVeiculos().get(i);
                System.out.println((i + 1) + ". " + v.getPlaca() + " - " + v.getModelo());
            }
            System.out.print("Escolha o número do veículo: ");
            int veiculoEscolhido = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            if (veiculoEscolhido > 0 && veiculoEscolhido <= Veiculo.getListaVeiculos().size()) {
                veiculo = Veiculo.getListaVeiculos().get(veiculoEscolhido - 1);
            } else {
                System.out.println("Veículo inválido, tente novamente.");
            }
        }

        /**
         * Criando a ordem de serviço
         * 
         */
        System.out.println("Construindo Ordem de serviço...");
        OrdemServico ordemServico = new OrdemServico.OrdemServicoBuilder()
                .id(GerenciamentoIDs.proximoID("OrdemServico"))
                .dataEHora(Utilidades.getDataEHoraAtuaisFormatadas())
                .cliente(cliente)
                .mecanico(mecanico)
                .descricaoProblema(descricaoProblema)
                .status("Aguardando Análise")
                .solucao("")
                .build();

        /**
         * Chama o método para adicionar a ordem de serviço na lista
         * 
         */
        OrdemServico.criarOrdemDeServico(ordemServico);

        /**
         * Exibe os detalhes da ordem criada
         * 
         */
        System.out.println("Ordem de serviço criada com sucesso!");
        System.out.println(ordemServico); // Exibe os detalhes da ordem criada
        System.out.print("Deseja criar outra ordem? (S/N): ");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuCriarOrdemServico();
        } else if (opcao.equals("N")) {
            menuEpecializado();
        } else {
            System.out.print("Opção inválida, voltando para o menu  principal.");
            menuEpecializado();
        }
    }

    public static void menuVeiculo() throws IOException {
        System.out.println("--- Veiculos ---");
        System.out.println("1. Cadastrar veiculo");
        System.out.println("2. Remover veiculo");
        System.out.println("3. Listar");
        System.out.println("4. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuCadastrarVeiculo();
            case 2 -> menuRemoverVeiculo();
            case 3 -> {
                Veiculo.listarVeiculos();
                menuVeiculo();
            }
            case 4 -> menuEpecializado();
        }
    }

    public static void menuRemoverVeiculo() throws IOException {
        System.out.println("--- Remover Veiculo ---");
        while (true) {
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            if (Veiculo.buscarVeiculoPorPlaca(placa)) {
                Veiculo.removerVeiculoPorPlaca(placa);
                System.out.print("Deseja remover outro Veículo?[S/N]: ");
                String opcao = scanner.nextLine().toUpperCase();
                if (opcao.equals("S")) {
                    menuRemoverVeiculo();
                } else if (opcao.equals("N")) {
                    menuEpecializado();
                } else {
                    System.out.println("Entrada inválida, voltando para o menu principal.");
                    menuEpecializado();
                }
            } else {
                System.out.println("Tente novamente.");
            }
        }

    };

    public static void menuCadastrarVeiculo() throws IOException {
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

        if (sucesso) {
            System.out.println("Deseja Cadastrar outro veículo?[S/N]");
            String opcao = scanner.nextLine().toUpperCase();
            if (opcao.equals("S")) {
                JsonUtil.salvar("data/veiculos.json", Veiculo.getListaVeiculos());
                menuCadastrarVeiculo();
            } else if (opcao.equals("N")) {
                JsonUtil.salvar("data/veiculos.json", Veiculo.getListaVeiculos());
                menuEpecializado();
            } else {
                System.out.println("Entrada inválida, voltando para o menu pricipal.");
                JsonUtil.salvar("data/veiculos.json", Veiculo.getListaVeiculos());
                menuEpecializado();
            }
        }

    }

    /**
     * Metodo responsável pelo menu de clientes
     * 
     */
    public static void menuClientes() throws IOException {
        System.out.println("--- Clientes ---");
        System.out.println("1. Listar");
        System.out.println("2. Cadastrar");
        System.out.println("3. Remover");
        System.out.println("4. Editar");
        System.out.println("5. Menu Principal");

        int opcao = scanner.nextInt();
        scanner.nextLine();// Captura o resto da ultima leitura

        switch (opcao) {
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

    /**
     * Metodo responsável pelo menu de cadastro de clientes
     * 
     */
    public static void menuCadastrarCliente() throws IOException {
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
        /**
         * pega a entrada do usuário e tranforma em maiúsculo.
         * 
         */
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuCadastrarCliente();
        } else if (opcao.equals("N")) {
            JsonUtil.salvar("data/clientes.json", Cliente.getListaClientes());
            menuClientes();
        } else {
            System.out.println("Opção inválida, voltando para o menu principal");
            JsonUtil.salvar("data/clientes.json", Cliente.getListaClientes());
            menuEpecializado();
        }
    }

    /*
     * 8Metodo responsável pelo menu de remoção de cliente.
     */
    public static void menuRemoverCliente() throws IOException {
        System.out.println("--- Remover Cliente ---");
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente.removerClientePorCpf(cpf);
        System.out.print("Deseja remover outro cliente?[S/N]: ");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuRemoverCliente();
        } else if (opcao.equals("N")) {
            menuClientes();
        } else {
            System.out.println("Opção inávila. Voltando para o menu principal.");
            menuEpecializado();
        }
    }

    /**
     * Metodo responsável pelo menu de edição de dados do cliente.
     * 
     */
    public static void menuEditarCliente() throws IOException {
        System.out.println("--- Editar Cliente ---");
        String cpf = menuVerificarUsuarioPorCpf();
        menuOpcoesEdicaoCliente(cpf);
    }

    /**
     * Metodo responsável pelo menu opções de Edição de cliente.
     * 
     * @param cpf do cliente
     */
    public static void menuOpcoesEdicaoCliente(String cpf) throws IOException {
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Endereço");
        System.out.println("4. Telefone");
        System.out.println("5. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();// limpando o resto da ultima leitura.
        switch (opcao) {
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
                /**
                 * atualiza o valor de referÊncia
                 * 
                 */
                cpf = novocpf;
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

    /**
     * Metodo responsável por confirmar a continuação de edição do mesmo cliente
     * 
     * @param cpf
     */
    public static void menuContinuidadeEdicaoCliente(String cpf) throws IOException {
        System.out.println("Continuar com a edição do cliente atual?\n"
                + "S. Sim\nN. Editar outro usuário\n0. Menu principal");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuOpcoesEdicaoCliente(cpf);
        } else if (opcao.equals("N")) {
            menuEditarCliente();
        } else if (opcao.equals("0")) {
            menuEpecializado();
        } else {
            System.out.println("Opção inválida, indo para o menu inicial.");
            menuEpecializado();
        }
    }

    /**
     * Metodo resposável pelu menu de verificação de usuário.
     * 
     * @return
     */
    public static String menuVerificarUsuarioPorCpf() throws IOException {
        while (true) {
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();
            if (cpf.equals("0")) {
                menuEpecializado();
            } else {
                if (Cliente.verificarClientePorCpf(cpf)) {
                    return cpf;
                } else {
                    System.out.print("Tente de novo ou digite 0 para voltar para o menu principal.");
                }
            }
        }
    }

    /**
     * Metodo responsável pelo menu de usuários.
     * 
     */
    public static void menuUsuarios() throws IOException {
        System.out.println("--- Usuários ---");
        System.out.println("1. Listar");
        System.out.println("2. Cadastrar");
        System.out.println("3. Remover");
        System.out.println("4. Editar");
        System.out.println("5. Menu Principal");

        int opcao = scanner.nextInt();
        scanner.nextLine();// Recebe o resto da ultima leitura

        switch (opcao) {
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

    /**
     * Metodo responsável pelo menu de edição de Usuário
     * 
     */
    public static void menuEdicaoUsuario() throws IOException {
        System.out.println("--- Edição Usuário ---");
        String cpf = menuVerificarUsuario();
        menuOpcoesEdicaoUsuario(cpf);
    }

    /**
     * Metodo responsável pelo menu de opções de edição do usuário.
     * 
     * @param cpf
     */
    public static void menuOpcoesEdicaoUsuario(String cpf) throws IOException {
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Login");
        System.out.println("4. Senha");
        System.out.println("5. Cargo");
        System.out.println("6. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();// limpando o resto da ultima leitura.
        switch (opcao) {
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

    /**
     * Metodo responsável pelo menu de continuidade de edição de usuários.
     * 
     * @param cpf
     */
    public static void menuContinuidadeEdicaoUsuario(String cpf) throws IOException {
        System.out.print("Deseja continuar a editar o usuário atual?\n"
                + "S. Sim\nN. Editar outro usuário\n0. Menu Principal");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuOpcoesEdicaoUsuario(cpf);
        } else if (opcao.equals("N")) {
            menuEdicaoUsuario();
        } else if (opcao.equals("0")) {
            menuEpecializado();
        } else {
            System.out.println("Opção inválida, voltando para o menu principal.");
            menuEpecializado();
        }
    }

    /**
     * Metodo responsável pelo menu de remoção de usuário.
     * 
     */
    public static void menuRemoverUsuario() throws IOException {
        System.out.println("--- Remove Usuário ---");
        String cpf = menuVerificarUsuario();
        SistemaLogin.removerUsuarioPorCpf(cpf);
    }

    /**
     * Metodo responsável pela menu de verificação de existência do usuário
     * 
     * @return cpf do cliente existente
     */
    public static String menuVerificarUsuario() {
        System.out.print("Cpf: ");
        String cpf = scanner.nextLine();
        if (SistemaLogin.verificarUsuarioPeloCpf(cpf)) {
            return cpf;
        } else {
            System.out.println("Tente novamente.");
            return menuVerificarUsuario();
        }
    }

    /**
     * Metodo responsável pelo menu de cadastro de usuários.
     * 
     */
    public static void menuCadastrarUsuario() throws IOException {
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

        switch (cargo) {
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
        if (opcao.equals("S")) {
            JsonUtil.salvar("data/usuarios.json", SistemaLogin.getListaUsuarios());
            menuCadastrarUsuario();
        } else if (opcao.equals("N")) {
            JsonUtil.salvar("data/usuarios.json", SistemaLogin.getListaUsuarios());
            menuUsuarios();
        }
    }
}
