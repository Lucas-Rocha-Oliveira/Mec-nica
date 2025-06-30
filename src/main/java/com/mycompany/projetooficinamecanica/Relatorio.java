package com.mycompany.projetooficinamecanica;

import com.mycompany.projetooficinamecanica.TransacaoFinanceira.TipoTransacao;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeList;

import com.google.gson.reflect.TypeToken;

/**
 * Representa um relatório diário, que contém todas as transações financeiras de
 * um dia.
 */
public class Relatorio {

    private List<TransacaoFinanceira> listaDeTransacoes = new ArrayList<>();
    private LocalDate dataRelatorio;

    private static List<Relatorio> listaDeRelatorios = new ArrayList<>();

    public Relatorio(LocalDate data) {
        this.dataRelatorio = data;
        this.listaDeTransacoes = new ArrayList<>();
    }

    public static Relatorio getRelatorioDeHoje() {
        return getRelatorioPorData(LocalDate.now());
    }

    // Getters e Setters
    public List<TransacaoFinanceira> getListaDeTransacoes() {
        return listaDeTransacoes;
    }

    public LocalDate getDataRelatorio() {
        return dataRelatorio;
    }

    public static List<Relatorio> getListaDeRelatorios() {
        return listaDeRelatorios;
    }

    public static void setListaDeRelatorios(List<Relatorio> lista) {
        if (lista != null) {
            listaDeRelatorios = lista;
        }
    }

    // Método único para adicionar qualquer transação financeira
    public void adicionarTransacao(TransacaoFinanceira transacao) {
        this.listaDeTransacoes.add(transacao);
    }

    /**
     * Encontra o relatório para uma data específica ou cria um novo se não existir.
     * Este é o método principal para garantir que estamos a trabalhar no relatório
     * correto.
     */
    public static Relatorio getRelatorioPorData(LocalDate data) {
        for (Relatorio r : listaDeRelatorios) {
            if (r.getDataRelatorio().isEqual(data)) {
                return r; // Retorna o relatório existente
            }
        }
        // Se não encontrou, cria um novo, adiciona à lista e retorna-o
        Relatorio novoRelatorio = new Relatorio(data);
        listaDeRelatorios.add(novoRelatorio);
        return novoRelatorio;
    }

    public static void getRelatorioMensal(int mes, int ano) {
        List<Relatorio> relatoriosDoMes = new ArrayList<>();
        for(Relatorio r : listaDeRelatorios){
            if(r.getDataRelatorio().getMonthValue() == mes && r.getDataRelatorio().getYear() == ano){
                relatoriosDoMes.add(r);
            }
        }

        double totalReceitas = 0;
        double totalDespesas = 0;

        List<TransacaoFinanceira> servicos = new ArrayList<>();
        List<TransacaoFinanceira> produtos = new ArrayList<>();
        List<TransacaoFinanceira> outras = new ArrayList<>();
        List<TransacaoFinanceira> despesas = new ArrayList<>();
        

         for (Relatorio r : relatoriosDoMes) {
            for (TransacaoFinanceira t : r.getListaDeTransacoes()) {
                
                // Usando switch no Enum diretamente (mais seguro e legível)
                switch (t.getTipo()) {
                    case RECEITA_SERVICO:
                        servicos.add(t);
                        totalReceitas += t.getValor();
                        break;
                    
                    case RECEITA_VENDA_PECA:
                        produtos.add(t);
                        totalReceitas += t.getValor();
                        break;

                    case DESPESA_SALARIO:
                    case DESPESA_COMPRA_PECA:
                    case DESPESA_OUTRAS:
                        despesas.add(t);
                        totalDespesas += t.getValor(); // O valor já é positivo
                        break;
                    
                    // Caso tenha outras transações não previstas
                    default:
                        outras.add(t);
                        // Uma lógica de segurança caso existam outros tipos
                        if (t.getValor() >= 0) {
                            totalReceitas += t.getValor();
                        } else {
                            totalDespesas += Math.abs(t.getValor());
                        }
                        break;
                }
            }
        }

        System.out.println("\n==============================================");
        System.out.printf("              BALANÇO MENSAL\n");
        System.out.printf("              Mês/Ano: %02d/%d\n", mes, ano);
        System.out.println("==============================================");

        System.out.println("\n--- Serviços Realizados ---");
        if (servicos.isEmpty())
            System.out.println("Não houve serviços realizados neste mês.");
        else
            for (TransacaoFinanceira t : servicos)
                System.out.println(t);

        System.out.println("\n--- Produtos Vendidos ---");
        if (produtos.isEmpty())
            System.out.println("Não houve venda de produtos neste mês.");
        else
            for (TransacaoFinanceira t : produtos)
                System.out.println(t);

        System.out.println("\n--- Outras Transações ---");
        if (outras.isEmpty())
            System.out.println("Não houve outras transações neste mês.");
        else
            for (TransacaoFinanceira t : outras)
                System.out.println(t);

        System.out.println("\n==============================================");
        System.out.printf("Total de Receitas no Mês: R$ %.2f\n", totalReceitas);
        System.out.printf("Total de Despesas no Mês: R$ %.2f\n", totalDespesas);
        System.out.println("----------------------------------------------");
        System.out.printf("Resultado Final do Mês:   R$ %.2f\n", totalReceitas - totalDespesas);
        System.out.println((totalReceitas - totalDespesas >= 0) ? "(Lucro)" : "(Prejuízo)");
        System.out.println("==============================================");
    }

