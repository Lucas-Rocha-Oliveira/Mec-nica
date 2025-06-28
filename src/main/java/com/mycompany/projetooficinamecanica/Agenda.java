/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.projetooficinamecanica.TransacaoFinanceira.TipoTransacao;

/**
 *
 * @author Lucas
 */

/**
 * A classe Agenda gerencia todos os compromissos da oficina.
 * Ela funciona como um caderno central de agendamentos.
 */
public class Agenda {
    private static List<Agendamento> compromissos = new ArrayList<>();

    public static void setCompromissos(List<Agendamento> compromissos) {
        Agenda.compromissos = compromissos;
    }

    public static void verAgenda() {
        System.out.println("--- Agenda Oficina ---");

        if (compromissos.isEmpty()) {
            System.out.println("Nenhum compromisso agendado.");
        } else {
            for (Agendamento a : compromissos) {
                System.out.println(a);
            }
        }

        System.out.println("-------------------");
    }

    public static List<Agendamento> getCompromissos() {
        return compromissos;
    }

    public static boolean verificarDisponibilidade(LocalDateTime dataHoraInicioProposta, int duracaoProposta,
            int idElevadorProposto) {
        LocalDateTime dataHoraFimProposta = dataHoraInicioProposta.plusMinutes(duracaoProposta);

        for (Agendamento agendamentoExistente : compromissos) {

            if (agendamentoExistente.getIdElevador() == idElevadorProposto) {

                LocalDateTime inicioExixtente = agendamentoExistente.getDataHoraInicio();
                LocalDateTime fimExistente = agendamentoExistente.getDataHoraFim();

                boolean conflito = dataHoraInicioProposta.isBefore(fimExistente)
                        && dataHoraFimProposta.isAfter(inicioExixtente);

                if (conflito) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void adicionarCompromisso(Agendamento agendamento) throws IOException {
        compromissos.add(agendamento);
        JsonUtil.salvar("data/Agenda.json", compromissos);
    }

    public static Agendamento getCompromissoPorId(int id) {
        for (Agendamento a : compromissos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public static void removerCompromisso(Agendamento agendamento) throws IOException {
        compromissos.remove(agendamento);
        JsonUtil.salvar("data/Agenda.json", compromissos);
    }

    public static void cancelarAgendamento(Agendamento agendamento) throws IOException {
        // 1. Calcula o valor total dos serviços que estavam agendados
        double valorTotalServicos = 0;
        for (Servico s : agendamento.getOrdemServico().getServicosRealizados()) {
            valorTotalServicos += s.getPreco();
        }

        // 2. Calcula a taxa de 20%
        double taxa = valorTotalServicos * 0.20;

        // 3. Cria a TransacaoFinanceira para a taxa
        String descricao = "Taxa de cancelamento da OS #" + agendamento.getOrdemServico().getId();
        TransacaoFinanceira transacaoTaxa = new TransacaoFinanceira(
                taxa,
                descricao,
                LocalDateTime.now(),
                TransacaoFinanceira.TipoTransacao.RECEITA_CANCELAMENTO);

        // 4. A LÓGICA CORRETA: Adiciona a transação ao relatório e salva
        Relatorio relatorioDoDia = Relatorio.getRelatorioPorData(LocalDate.now());
        relatorioDoDia.adicionarTransacao(transacaoTaxa);
        Relatorio.salvarRelatorios(); // Salva as alterações nos relatórios

        // 5. Remove o compromisso da agenda
        removerCompromisso(agendamento); // Este método já salva a lista de agendamentos

        System.out.println("Agendamento #" + agendamento.getId() + " cancelado com sucesso.");
        System.out.printf("Taxa de cancelamento de R$ %.2f registada.\n", taxa);
    }
}
