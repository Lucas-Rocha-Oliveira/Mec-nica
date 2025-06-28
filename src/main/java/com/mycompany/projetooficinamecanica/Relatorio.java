/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/* Atributos da classe:
lista de produtos 
lista de serviços 
local date tima

Métodos da classe:
adiociomar produto
adicionar serviço 
gerar relatorio
*/

public class Relatorio {

    private List<Produto> listaDeProdutosVendidos = new ArrayList<>();
    private List<Servico> listaDeServicosRealizados = new ArrayList<>();
    private static List<Relatorio> listaDeRelatorios = new ArrayList<>();

    private LocalDate dataRelatorio;

    public LocalDate getDataRelatorio() {
        return dataRelatorio;
    }

    public static List<Relatorio> getListaDeRelatorios() {
        return listaDeRelatorios;
    }

    public static void setListaDeRelatorios(List<Relatorio> listaDeRelatorios) {
        Relatorio.listaDeRelatorios = listaDeRelatorios;
    }

    public void setDataRelatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public Relatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public static void adicionarVendaProduto(Produto produto) throws IOException {
        Relatorio relatorioHoje = getRelatorioDeHoje(LocalDate.now());
        relatorioHoje.listaDeProdutosVendidos.add(produto);
        listaDeRelatorios.add(relatorioHoje);
        JsonUtil.salvar("data/Relatorios.json", listaDeRelatorios);
    }

    public static void adicionarServicoRealizado(Servico servico) throws IOException {
        Relatorio relatorioHoje = getRelatorioDeHoje(LocalDate.now());
        relatorioHoje.listaDeServicosRealizados.add(servico);
        JsonUtil.salvar("data/Relatorios.json", listaDeRelatorios);
    }

    public static Relatorio getRelatorioDeHoje(LocalDate data) {
        for (Relatorio r : listaDeRelatorios) {
            if (r.getDataRelatorio().isEqual(data)) {
                return r;
            }
        }

        Relatorio novoRelatorio = new Relatorio(LocalDateTime.now().toLocalDate());
        return novoRelatorio;
    }

    public static void getRelatorioMes(int mes, int ano) {
        Month mesEscolhido = Month.of(mes);
        List<Relatorio> relatoriosDoMes = new ArrayList<>();

        for (Relatorio r : listaDeRelatorios) {
            if (r.getDataRelatorio().getMonth() == mesEscolhido && r.getDataRelatorio().getYear() == ano) {
                relatoriosDoMes.add(r);
            }
        }

        if (relatoriosDoMes.isEmpty()) {
            System.out.println("\nNão houve relatórios para " + mesEscolhido.toString() + " de " + ano + ".");
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("              RELATÓRIO MENSAL");
        System.out.println("              Mês/Ano: " + String.format("%02d", mes) + "/" + ano);
        System.out.println("==============================================");

        
        for (Relatorio relatorioDiario : relatoriosDoMes) {
            System.out.println(); 
            System.out.println(relatorioDiario.toString());
        }

        System.out.println("\n\n==============================================");
        System.out.println("          FIM DO RELATÓRIO MENSAL");
        System.out.println("==============================================");
    }

    public static Relatorio getRelatorioDiario(LocalDate data) {

        for (Relatorio r : listaDeRelatorios) {
            if (r.getDataRelatorio() == data) {
                return r;
            }
        }

        return null;
    }

    public String listarServicosRealizados() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Serviços Realizados ---");

        if (listaDeServicosRealizados.isEmpty()) {
            sb.append("\nNáo houve serviço nesse dia.");
        } else {
            for (Servico s : listaDeServicosRealizados) {
                sb.append("\n- ").append(s);
            }
        }

        return sb.toString();

    }

    public String listarProdutosVendidos() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Produtos Vendidos ---");

        if (listaDeProdutosVendidos.isEmpty()) {
            sb.append("\nNáo houve venda de produto nesse dia.");
        } else {
            for (Produto p : listaDeProdutosVendidos) {
                sb.append("\n- ").append(p);
            }
        }

        return sb.toString();

    }

    @Override
    public String toString() {
        // CORREÇÃO: Remova a parte de hora (HH:mm) do padrão de formatação.
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder relatorioCompleto = new StringBuilder();

        relatorioCompleto.append("=====================================\n");
        relatorioCompleto.append("      RELATÓRIO REFERENTE AO DIA\n");
        relatorioCompleto.append("=====================================\n");
        relatorioCompleto.append("Data: ").append(getDataRelatorio().format(formatador)).append("\n");

        relatorioCompleto.append(listarServicosRealizados());
        relatorioCompleto.append("\n");
        relatorioCompleto.append(listarProdutosVendidos());

        relatorioCompleto.append("\n=====================================");

        return relatorioCompleto.toString();
    }

}
