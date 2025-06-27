/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Lucas
 */
public class Utilidades {
    public static String getDataEHoraAtuaisFormatadas(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); 
    }
    
    public static String getDataAtualFormatada(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
    }
    
    public static LocalDateTime gerarDataHoraPorString(String data) throws DateTimeParseException {
        
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        return LocalDateTime.parse(data, formatador);
        
    }
    
    public static LocalDate gerarDataPorString(String data) throws Exception {
        
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try {
            return LocalDate.parse(data, formatador);
        } catch (DateTimeParseException e) {
            throw new Exception("O formato da data '" + data + "' é inválido. Use dd/MM/yyyy.", e);
        }
    }
    
}
