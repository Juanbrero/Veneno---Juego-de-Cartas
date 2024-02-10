package modelo.baraja;

import java.io.Serializable;
import java.util.ArrayList;

public interface IPilaPalo extends Serializable {

    Palo getPalo();

    double getSumaValores();

    boolean isReiniciar();

    ArrayList<ICarta> getCartasEnMesa();
}
