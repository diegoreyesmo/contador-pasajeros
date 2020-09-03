package com.diegoreyesmo.android.colpas;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

public class ContadorPasajeros {
    private ImageView imageViewUnidad;
    private ImageView imageViewDecena;
    private ImageView imageViewCentena;
    private int contadorPasajeros = 0;

    public void incrementar() {
        contadorPasajeros++;
        if (contadorPasajeros > 999) contadorPasajeros = 1;
        pintar();
    }

    public void reset() {
        contadorPasajeros = 0;
        pintar();
    }

    private void pintar() {
        try {
            String numero = String.format("%03d", contadorPasajeros);
            imageViewUnidad.setImageResource(obtenerDigito(numero.charAt(2)));
            imageViewDecena.setImageResource(obtenerDigito(numero.charAt(1)));
            imageViewCentena.setImageResource(obtenerDigito(numero.charAt(0)));
        } catch (Exception e) {

        }
    }

    private int obtenerDigito(char numero) {
        switch (numero) {
            case '0':
                return R.drawable.d0;
            case '1':
                return R.drawable.d1;
            case '2':
                return R.drawable.d2;
            case '3':
                return R.drawable.d3;
            case '4':
                return R.drawable.d4;
            case '5':
                return R.drawable.d5;
            case '6':
                return R.drawable.d6;
            case '7':
                return R.drawable.d7;
            case '8':
                return R.drawable.d8;
            case '9':
                return R.drawable.d9;
            default:
                return 0;
        }
    }

    public ImageView getImageViewUnidad() {
        return imageViewUnidad;
    }

    public void setImageViewUnidad(ImageView imageViewUnidad) {
        this.imageViewUnidad = imageViewUnidad;
    }

    public ImageView getImageViewDecena() {
        return imageViewDecena;
    }

    public void setImageViewDecena(ImageView imageViewDecena) {
        this.imageViewDecena = imageViewDecena;
    }

    public ImageView getImageViewCentena() {
        return imageViewCentena;
    }

    public void setImageViewCentena(ImageView imageViewCentena) {
        this.imageViewCentena = imageViewCentena;
    }

    public List<ImageView> getViews() {
        return Arrays.asList(imageViewUnidad, imageViewDecena, imageViewCentena);
    }

}
