/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetooficinamecanica;

import java.util.Comparator;

/**
 *
 * @author Lucas
 */
public class ClienteComparator {

    public static class ClienteNomeComparator implements Comparator<Cliente> {

        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getNome().compareToIgnoreCase(c2.getNome());
        }
    }

    public static class ClienteCpfComparator implements Comparator<Cliente> {

        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getCpf().compareTo(c2.getCpf());
        }
    }

}
