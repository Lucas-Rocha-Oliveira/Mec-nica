/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.time.LocalDateTime;
import java.util.Comparator;
/**
 *
 * @author Lucas
 */
public class ComparadorDeAgendamentos implements Comparator<Agendamento>{
    
    @Override
    public int compare(Agendamento agendamento1, Agendamento agendamento2) {
       LocalDateTime data1 = agendamento1.getDataHoraInicio();
       LocalDateTime data2 = agendamento2.getDataHoraInicio();
       
       return data1.compareTo(data2);
    }
    
}
