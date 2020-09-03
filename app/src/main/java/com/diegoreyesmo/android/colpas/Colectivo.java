package com.diegoreyesmo.android.colpas;

import java.util.ArrayList;
import java.util.List;

public class Colectivo {
    private List<Asiento> asientos;
    private ContadorPasajeros contadorPasajeros;

    public Colectivo() {
        asientos = new ArrayList<>(4);
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void subePasajero() {
        contadorPasajeros.incrementar();
    }

    public void pasajeroPagaPasaje() {

    }

    public void setContadorPasajeros(ContadorPasajeros contadorPasajeros) {
        this.contadorPasajeros = contadorPasajeros;
    }
}
