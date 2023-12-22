package modelo.jugador;

import modelo.baraja.Carta;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable {

    private String nombre;
    private int id;
    private int puntosTotales = 0;
    private int puntosALevantar = 0;
    private boolean miTurno = false;
    private ArrayList<Carta> cartasEnMano = new ArrayList<>();
    private int partidasJugadas = 0;
    private double ratio;

    public Jugador(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public ArrayList<Carta> getCartasEnMano() {
        return cartasEnMano;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return this.id;
    }

    public int getPuntos() {
        return puntosTotales;
    }

    public int getPuntosALevantar() {
        return puntosALevantar;
    }

    public void setPuntosALevantar(int puntosALevantar) {
        this.puntosALevantar = puntosALevantar;
    }

    public boolean isMiTurno() {
        return miTurno;
    }

    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }


    public void recibirCartas(ArrayList<Carta> cartas) {
        this.cartasEnMano = cartas;
        for (Carta carta : cartasEnMano) {
            carta.setEnMano(true);
        }
    }

    public void tirarCarta(int indiceCarta) {

        cartasEnMano.get(indiceCarta).setEnMano(false);
        cartasEnMano.set(indiceCarta,null);

    }

    public void sumarPuntos(int puntos) {

        this.puntosTotales += puntos;
        this.puntosALevantar = puntos;

    }

}
