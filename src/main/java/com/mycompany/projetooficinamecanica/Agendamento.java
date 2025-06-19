/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author Lucas
 */
public class Agendamento {
    private int id;
    private OrdemServico ordemServico;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private int idElevador;
    
    public Agendamento(OrdemServico ordemServico, LocalDateTime dataHoraInicio, int idElevador) throws IOException{
        this.id = GerenciamentoIDs.proximoID("Agendamento");
        this.ordemServico = ordemServico;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = this.dataHoraInicio.plusMinutes(getDuracaoTotal(this.ordemServico));
        this.idElevador = idElevador;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }
    
    public static int getDuracaoTotal(OrdemServico ordemServico){
        int duracaoTotal = 0;
        for(Servico s : ordemServico.getServicosRealizados()){
            duracaoTotal += s.getDuracaoEstimadaEmMinutos();
        }
        return duracaoTotal;
    }
}
