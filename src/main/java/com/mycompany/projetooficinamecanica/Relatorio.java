/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

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

    public void setDataRelatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public Relatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }
    
    
    public static void adicionarVendaProduto(Produto produto){
        Relatorio relatorioHoje = getRelatorioDeHoje(LocalDate.now());
        relatorioHoje.listaDeProdutosVendidos.add(produto);
    }
    
    public static void adicionarServficoRealizado(Servico servico){
        Relatorio relatorioHoje = getRelatorioDeHoje(LocalDate.now());
        relatorioHoje.listaDeServicosRealizados.add(servico);
    }
    
    public static Relatorio getRelatorioDeHoje(LocalDate data){
        for(Relatorio r : listaDeRelatorios){
            if(r.getDataRelatorio().isEqual(data)){
              return r;  
            }
        }
        
        Relatorio novoRelatorio = new Relatorio(LocalDateTime.now().toLocalDate());
        return novoRelatorio;
    }
    
    public static void getRelatorioMes(int mes, int ano){
        Month mesEscolhido = Month.of(mes);
        
        List<Relatorio> ListaRelatoriosMensal = new ArrayList<>();
        
        for(Relatorio r : listaDeRelatorios){
            if(r.getDataRelatorio().getMonth() ==  mesEscolhido){
              ListaRelatoriosMensal.add(r);
            }
        }
        
        if(ListaRelatoriosMensal.isEmpty()){
            System.out.println("Não houve Relatorios nesse mês.");
        }else{
            for(Relatorio r : ListaRelatoriosMensal){
                System.out.println(r.toString());
            }
        }
    }
    
    public static Relatorio getRelatorioDiario(LocalDate data){
        
        for(Relatorio r : listaDeRelatorios){
            if(r.getDataRelatorio() ==  data){
              return r;
            }
        }
        
        return null;
    }
    


    public String listarServicosRealizados(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n--- Serviços Realizados ---");
        
        if(listaDeServicosRealizados.isEmpty()){
            sb.append("\nNáo houve serviço nesse dia.");
        }else{
            for(Servico s: listaDeServicosRealizados){
            sb.append("\n- ").append(s); 
            }
        }
        
        return sb.toString();
        
    }
    
    public String listarProdutosVendidos(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n--- Produtos Vendidos ---");
        
        if(listaDeProdutosVendidos.isEmpty()){
            sb.append("\nNáo houve venda de produto nesse dia.");
        }else{
            for(Produto p: listaDeProdutosVendidos){
            sb.append("\n- ").append(p); 
            }
        }
        
        return sb.toString();
        
    }
    
    
    @Override
    public String toString() {
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
    
    StringBuilder relatorioCompleto = new StringBuilder();
    
    relatorioCompleto.append("=====================================\n");
    relatorioCompleto.append("      RELATÓRIO DO DIA\n");
    relatorioCompleto.append("=====================================\n");
    relatorioCompleto.append("Gerado em: ").append(getDataRelatorio().format(formatador)).append("\n");
    
    relatorioCompleto.append(listarServicosRealizados());
    relatorioCompleto.append("\n"); 
    relatorioCompleto.append(listarProdutosVendidos());
    
    relatorioCompleto.append("\n=====================================");
    
    return relatorioCompleto.toString();
    }
    
    
}