    public static List<Relatorio> getRelatoriosPorMes(int mes) {
        List<Relatorio> relatoriosDoMes = new ArrayList<>();
        for (Relatorio r : listaDeRelatorios) {
            if (r.getDataRelatorio().getMonthValue() == mes) {
                relatoriosDoMes.add(r);
            }
        }
        return relatoriosDoMes;
    }

    public static void menuGerarBalancoMensal(int mes) {
        List<Relatorio> relatoriosDoMes = getRelatoriosPorMes(mes);
        double total = 0;

        System.out.println("========== BALANÇO DO MÊS " + mes + " ==========");

        for (Relatorio r : relatoriosDoMes) {
            for (TransacaoFinanceira t : r.getListaDeTransacoes()) {
                System.out.println(t);

                switch (t.getTipo()) {
                    case RECEITA_SERVICO:
                    case RECEITA_VENDA_PECA:
                    case RECEITA_CANCELAMENTO:
                        total += t.getValor();
                        break;

                    case DESPESA_SALARIO:
                    case DESPESA_COMPRA_PECA:
                    case DESPESA_OUTRAS:
                        total -= t.getValor();
                        break;
                }
            }
        }

        System.out.println("==============================================");
        System.out.printf("Balanço do mês: R$ %.2f%n", total);
        System.out.println("==============================================");
    }

    public static StringBuilder listarDespessasPorMes(int mes) {
        StringBuilder sb = new StringBuilder();

        List<Relatorio> relatoriosDoMes = getRelatoriosPorMes(mes);

        for (Relatorio r : relatoriosDoMes) {
            for (TransacaoFinanceira t : r.getListaDeTransacoes()) {
                if (t.getTipo() == TipoTransacao.DESPESA_COMPRA_PECA
                        || t.getTipo() == TipoTransacao.DESPESA_SALARIO
                        || t.getTipo() == TipoTransacao.DESPESA_OUTRAS) {

                    sb.append("\n");
                    sb.append(t);
                }
            }
        }
        sb.append("\n");
        return sb;
    }

    /**
     * O toString agora é inteligente. Ele percorre a lista de transações
     * e constrói as seções do relatório dinamicamente.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append("\n=====================================\n");
        sb.append("      RELATÓRIO DO DIA: ").append(this.dataRelatorio.format(formatador)).append("\n");
        sb.append("=====================================\n");

        // --- Seção de Serviços ---
        sb.append("\n--- Serviços Realizados ---\n");
        long servicosCount = this.listaDeTransacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA_SERVICO)
                .peek(t -> sb.append(t).append("\n"))
                .count();
        if (servicosCount == 0) {
            sb.append("Não houve serviços neste dia.\n");
        }

        // --- Seção de Vendas ---
        sb.append("\n--- Produtos Vendidos ---\n");
        long vendasCount = this.listaDeTransacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA_VENDA_PECA)
                .peek(t -> sb.append(t).append("\n"))
                .count();
        if (vendasCount == 0) {
            sb.append("Não houve venda de produtos neste dia.\n");
        }

        // --- Seção de Outras Transações ---
        sb.append("\n--- Outras Transações ---\n");
        long outrasCount = this.listaDeTransacoes.stream()
                .filter(t -> t.getTipo() != TipoTransacao.RECEITA_SERVICO
                        && t.getTipo() != TipoTransacao.RECEITA_VENDA_PECA)
                .peek(t -> sb.append(t).append("\n"))
                .count();
        if (outrasCount == 0) {
            sb.append("Não houve outras transações neste dia.\n");
        }

        sb.append("\n=====================================");
        return sb.toString();
    }

    // Métodos para carregar e salvar os relatórios
    public static void salvarRelatorios() throws IOException {
        JsonUtil.salvar("data/Relatorios.json", listaDeRelatorios);
    }

    public static void carregarRelatorios() throws IOException {
        Type tipoLista = new TypeToken<List<Relatorio>>() {
        }.getType();
        setListaDeRelatorios(JsonUtil.carregarLista("data/Relatorios.json", tipoLista));
    }
}
