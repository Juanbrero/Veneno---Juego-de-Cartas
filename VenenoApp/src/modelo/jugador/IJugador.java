package modelo.jugador;

import modelo.baraja.ICarta;

import java.io.Serializable;
import java.util.ArrayList;

public interface IJugador extends Serializable {

    double getRatio();

    int getPartidasJugadas();

    ArrayList<ICarta> getCartasEnMano();

    String getNombre();

    int getId();

    int getPuntos();

    int getPuntosALevantar();

    boolean isMiTurno();
}
