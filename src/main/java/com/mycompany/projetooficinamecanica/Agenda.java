/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    public static void verAgenda(){
        System.out.println("--- Agenda Oficina ---");
        
        if(compromissos.isEmpty()){
           System.out.println("Nenhum compromisso agendado."); 
        }else{
            for(Agendamento a : compromissos){
                System.out.println(a);
            }
        }
        
        System.out.println("-------------------");
    }
    
    public static boolean verificarDiponibilidade(LocalDateTime dataHoraInicioProposta, int duracaoProposta, int idElevadorProposto){
        LocalDateTime dataHoraFimProposta = dataHoraInicioProposta.plusMinutes(duracaoProposta);
        
        for(Agendamento agendamentoExistente : compromissos){
           
            if(agendamentoExistente.getIdElevador() == idElevadorProposto){
              
                LocalDateTime inicioExixtente = agendamentoExistente.getDataHoraInicio();
                LocalDateTime fimExistente = agendamentoExistente.getDataHoraFim();
                
                boolean conflito = dataHoraInicioProposta.isBefore(fimExistente) && dataHoraFimProposta.isAfter(inicioExixtente);
                
                if(conflito){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void adicionarCompromisso(Agendamento agendamento){
        compromissos.add(agendamento);
    }
}
