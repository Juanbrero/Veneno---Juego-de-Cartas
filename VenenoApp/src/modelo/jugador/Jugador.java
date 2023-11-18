package modelo.jugador;

import modelo.baraja.Carta;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable {

    private String nombre;
    private int id;
    private int puntos = 0;
    private boolean miTurno = false;
    private ArrayList<Carta> cartasEnMano = new ArrayList<>();

    public Jugador(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
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
        return puntos;
    }

//    public void setPuntos(int puntos) {
//        this.puntos += puntos;
//    }

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

        this.puntos += puntos;

    }

}
