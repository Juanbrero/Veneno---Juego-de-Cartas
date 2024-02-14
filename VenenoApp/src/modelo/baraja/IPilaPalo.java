package modelo.baraja;

import java.io.Serializable;
import java.util.ArrayList;

public interface IPilaPalo extends Serializable {

    Palo getPalo();

    double getSumaValores();

    int getLevante();

    boolean isReiniciar();

    ArrayList<ICarta> getCartasEnMesa();
}
