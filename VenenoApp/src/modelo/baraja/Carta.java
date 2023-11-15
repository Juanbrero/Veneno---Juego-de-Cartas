package modelo.baraja;

import java.io.Serializable;

public class Carta implements Serializable {

    private Palo palo;
    private int nro;
    private boolean enMazo;
    private boolean enMano;
    private boolean enMesa;
    private double valor;

    public Carta(Palo palo, int nro) {
        this.setPalo(palo);
        this.setNro(nro);
        this.setEnMazo(false);
        this.setEnMano(false);
        this.setEnMesa(false);
        if (this.getNro() == 10 || this.getNro() == 11 || this.getNro() == 12) {

            this.setValor(0.5);
        } else {
            this.setValor(nro);
        }
    }

    public Palo getPalo() {
        return palo;
    }

    public void setPalo(Palo palo) {
        this.palo = palo;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public boolean isEnMazo() {
        return enMazo;
    }

    public void setEnMazo(boolean enMazo) {
        this.enMazo = enMazo;
    }

    public boolean isEnMano() {
        return enMano;
    }

    public void setEnMano(boolean enMano) {
        this.enMano = enMano;
    }

    public boolean isEnMesa() {
        return enMesa;
    }

    public void setEnMesa(boolean enMesa) {
        this.enMesa = enMesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isCopa() {

        boolean esCopa = false;

        if (this.getPalo().equals(Palo.COPA)) {
            esCopa = true;
        }
        return esCopa;
    }

}