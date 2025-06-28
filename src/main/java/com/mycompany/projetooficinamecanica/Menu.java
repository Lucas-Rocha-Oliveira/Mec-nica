package com.mycompany.projetooficinamecanica;

import static com.mycompany.projetooficinamecanica.Estoque.listaDeProdutos;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner; //Permite ler dados digitados pelo usuário.

import com.mycompany.projetooficinamecanica.TransacaoFinanceira.TipoTransacao;

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
        System.out.println("7. Balanco");
        System.out.println("8. Relatório(s)");
        System.out.println("9. Lançar venda.");
        System.out.println("10. Logout");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuClientes();
            case 2 -> menuVeiculo();
            case 3 -> menuUsuarios();
            case 4 -> menuOrdemServico();
            case 5 -> menuEstoque();
            case 6 -> menuAgenda();
            case 7 -> menuBalanco();
            case 8 -> menuRelatorio();
            case 9 -> menuLancarVenda();
            case 10 -> menuPrincipal();

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

    public static void menuBalanco() throws IOException {

        System.out.println("--- Menu Balaço ---");
        System.out.println("1. Adicionar Despesa(s).");
        System.out.println("2. Listar Despesas.");
        System.out.println("3. Gerar Balanço Mensal.");
        System.out.println("4. Menu Principal.");

        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1:
                menuAdicionarDespesa();
            case 2:
                menuListarDespesa();
                break;
            case 3:
                menuGerarBalanço();
            case 4:
                menuEpecializado();
        }
    }

    public static void menuGerarBalanço(){
        int mes = 0;
        while (true) {
            System.out.print("Mês (1 a 12): ");
            mes = scanner.nextInt();
            scanner.nextLine();
            if (mes < 1 || mes > 12) {
                System.out.println("Selecione um mês válido.");
            } else {
                break;
            }
        }
        Relatorio.getRelatoriosPorMes(mes);
    }

    public static void menuListarDespesa() {
        System.out.println("--- Listar Despesa ---");
        int mes = 0;
        while (true) {
            System.out.print("Mês (1 a 12): ");
            mes = scanner.nextInt();
            scanner.nextLine();
            if (mes < 1 || mes > 12) {
                System.out.println("Selecione um mês válido.");
            } else {
                break;
            }
        }

        StringBuilder resultado = Relatorio.listarDespessasPorMes(mes);
        System.out.println(resultado.toString());
    }

    public static void menuAdicionarDespesa() throws IOException {
        System.out.println("--- Adicionar Despesa ---");

        while (true) {
            System.out.print("Descrição da Despesa: ");
            String descricao = scanner.nextLine();

            System.out.print("Custo da Despesa: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();
            int opcaoTipo = 0;
            while (true) {
                System.out.println("Tipo da Despesa\n1-DESPESA_SALARIO \n2- DESPESA_COMPRA_PECA\n3 - DESPESA_OUTRAS\n");
                opcaoTipo = scanner.nextInt();
                scanner.nextLine();
                if (opcaoTipo != 1 && opcaoTipo != 2 && opcaoTipo != 3) {
                    System.out.println("Opção inválida, tente novamente.");
                } else {
                    break;
                }
            }
            TipoTransacao tipo = null;
            switch (opcaoTipo) {
                case 1:
                    tipo = TipoTransacao.DESPESA_SALARIO;
                    break;
                case 2:
                    tipo = TipoTransacao.DESPESA_COMPRA_PECA;
                    break;
                case 3:
                    tipo = TipoTransacao.DESPESA_OUTRAS;
                    break;
            }

            TransacaoFinanceira novaTransacaoFinanceira = new TransacaoFinanceira(preco, descricao, LocalDateTime.now(),
                    tipo);
            Relatorio.getRelatorioDeHoje().adicionarTransacao(novaTransacaoFinanceira);
            Relatorio.salvarRelatorios();

            while (true) {
                System.out.print("Deseja adicionar outra despesa? [S/N]: ");
                String opcao = scanner.nextLine().toUpperCase();

                if (!opcao.equals("S") && !opcao.equals("N")) {
                    System.out.println("Opção inválida, tente novamente");
                } else {
                    if (opcao.equals("S")) {
                        menuAdicionarDespesa();
                        break;
                    } else {
                        menuBalanco();
                        break;
                    }
                }
            }
        }
    }

    public static void menuRelatorio() throws IOException {
        System.out.println("--- Menu Relatorio ---");
        System.out.println("1. Gerar relatório diario.");
        System.out.println("2. Gerar relatório mensal.");
        System.out.println("3. Menu Principal.");

        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1:
                menuGerarRelatorioDiario();
                menuRelatorio();
                break;
            case 2:
                menuGerarRelatorioMensal();
                menuRelatorio();
                break;
            case 3:
                menuEpecializado();
                break;
        }
    }

    public static void menuGerarRelatorioDiario() {
        System.out.println("--- Relatorio Diário ---");
        LocalDate dataRelatorio = null;
        while (true) {
            System.out.print("Data do relatorio: ");
            String data = scanner.nextLine();
            try {
                dataRelatorio = Utilidades.gerarDataPorString(data);
                break;
            } catch (Exception e) {
                System.out.println("Formato de data inexistente, tente novamente.");
            }
        }

        Relatorio relatorioHoje = Relatorio.getRelatorioDeHoje();

        if (relatorioHoje == null) {
            System.out.println("Esse dia não houve relatório.");
        } else {
            System.out.println(relatorioHoje);
        }
    }

    public static void menuGerarRelatorioMensal() {
        System.out.println("---Relatório Mensal---");
        System.out.println("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o mês: ");
        int mes = scanner.nextInt();
        scanner.nextLine();

        Relatorio.getRelatorioMes(mes, ano);
    }

    public static void menuAgenda() throws IOException {
        System.out.println("--- Menu Agenda ---");
        System.out.println("1. Fazer um agendamento.");
        System.out.println("2. Ver Agenda.");
        System.out.println("3. Cancelar Agendamento.");
        System.out.println("4. Menu Principal.");

        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuAgendamentoPrevio();
            case 2 -> {
                Agenda.verAgenda();
                menuAgenda();
            }
            case 3 -> menuCancelarAgendamento();
            case 4 -> menuEpecializado();
        }

    }

    public static void menuCancelarAgendamento() throws IOException {
        System.out.println("--- Cancelar Agendamento ---");
        Agendamento agendamento = null;
        while (true) {
            System.out.print("ID do agendamento: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            agendamento = Agenda.getCompromissoPorId(id);
            if (agendamento == null) {
                System.out.println("ID inválido, tente novamente.");
            } else {
                break;
            }
        }
        Agenda.cancelarAgendamento(agendamento);
        System.out.print("Agendamento cancelado com sucesso !\nDeseja fazer um novo agendamento?: [S/N]: ");
        String continuar = scanner.nextLine().toUpperCase();
        switch (continuar) {
            case "S":
                menuCancelarAgendamento();
                break;
            case "N":
                menuAgenda();
                break;
            default:
                menuEpecializado();
                break;
        }

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
            } else {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        }

        Veiculo veiculo = null;
        while (true) {
            System.out.print("Placa do veículo: ");
            String placa = scanner.nextLine();
            if (Veiculo.buscarVeiculoPorPlaca(placa)) {
                veiculo = Veiculo.retornaVeiculoPorPlaca(placa);
                break;
            } else {
                System.out.println("Veículo não encontrado. Tente novamente.");
            }
        }

        System.out.print("Problema relatado pelo cliente: ");
        String descricaoProblema = scanner.nextLine();

        System.out.println("Qual o motivo do agendamento?");
        System.out.println("1. Diagnóstico");
        System.out.println("2. Serviço específico");
        int opcao = 0;
        while (true) {
            System.out.print("Motivo: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer
                if (opcao == 1 || opcao == 2)
                    break;
                System.out.println("Opção inválida, tente novamente.");
            } catch (InputMismatchException e) {
                scanner.nextLine(); // limpar entrada inválida
                System.out.println("Por favor, digite um número válido.");
            }
        }

        Servico servicoPrincipal = null;
        switch (opcao) {
            case 1:
                servicoPrincipal = Servico.DIAGNOSTICO_INICIAL;
                break;
            case 2:
                System.out.println("Escolha o serviço específico:");
                Servico[] listaServicos = Servico.values();
                for (int i = 0; i < listaServicos.length; i++) {
                    System.out.println(
                            (i + 1) + ". " + listaServicos[i].getDescricao() + " - R$" + listaServicos[i].getPreco());
                }

                int escolha = 0;
                while (true) {
                    System.out.print("Serviço: ");
                    try {
                        escolha = scanner.nextInt();
                        scanner.nextLine(); // limpar buffer
                        if (escolha >= 1 && escolha <= listaServicos.length) {
                            servicoPrincipal = listaServicos[escolha - 1];
                            break;
                        } else {
                            System.out.println("Escolha inválida, tente novamente.");
                        }
                    } catch (InputMismatchException e) {
                        scanner.nextLine(); // limpar entrada inválida
                        System.out.println("Por favor, digite um número válido.");
                    }
                }
                break;
        }

        // Seleção aleatória de elevador entre 1 e 3
        Random random = new Random();
        int idElevadorProposto = random.nextInt(3) + 1;
        System.out.println("Elevador " + idElevadorProposto + " selecionado.");

        int duracao = servicoPrincipal.getDuracaoEstimadaEmMinutos();

        while (true) {
            LocalDateTime dataHoraProposta = null;
            while (true) {
                System.out.print("Use o formato exato 'dd/MM/yyyy HH:mm': ");
                String dataHoraDigitada = scanner.nextLine();
                try {
                    dataHoraProposta = Utilidades.gerarDataHoraPorString(dataHoraDigitada);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("ERRO: O formato da data inserido está incorreto.");
                }
            }

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

                OrdemServico.criarOrdemDeServico(novaOrdem);

                Agendamento novoAgendamento = new Agendamento(novaOrdem, dataHoraProposta, idElevadorProposto);
                Agenda.adicionarCompromisso(novoAgendamento);

                String descricaoTransacao = "Receita do agendamento OS #" + novaOrdem.getId() + ": "
                        + servicoPrincipal.getDescricao();
                double valorServico = servicoPrincipal.getPreco();

                TransacaoFinanceira transacao = new TransacaoFinanceira(
                        valorServico,
                        descricaoTransacao,
                        LocalDateTime.now(),
                        TransacaoFinanceira.TipoTransacao.RECEITA_SERVICO);

                Relatorio relatorioDoDia = Relatorio.getRelatorioPorData(dataHoraProposta.toLocalDate());
                relatorioDoDia.adicionarTransacao(transacao);
                Relatorio.salvarRelatorios();

                System.out.println("Agendamento Confirmado!");
                break;
            } else {
                System.out.println("Desculpe, este horário no elevador " + idElevadorProposto
                        + " já está ocupado. Por favor, tente outro horário.");
            }
        }

        menuAgenda(); // Volta ao menu da agenda
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
        System.out.println("6. Lançar Venda.");
        System.out.println("7. logout");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuAgenda();
            case 2 -> menuClientes();
            case 3 -> menuOrdemServico();
            case 4 -> menuVeiculo();
            case 5 -> menuEstoque();
            case 6 -> menuLancarVenda();
            case 7 -> menuPrincipal();
        }
    }

    public static void menuLancarVenda() throws IOException {
        System.out.println("--- Loja ---");

        Produto produto = null;
        while (true) {
            System.out.print("Código do Produto: ");
            int codigo = scanner.nextInt();
            scanner.nextLine();
            produto = Estoque.buscarProdutoPorCodigo(codigo);
            if (produto == null) {
                System.out.println("Esse produto não existe no estoque, tente outro.");
            } else {
                break;
            }
        }

        int quantidade = 0;
        while (true) {
            System.out.print("Quantidade vendida: ");
            quantidade = scanner.nextInt();
            scanner.nextLine();
            if ((produto.getQuantidadeEmEstoque() - quantidade) < 0) {
                System.out.println("Quantidade pretendida é maior que em estoque.");
            } else {
                break;
            }
        }

        Estoque.subtrairQuantidadeEstoque(produto, quantidade);

        String descricao = "Venda de " + quantidade + "x " + produto.getNome();
        double valorVenda = produto.getPreco() * quantidade;
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                valorVenda,
                descricao,
                LocalDateTime.now(),
                TransacaoFinanceira.TipoTransacao.RECEITA_VENDA_PECA);

        Relatorio relatorioDoDia = Relatorio.getRelatorioPorData(LocalDate.now());
        relatorioDoDia.adicionarTransacao(transacao);
        Relatorio.salvarRelatorios();

        System.out.println("Venda Lançada com sucesso!");
        System.out.print("Deseja lançar outra venda? [S/N]: ");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) {
            menuLancarVenda();
        } else {
            menuEpecializado();
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
        System.out.println("4. Adicionar Serviço Realizado");
        System.out.println("5. Adicionar Produto Utilizado");
        System.out.println("6. Menu Principal");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1 -> menuCriarOrdemServico();
            case 2 -> {
                OrdemServico.listarOrdensServico();
                menuOrdemServico();
            }
            case 3 -> menuAtualizarOrdemServico();
            case 4 -> menuAdicionarServicoRealizado();
            case 5 -> menuAdicionarPecaUltilizada();
            case 6 -> menuEpecializado();
        }
    }

    public static void menuAdicionarPecaUltilizada() throws IOException {
        System.out.println("--- Produto Utilizado ---");

        OrdemServico os = null;

        while (true) {
            System.out.println("Insira o id da Ordem de Serviço");
            int id = scanner.nextInt();
            scanner.nextLine();
            os = OrdemServico.getOrdemServicoPorId(id);
            if (os == null) {
                System.out.println("Nenhuma OS possuí esse ID");
            } else {
                break;
            }
        }
        Produto produto = null;
        while (true) {
            System.out.println("Insira o codigo do produto.");
            int id = scanner.nextInt();
            scanner.nextLine();
            produto = Estoque.buscarProdutoPorCodigo(id);
            if (produto == null) {
                System.out.println("Código inválido, tente novamente.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Quantidade utilizada: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();
            if ((produto.getQuantidadeEmEstoque() - quantidade) < 0) {
                System.out.println("Quantidade solicitada indiponível no estoque.");
            } else {
                Estoque.subtrairQuantidadeEstoque(produto, quantidade);
                String descricao = "Produto: " + produto.getNome() + " - Quantidade: " + quantidade;
                TransacaoFinanceira novaTransacaoFinanceira = new TransacaoFinanceira((produto.getPreco() * quantidade),
                        descricao, LocalDateTime.now(), TipoTransacao.RECEITA_VENDA_PECA);
                Relatorio.getRelatorioDeHoje().adicionarTransacao(novaTransacaoFinanceira);
                Relatorio.salvarRelatorios();
                for (int i = 0; i <= quantidade; i++) {
                    os.adicionarProduto(produto);
                }
                System.out.println("Produto registrado com sucesso. \nDeseja adicionar outra peça? [S/N]: ");
                String continuar = scanner.nextLine().toUpperCase();
                switch (continuar) {
                    case "S":
                        menuAdicionarPecaUltilizada();
                        break;
                    case "N":
                        menuOrdemServico();
                        JsonUtil.salvar("data/produtos.json", listaDeProdutos);
                        break;
                    default:
                        menuEpecializado();
                        JsonUtil.salvar("data/produtos.json", listaDeProdutos);
                        break;
                }
            }
        }
    }

    public static void menuAdicionarServicoRealizado() throws IOException {

        System.out.println("--- Servico Realizado ---");

        OrdemServico os = null;

        while (true) {
            System.out.println("Insira o id da Ordem de Serviço");
            int id = scanner.nextInt();
            scanner.nextLine();
            os = OrdemServico.getOrdemServicoPorId(id);
            if (os == null) {
                System.out.println("Nenhuma OS possuí esse ID");
            } else {
                break;
            }
        }

        Servico.listarServicos();
        Servico servicoAdicionado = null;
        int op = 0;

        while (true) {
            System.out.println("Selecione um serviço: ");
            op = scanner.nextInt();
            scanner.nextLine();
            if (op < 1 || op > 8) {
                System.out.println("opção inválida.");
            } else {
                break;
            }
        }
        switch (op) {
            case 1:
                servicoAdicionado = Servico.TROCA_DE_PNEU;
                break;
            case 2:
                servicoAdicionado = Servico.ALINHAMENTO;
                break;
            case 3:
                servicoAdicionado = Servico.BALANCEAMENTO;
                break;
            case 4:
                servicoAdicionado = Servico.REVISAO_DOS_FREIOS;
                break;
            case 5:
                servicoAdicionado = Servico.REVISAO_DO_SISTEMA_DE_ARREFECIMENTO;
                break;
            case 6:
                servicoAdicionado = Servico.TROCA_DE_PASTILHAS;
                break;
            case 7:
                servicoAdicionado = Servico.TROCA_CORREIA_DENTADA;
                break;
            case 8:
                servicoAdicionado = Servico.DIAGNOSTICO_INICIAL;
                break;
        }

        os.adicionarServico(servicoAdicionado);
        TransacaoFinanceira novaTransacaoFinanceira = new TransacaoFinanceira(servicoAdicionado.getPreco(),
                servicoAdicionado.getDescricao(), LocalDateTime.now(), TipoTransacao.RECEITA_SERVICO);
        Relatorio.getRelatorioDeHoje().adicionarTransacao(novaTransacaoFinanceira);

        System.out.println("Serviço Adicionado com sucesso.\nDeseja adicionar outro serviço?: [S/N]: ");
        String continuar = scanner.nextLine().toUpperCase();
        switch (continuar) {
            case "S":
                menuAdicionarServicoRealizado();
                break;
            case "N":
                menuOrdemServico();
                Relatorio.salvarRelatorios();
                break;
            default:
                menuEpecializado();
                Relatorio.salvarRelatorios();
                break;
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