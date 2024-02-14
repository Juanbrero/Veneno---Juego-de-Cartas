package modelo.baraja;

import java.util.ArrayList;

public class PilaPalo implements IPilaPalo {

    private static final int SUMA_MINIMA = 13;
    private ArrayList<ICarta> cartasEnMesa = new ArrayList<>();
    private Palo palo;
    private double sumaValores = 0;
    private boolean reiniciar = false;
    private int levante = 0;


    public PilaPalo(Palo palo) {
        this.palo = palo;

    }

    @Override
    public Palo getPalo() {
        return palo;
    }

    @Override
    public double getSumaValores() {
        return sumaValores;
    }

    private void setSumaValores(double sumaValores) {
        this.sumaValores = sumaValores;
    }

    public static int getSumaMinima() {
        return SUMA_MINIMA;
    }

    public int getLevante() {
        return levante;
    }

    public boolean isReiniciar() {
        return reiniciar;
    }

    public void setReiniciar(boolean reiniciar) {
        this.reiniciar = reiniciar;
    }

    @Override
    public ArrayList<ICarta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    public void agregarCarta(ICarta carta) {

        carta.setEnMesa(true);
        this.setSumaValores(getSumaValores() + carta.getValor());
        this.cartasEnMesa.add(carta);
        if (carta.isCopa()) {
            this.levante++;
        }

    }

    public void reinicarPila() {

        for (int i = 0; i < cartasEnMesa.size(); i++) {
            this.cartasEnMesa.get(i).setEnMesa(false);
        }
        this.cartasEnMesa.clear();
        this.setSumaValores(0);
        this.levante = 0;

    }

}