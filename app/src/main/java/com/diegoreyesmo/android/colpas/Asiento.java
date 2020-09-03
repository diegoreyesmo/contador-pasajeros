package com.diegoreyesmo.android.colpas;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.ImageView;

import com.diegoreyesmo.android.colpas.enums.Estado;
import com.diegoreyesmo.android.colpas.enums.Posicion;

@SuppressLint("AppCompatCustomView")
public class Asiento {
    private Posicion posicion;
    private Estado estado;
    private ImageView pasajero;

    public Asiento(Posicion posicion, ImageView pasajero) {
        this.posicion = posicion;
        this.pasajero = pasajero;
        asignarEstado(Estado.LIBRE);
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void asignarEstado(Estado estado) {
        this.estado = estado;
        switch (estado) {
            case LIBRE:
                pasajero.setColorFilter(null);
                break;
            case PENDIENTE:
                pasajero.setColorFilter(Color.argb(100, 255, 0, 0));
                break;
            case PAGADO:
                pasajero.setColorFilter(Color.argb(100, 0, 255, 0));
                break;
        }
    }

    public ImageView getImageViewPasajero() {
        return pasajero;
    }

    public void cambiarASiguienteEstado() {
        switch (estado) {
            case LIBRE:
                estado = Estado.PENDIENTE;
                asignarEstado(estado);
                break;
            case PENDIENTE:
                estado = Estado.PAGADO;
                asignarEstado(estado);
                break;
            case PAGADO:
                estado = Estado.LIBRE;
                asignarEstado(estado);
                break;
        }
    }

    public void intercambiarConAsiento(Asiento asientoDestino) {
        Estado estadoSwap = asientoDestino.getEstado();
        if (estadoSwap.equals(Estado.LIBRE)) {
            asientoDestino.asignarEstado(estado);
            asignarEstado(estadoSwap);
        }
    }
}
