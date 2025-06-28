/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Lucas
 */
public class TransacaoFinanceira {
    public enum TipoTransacao{
        RECEITA_SERVICO,      // Dinheiro que entrou pela execução de um serviço
        RECEITA_VENDA_PECA,   // Dinheiro que entrou pela venda de uma peça
        RECEITA_CANCELAMENTO, // Dinheiro que entrou por uma taxa de cancelamento
        DESPESA_SALARIO,      // Dinheiro que saiu para pagar funcionários
        DESPESA_COMPRA_PECA,  // Dinheiro que saiu para comprar peças para o estoque
        DESPESA_OUTRAS 
    }

    private double valor;
    private String descricao;
    private LocalDateTime data;
    private TipoTransacao tipo;
    
    public TransacaoFinanceira(double valor, String descricao, LocalDateTime data, TipoTransacao tipo) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
   
    public TipoTransacao getTipo() {
        return tipo;
    }
    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + 
               " | " + tipo + 
               " | " + descricao + 
               " | R$ " + String.format("%.2f", valor);
    }
}
