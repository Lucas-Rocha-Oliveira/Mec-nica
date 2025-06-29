package com.mycompany.projetooficinamecanica;

public class Oficina {

    /**
     * Vetor estático e final para armazenar os 3 elevadores da oficina,
     * conforme requisito do projeto.
     */
    private static final Elevador[] elevadores;

    // Bloco inicializador estático. É executado uma vez quando a classe é carregada.
    static {
        elevadores = new Elevador[3];
        // Conforme o escopo, um elevador é especializado e os outros dois são de uso geral.
        elevadores[0] = new Elevador(1, Elevador.TipoElevador.ALINHAMENTO_E_BALANCEAMENTO);
        elevadores[1] = new Elevador(2, Elevador.TipoElevador.SERVICOS_GERAIS);
        elevadores[2] = new Elevador(3, Elevador.TipoElevador.SERVICOS_GERAIS);
    }

    /**
     * Retorna o vetor de elevadores da oficina.
     * @return um vetor contendo todos os elevadores.
     */
    public static Elevador[] getElevadores() {
        return elevadores;
    }
}