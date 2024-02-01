package modelo.jugador;

import modelo.baraja.Carta;

import java.io.Serializable;
import java.util.ArrayList;

public interface IJugador extends Serializable {

    double getRatio();

    int getPartidasJugadas();

    ArrayList<Carta> getCartasEnMano();

    String getNombre();

    int getId();

    int getPuntos();

    int getPuntosALevantar();

    boolean isMiTurno();
}
