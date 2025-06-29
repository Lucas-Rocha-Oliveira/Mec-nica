package com.mycompany.projetooficinamecanica;

public class Elevador {

    public enum TipoElevador {
        SERVICOS_GERAIS,
        ALINHAMENTO_E_BALANCEAMENTO
    }

    private final int id;
    private final TipoElevador tipo;

    public Elevador(int id, TipoElevador tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public TipoElevador getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Elevador ID: " + id + " (Tipo: " + tipo + ")";
    }
}
