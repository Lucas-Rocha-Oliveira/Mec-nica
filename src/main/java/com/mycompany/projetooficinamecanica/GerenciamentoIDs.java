package com.mycompany.projetooficinamecanica;

import java.io.IOException;
import java.util.Map;


public class GerenciamentoIDs {
    private static Map<String, Integer> contadores;

    public static Map<String, Integer> getContadores() {
        return contadores;
    }

    public static void setContadores(Map<String, Integer> contadores) {
        GerenciamentoIDs.contadores = contadores;
    }


    /** Retorna o ID atual dependendo da classe
     * 
     * @param tipo
     * @return id atual de acordo com a classe
     */
    public static int proximoID(String tipo) throws IOException {
        if (!contadores.containsKey(tipo)) {
            contadores.put(tipo, 1); /** Se não houver o tipo, inicializa o contador com 1
             * 
             */
        }
        int idAtual = contadores.get(tipo);
        contadores.put(tipo, idAtual + 1);
        JsonUtil.salvar("data/ContadorID.json", contadores);
        return idAtual;
    }
}
