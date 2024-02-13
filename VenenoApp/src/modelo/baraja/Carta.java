package modelo.baraja;


public class Carta implements ICarta {

    private Palo palo;
    private int nro;
    private boolean enMazo;
    private boolean enMano;
    private boolean enMesa;
    private double valor;
    private int id;

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


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Palo getPalo() {
        return palo;
    }

    private void setPalo(Palo palo) {
        this.palo = palo;
    }

    @Override
    public int getNro() {
        return nro;
    }

    private void setNro(int nro) {
        this.nro = nro;
    }

    @Override
    public boolean isEnMazo() {
        return enMazo;
    }

    public void setEnMazo(boolean enMazo) {
        this.enMazo = enMazo;
    }

    @Override
    public boolean isEnMano() {
        return enMano;
    }

    public void setEnMano(boolean enMano) {
        this.enMano = enMano;
    }

    @Override
    public boolean isEnMesa() {
        return enMesa;
    }

    public void setEnMesa(boolean enMesa) {
        this.enMesa = enMesa;
    }

    @Override
    public double getValor() {
        return valor;
    }

    private void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean isCopa() {

        boolean esCopa = false;

        if (this.getPalo().equals(Palo.COPA)) {
            esCopa = true;
        }
        return esCopa;
    }

    @Override
    public String toString() {
        return "[" + nro + " " + palo + "]";
    }


}