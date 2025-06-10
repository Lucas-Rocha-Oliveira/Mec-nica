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
public class Utilidades {
    public static String getDataEHoraAtuaisFormatadas(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); 
    }
    
    public static String getDataAtualFormatada(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
    }
}
