package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Classe principal para demonstrar a entrega final do projeto,
 * abordando todas as questões solicitadas.
 */
public class DemonstracaoFinal {

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("  INÍCIO DA DEMONSTRAÇÃO FINAL DO PROJETO OFICINA      ");
        System.out.println("=========================================================\n");

        // --- Demonstrações da "Questão 2" (itens 1-14 da imagem) ---
        demonstrarQuestoesEstruturais();
        demonstrarQuestao3_ToString();
        demonstrarQuestao5_Elevadores();
        demonstrarQuestao6e7_CRUD();
        demonstrarQuestao11e12_ContadoresVeiculo();
        
        // --- Demonstrações das Questões 15, 16, 17 e 18 (solicitação anterior) ---
        List<Cliente> clientes = criarDadosIniciaisClientes(); // Cria dados para as demos
        criarDadosIniciaisEstoque();

        demonstrarQuestao15(clientes);
        demonstrarQuestao16(clientes);
        demonstrarQuestao17(clientes);
        demonstrarQuestao18();
    }
    
    // =================================================================================
    // NOVAS DEMONSTRAÇÕES (BASEADAS NA IMAGEM ENVIADA)
    // =================================================================================

    public static void demonstrarQuestoesEstruturais() {
        System.out.println("\n// --- Análise dos Requisitos Estruturais (não demonstráveis em execução) ---\n");
        System.out.println("   - Requisito 1 (Implementar Classes): Cumprido pela existência do projeto.");
        System.out.println("   - Requisito 2 (Acessos de Colaborador/Admin): Cumprido pela lógica da classe Menu.");
        System.out.println("   - Requisito 4 (Uso de 'super'): Cumprido nos construtores das subclasses (Ex: Gerente, Cliente).");
        System.out.println("   - Requisito 8 (Verificar Ordens de Serviço): Cumprido nos menus interativos.");
        System.out.println("   - Requisito 9 (Armazenamento Dinâmico): Cumprido pelo uso de ArrayList em todo o projeto.");
        System.out.println("   - Requisito 10 (Extrato de Venda): Demonstração feita na Questão 18.");
        System.out.println("   - Requisito 14 (Salvar em JSON): É a base de todo o sistema com a classe JsonUtil.\n");
        System.out.println("---------------------------------------------------------");
    }
    
    public static void demonstrarQuestao3_ToString() {
        System.out.println("\n// --- Questão 3: Demonstração do método toString() sobrescrito ---\n");
        Cliente cliente = new Cliente("Cliente Exemplo", "999.999.999-99", "Rua Teste, 123", "38-99999-9999");
        System.out.println("Ao usar System.out.println() em um objeto Cliente, o método toString() customizado é chamado:");
        System.out.println(cliente);
        System.out.println("---------------------------------------------------------");
    }
    
    public static void demonstrarQuestao5_Elevadores() {
        System.out.println("\n// --- Questão 5: Demonstração do Vetor Estático de Elevadores ---\n");
        System.out.println("Acessando o vetor estático da classe Oficina para provar sua existência e inicialização:");
        for (Elevador elevador : Oficina.getElevadores()) {
            System.out.println("   - " + elevador.toString());
        }
        System.out.println("---------------------------------------------------------");
    }
    
    public static void demonstrarQuestao6e7_CRUD() {
        System.out.println("\n// --- Questão 6 e 7: Demonstração de um fluxo CRUD (Create, Read, Update, Delete) ---\n");
        List<Cliente> listaParaCRUD = new ArrayList<>();
        Cliente.setListaClientes(listaParaCRUD); // Usando uma lista temporária para não sujar outras demos

        // 1. CREATE
        Cliente novoCliente = new Cliente("Maria Souza", "555.555.555-55", "Av. Principal", "N/A");
        listaParaCRUD.add(novoCliente);
        System.out.println("--> CREATE: Cliente '" + novoCliente.getNome() + "' adicionado.");

        // 2. READ
        System.out.println("--> READ: Lista atual de clientes:");
        listaParaCRUD.forEach(c -> System.out.println("   - " + c.getNome() + " (Telefone: " + c.getTelefone() + ")"));

        // 3. UPDATE
        novoCliente.setTelefone("38-5555-5555");
        System.out.println("\n--> UPDATE: Telefone da cliente '" + novoCliente.getNome() + "' atualizado.");
        System.out.println("--> READ Pós-Update:");
        listaParaCRUD.forEach(c -> System.out.println("   - " + c.getNome() + " (Telefone: " + c.getTelefone() + ")"));
        
        // 4. DELETE
        listaParaCRUD.remove(novoCliente);
        System.out.println("\n--> DELETE: Cliente '" + novoCliente.getNome() + "' removido.");
        System.out.println("--> READ Pós-Delete: (a lista deve estar vazia)");
        if (listaParaCRUD.isEmpty()) {
            System.out.println("   - Lista vazia.");
        } else {
            listaParaCRUD.forEach(c -> System.out.println(c.getNome()));
        }
        System.out.println("---------------------------------------------------------");
    }
    
    public static void demonstrarQuestao11e12_ContadoresVeiculo() {
        System.out.println("\n// --- Questão 11 e 12: Demonstração dos Contadores Estáticos de Veículos ---\n");
        // Nota: os contadores são estáticos, então eles podem ter valores de execuções anteriores na mesma sessão.
        // O ideal seria resetá-los, mas para a demo, apenas criaremos novos e mostraremos o incremento.
        
        System.out.println("Valores dos contadores ANTES de criar novos veículos:");
        Veiculo.mostrarContadores();
        
        // Criando 3 novos veículos para demonstrar o incremento
        Cliente clienteDemo = new Cliente("Dono dos Veículos", "123.456.789-00", "", "");
        new Veiculo("DEMO-001", "Carro A", "Marca A", "Azul", 2023, clienteDemo.getCpf());
        new Veiculo("DEMO-002", "Carro B", "Marca B", "Verde", 2024, clienteDemo.getCpf());
        new Veiculo("DEMO-003", "Carro C", "Marca C", "Branco", 2025, clienteDemo.getCpf());
        System.out.println("\n3 novos veículos foram instanciados...");

        System.out.println("\nValores dos contadores DEPOIS de criar novos veículos (demonstrando o incremento):");
        // O método mostrarContadores() já cumpre o requisito 12 de retornar as instâncias.
        Veiculo.mostrarContadores();
        System.out.println("---------------------------------------------------------");
    }

    public static void demonstrarQuestao15(List<Cliente> clientes) {
        System.out.println("\n// --- Questão 15: Teste do Iterator e For-Each ---\n");
        System.out.println("--> 1. Percorrendo a lista de Clientes com Iterator e while:");
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            System.out.println("   - " + cliente.getNome());
        }
        System.out.println("\n--> EXPLICAÇÃO Iterator vs For-Each:");
        System.out.println("O loop 'for-each' é 'açúcar sintático' para o padrão Iterator. É uma forma mais limpa e segura de escrever o mesmo código de iteração.");
        System.out.println("---------------------------------------------------------");
    }

    public static void demonstrarQuestao16(List<Cliente> clientes) {
        System.out.println("\n// --- Questão 16: Teste do Comparator com Collections.sort() ---\n");
        System.out.println("--> Lista de Clientes (Ordem Original):");
        clientes.forEach(c -> System.out.println("   - " + c.getNome() + " (CPF: " + c.getCpf() + ")"));

        System.out.println("\n--> Ordenando por NOME...");
        // CORREÇÃO APLICADA AQUI
        Collections.sort(clientes, new ClienteComparator.ClienteNomeComparator());
        clientes.forEach(c -> System.out.println("   - " + c.getNome()));

        System.out.println("\n--> Ordenando por CPF...");
        // CORREÇÃO APLICADA AQUI
        Collections.sort(clientes, new ClienteComparator.ClienteCpfComparator());
        clientes.forEach(c -> System.out.println("   - " + c.getNome() + " (CPF: " + c.getCpf() + ")"));
        System.out.println("---------------------------------------------------------");
    }
    
    public static void demonstrarQuestao17(List<Cliente> clientes) {
        System.out.println("\n// --- Questão 17: Teste do método find() e Collections.binarySearch() ---\n");
        Cliente clienteParaBuscar = new Cliente("Carlos Pereira", "333.333.333-33", "", "");
        System.out.println("--> 1. Testando nosso método find():");
        // CORREÇÃO APLICADA AQUI
        Cliente encontradoComFind = Cliente.find(clientes, clienteParaBuscar, new ClienteComparator.ClienteCpfComparator());
        System.out.println("   Resultado do find(): " + (encontradoComFind != null ? "Encontrado -> " + encontradoComFind.getNome() : "Não encontrado."));

        System.out.println("\n--> 2. Testando Collections.binarySearch():");
        // CORREÇÃO APLICADA AQUI
        Collections.sort(clientes, new ClienteComparator.ClienteCpfComparator()); // Garantindo que está ordenado
        int indice = Collections.binarySearch(clientes, clienteParaBuscar, new ClienteComparator.ClienteCpfComparator());
        System.out.println("   Resultado do binarySearch(): " + (indice >= 0 ? "Encontrado na posição " + indice : "Não encontrado."));
        System.out.println("\n--> COMPARAÇÃO: find() é uma busca linear (O(n)) que não exige ordenação. binarySearch() é uma busca binária (O(log n)), muito mais rápida, mas exige que a lista esteja ordenada.");
        System.out.println("---------------------------------------------------------");
    }

    public static void demonstrarQuestao18() {
        System.out.println("\n// --- Questão 18: Demonstração do Fluxo de Atendimento (Resumida) ---\n");

        Random random = new Random();
        Mecanico mecanicoResponsavel = new Mecanico("João Mecânico", "100.100.100-10", "joao", "123");
        
        System.out.println("Estoque INICIAL de 'Vela de Ignição': " + Estoque.buscarProdutoPorCodigo(101).getQuantidadeEmEstoque());
        System.out.println("---------------------------------------------------------\n");

        for (int i = 1; i <= 2; i++) { // Reduzido para 2 clientes para um output mais limpo
            System.out.println(">>> ATENDIMENTO CLIENTE #" + i);
            Cliente novoCliente = new Cliente("Cliente " + i, "00" + i + ".00" + i + ".00" + i + "-0" + i, "Rua " + i, "99999-000" + i);
            Veiculo novoVeiculo = new Veiculo("ABC-000" + i, "Modelo " + i, "Marca " + i, "Preto", 2020 + i, novoCliente.getCpf());
            Servico servicoRealizado = Servico.values()[random.nextInt(Servico.values().length)];
            OrdemServico os = null;
            try {
                 os = new OrdemServico.OrdemServicoBuilder()
                    .id(GerenciamentoIDs.proximoID("OrdemServico_Demo"))
                    .cliente(novoCliente).veiculo(novoVeiculo).mecanico(mecanicoResponsavel)
                    .descricaoProblema("Problema " + i).status("Em Atendimento")
                    .adicionaServico(servicoRealizado).build();
            } catch (IOException e) { System.out.println("Erro ao gerar ID para a OS."); }
            
            Produto produtoUtilizado = Estoque.buscarProdutoPorCodigo(101);
            try {
                os.adicionarProduto(produtoUtilizado);
                Estoque.subtrairQuantidadeEstoque(produtoUtilizado, 1);
            } catch(IOException e) { System.out.println("Erro ao dar baixa no estoque."); }
            
            os.setStatus("Concluído e Entregue");
            System.out.printf("   - OS #%d para '%s' finalizada. Total: R$ %.2f\n\n", os.getId(), novoCliente.getNome(), (produtoUtilizado.getPreco() + servicoRealizado.getPreco()));
        }
        
        System.out.println("---------------------------------------------------------");
        System.out.println("Estoque FINAL de 'Vela de Ignição': " + Estoque.buscarProdutoPorCodigo(101).getQuantidadeEmEstoque());
        System.out.println("\n=========================================================");
        System.out.println("            FIM DA DEMONSTRAÇÃO FINAL");
        System.out.println("=========================================================");
    }
    
    // MÉTODOS AUXILIARES PARA CRIAR DADOS DE TESTE
    private static List<Cliente> criarDadosIniciaisClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Ana Silva", "111.111.111-11", "Rua A, 10", "38-1111-1111"));
        clientes.add(new Cliente("Eduardo Costa", "444.444.444-44", "Rua D, 40", "38-4444-4444"));
        clientes.add(new Cliente("Carlos Pereira", "333.333.333-33", "Rua C, 30", "38-3333-3333"));
        clientes.add(new Cliente("Beatriz Lima", "222.222.222-22", "Rua B, 20", "38-2222-2222"));
        Cliente.setListaClientes(clientes);
        return clientes;
    }

    private static void criarDadosIniciaisEstoque() {
        if (Estoque.getListaDeProdutos() == null || Estoque.getListaDeProdutos().isEmpty()) {
            List<Produto> produtos = new ArrayList<>();
            produtos.add(new Produto(101, "Vela de Ignição", 45.50, 50, Produto.CategoriaDoProduto.PEÇA));
            produtos.add(new Produto(102, "Óleo de Motor 1L", 89.90, 30, Produto.CategoriaDoProduto.CONSUMIVEL));
            Estoque.setListaDeProdutos(produtos);
        }
    }
}