package com.diegoreyesmo.android.colpas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.diegoreyesmo.android.colpas.enums.Posicion;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static final int UMBRAL_TOUCH_O_DESLIZAR = 30;
    private final String TAG_MAIN = "COLPAS_5423798";
    private Colectivo colectivo;
    private ContadorPasajeros contadorPasajeros;
    private float x1, x2;
    private float y1, y2;
    private float dx, dy;
    private Asiento asientoDestino;
    private Asiento asientoOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empezarAplicacion();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void empezarAplicacion() {
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        colectivo = new Colectivo();
        contadorPasajeros = new ContadorPasajeros();
        colectivo.setContadorPasajeros(contadorPasajeros);
        asociarIdConObjetos();
        setOnTouchListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void asociarIdConObjetos() {
        colectivo.getAsientos().add(new Asiento(Posicion.COPILOTO, (ImageView) findViewById(R.id.copiloto)));
        colectivo.getAsientos().add(new Asiento(Posicion.IZQUIERDA, (ImageView) findViewById(R.id.izquierda)));
        colectivo.getAsientos().add(new Asiento(Posicion.CENTRO, (ImageView) findViewById(R.id.centro)));
        colectivo.getAsientos().add(new Asiento(Posicion.DERECHA, (ImageView) findViewById(R.id.derecha)));
        contadorPasajeros.setImageViewUnidad((ImageView) findViewById(R.id.unidad));
        contadorPasajeros.setImageViewDecena((ImageView) findViewById(R.id.decena));
        contadorPasajeros.setImageViewCentena((ImageView) findViewById(R.id.centena));
    }

    private void setOnTouchListeners() {
        for (Asiento asiento : colectivo.getAsientos()) {
            asiento.getImageViewPasajero().setOnTouchListener(this);
        }
        for (ImageView contador : contadorPasajeros.getViews()) {
            contador.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        asientoOrigen = obtenerAsiento((ImageView) v);
        if (asientoOrigen != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    x1 = event.getX();
                    y1 = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    x2 = event.getX();
                    y2 = event.getY();
                    if (esTouch()) {
                        eventoTouch();
                    } else {
                        eventoDeslizar();
                    }
                    return true;
                }
            }
        } else if (esContadorView((ImageView) v)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.mensaje_contador_reset)
                    .setPositiveButton(R.string.mensaje_reiniciar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            contadorPasajeros.reset();
                        }
                    })
                    .setNegativeButton(R.string.mensaje_cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.create().show();
        }
        return false;
    }

    private boolean esContadorView(ImageView v) {
        return contadorPasajeros != null && (contadorPasajeros.getImageViewUnidad().equals(v) ||
                contadorPasajeros.getImageViewDecena().equals(v) ||
                contadorPasajeros.getImageViewCentena().equals(v));
    }

    private boolean esTouch() {
        dx = Math.abs(x1 - x2);
        dy = Math.abs(y1 - y2);
        double distancia = Math.hypot(dx, dy);
        return distancia < UMBRAL_TOUCH_O_DESLIZAR;
    }

    private void eventoTouch() {
        if (asientoOrigen == null) {
            return;
        }
        asientoOrigen.cambiarASiguienteEstado();
        switch (asientoOrigen.getEstado()) {
            case PENDIENTE:
                colectivo.subePasajero();
                break;
            case PAGADO:
                colectivo.pasajeroPagaPasaje();
        }
    }

    private void eventoDeslizar() {
        if (dx > dy) {
            movimientoHorizontal();
        } else {
            movimientoVertical();
        }
    }

    private void movimientoHorizontal() {
        if (x1 < x2) {
            switch (asientoOrigen.getPosicion()) {
                case IZQUIERDA:
                    asientoDestino = obtenerAsiento(Posicion.CENTRO);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case CENTRO:
                    asientoDestino = obtenerAsiento(Posicion.DERECHA);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case DERECHA:
                    asientoDestino = obtenerAsiento(Posicion.IZQUIERDA);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
            }
        } else {
            switch (asientoOrigen.getPosicion()) {
                case IZQUIERDA:
                    asientoDestino = obtenerAsiento(Posicion.DERECHA);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case CENTRO:
                    asientoDestino = obtenerAsiento(Posicion.IZQUIERDA);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case DERECHA:
                    asientoDestino = obtenerAsiento(Posicion.CENTRO);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
            }
        }

    }

    private void movimientoVertical() {
        if (y1 < y2) {
            switch (asientoOrigen.getPosicion()) {
                case COPILOTO:
                    asientoDestino = obtenerAsiento(Posicion.DERECHA);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
            }
        } else {
            switch (asientoOrigen.getPosicion()) {
                case IZQUIERDA:
                    asientoDestino = obtenerAsiento(Posicion.COPILOTO);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case CENTRO:
                    asientoDestino = obtenerAsiento(Posicion.COPILOTO);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
                case DERECHA:
                    asientoDestino = obtenerAsiento(Posicion.COPILOTO);
                    asientoOrigen.intercambiarConAsiento(asientoDestino);
                    break;
            }
        }
    }


    private Asiento obtenerAsiento(Posicion posicion) {
        for (Asiento asiento : colectivo.getAsientos()) {
            if (posicion.equals(asiento.getPosicion())) {
                return asiento;
            }
        }
        return null;
    }

    private Asiento obtenerAsiento(ImageView button) {
        for (Asiento asiento : colectivo.getAsientos()) {
            if (button.equals(asiento.getImageViewPasajero())) {
                return asiento;
            }
        }
        return null;
    }
}

