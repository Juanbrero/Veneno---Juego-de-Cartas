package modelo.baraja;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {

    private ArrayList<ICarta> cartasEnMazo = new ArrayList<>();
    private static final int MAX_CARTAS = 48;
    private int cantidadCartasEnMazo = 0;

    public Mazo() {

        for (Palo palo : Palo.values()) {

            for (int nroCarta = 1; nroCarta <= 12; nroCarta++) {

                Carta carta = new Carta(palo, nroCarta);
                carta.setEnMazo(true);
                cartasEnMazo.add(carta);

            }
        }
        this.setCantidadCartasEnMazo(MAX_CARTAS);
    }

    public int getCantidadCartasEnMazo() {
        return cantidadCartasEnMazo;
    }

    private void setCantidadCartasEnMazo(int cantidadCartasEnMazo) {
        this.cantidadCartasEnMazo = cantidadCartasEnMazo;
    }

    public void mezclar() {
        Collections.shuffle(cartasEnMazo);
    }

    public ArrayList<ICarta> repartir() {

        int indice = 0;
        ArrayList<ICarta> cartas = new ArrayList<>();
        while (indice < MAX_CARTAS && cantidadCartasEnMazo > 0 && cartas.size() < 4) {

            if (cartasEnMazo.get(indice).isEnMazo()) {

                cartas.add(cartasEnMazo.get(indice));
                cartasEnMazo.get(indice).setId(cartas.size() - 1);
                cartasEnMazo.get(indice).setEnMazo(false);
                this.cantidadCartasEnMazo--;
            }

            indice++;
        }
        return cartas;
    }

    public void juntarCartas() {

        int indice = 0;
        while (cantidadCartasEnMazo < MAX_CARTAS) {
            this.cartasEnMazo.get(indice).setEnMazo(true);
            this.cartasEnMazo.get(indice).setId(0);
            this.cantidadCartasEnMazo++;
            indice++;
        }
    }

}