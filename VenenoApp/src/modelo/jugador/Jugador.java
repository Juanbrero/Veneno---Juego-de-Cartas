package modelo.jugador;

import modelo.baraja.Carta;
import modelo.baraja.ICarta;

import java.util.ArrayList;

public class Jugador implements IJugador {

    private String nombre;
    private int id;
    private int puntosTotales = 0;
    private int puntosALevantar = 0;
    private boolean miTurno = false;
    private ArrayList<ICarta> cartasEnMano = new ArrayList<>();
    private int partidasJugadas = 0;
    private double ratio;

    public Jugador(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    @Override
    public ArrayList<ICarta> getCartasEnMano() {
        return cartasEnMano;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPuntos() {
        return puntosTotales;
    }

    @Override
    public int getPuntosALevantar() {
        return puntosALevantar;
    }

    public void setPuntosALevantar(int puntosALevantar) {
        this.puntosALevantar = puntosALevantar;
    }

    @Override
    public boolean isMiTurno() {
        return miTurno;
    }

    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }


    public void recibirCartas(ArrayList<ICarta> cartas) {
        this.cartasEnMano = cartas;
        for (ICarta carta : cartasEnMano) {
            carta.setEnMano(true);
        }
    }

    public void tirarCarta(ICarta carta) {

//        for (int i = 0; i < cartasEnMano.size(); i++) {

//            if (cartasEnMano.get(i).getNro() == carta.getNro() && cartasEnMano.get(i).getPalo() == carta.getPalo()) {
//
//                cartasEnMano.get(i).setEnMano(false);
//                cartasEnMano.set(i,null);
//            }

            cartasEnMano.get(carta.getId()).setEnMano(false);
            cartasEnMano.set(carta.getId(), null);

//        }

    }

    public void sumarPuntos(int puntos) {

        this.puntosTotales += puntos;
        this.puntosALevantar = puntos;

    }

}
