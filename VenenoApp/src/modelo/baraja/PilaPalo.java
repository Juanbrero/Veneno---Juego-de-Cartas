package modelo.baraja;


import java.io.Serializable;
import java.util.ArrayList;

public class PilaPalo implements Serializable {

    private static final int SUMA_MINIMA = 13;
    private ArrayList<Carta> cartasEnMesa = new ArrayList<>();
    private Palo palo;
    private double sumaValores = 0;


    public PilaPalo(Palo palo) {
        this.palo = palo;

    }

    public Palo getPalo() {
        return palo;
    }

    public double getSumaValores() {
        return sumaValores;
    }

    private void setSumaValores(double sumaValores) {
        this.sumaValores = sumaValores;
    }

    public static int getSumaMinima() {
        return SUMA_MINIMA;
    }

    public ArrayList<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    public void agregarCarta(Carta carta) {

        carta.setEnMesa(true);
        this.setSumaValores(getSumaValores() + carta.getValor());
        this.cartasEnMesa.add(carta);

    }

    public void reinicarPila() {

        for (int i = 0; i < cartasEnMesa.size(); i++) {
            this.cartasEnMesa.get(i).setEnMesa(false);
        }
        this.cartasEnMesa.clear();
        this.setSumaValores(0);

    }

}