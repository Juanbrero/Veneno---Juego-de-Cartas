package modelo.baraja;

import java.io.Serializable;

public interface ICarta extends Serializable {

    Palo getPalo();

    int getNro();

    void setId(int id);

    int getId();

    boolean isEnMazo();

    void setEnMazo(boolean c);

    boolean isEnMano();

    void setEnMano(boolean c);

    boolean isEnMesa();

    void setEnMesa(boolean c);

    double getValor();

    boolean isCopa();

}
