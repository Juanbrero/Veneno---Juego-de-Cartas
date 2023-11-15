package vista.grafica;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego {

    private VentanaGrafica pantallaMenu;
    private JFrame pantallaJuego;
    private String nombre;
    private JPanel panelPrincipal;
    private JPanel pilasEnMesa;
    private JPanel pilas;
    private JPanel pilaBasto;
    private JPanel pilaOro;
    private JPanel pilaEspada;
    private JPanel cartasEnMano;
    private JPanel mano;
    private JButton[] cartas = new JButton[4];
    private JPanel vistaCarta;
    private JButton botonCarta;
    private double sumaValorPilaOro = 0;
    private double sumaValorPilaBasto = 0;
    private double sumaValorPilaEspada = 0;
    private JLabel sumOro = new JLabel("Acumulado: 0");
    private JLabel sumBasto = new JLabel("Acumulado: 0");
    private JLabel sumEspada = new JLabel("Acumulado: 0");
    private JPanel nroCartaSup;
    private JPanel paloCarta;
    private JPanel nroCartaInf;
    private JLabel nroCarta1;
    private JLabel nroCarta2;
    private JLabel panelPalo;
    private JPanel panelInfoJugador;
    private int puntos = 0;
    private JLabel panelPuntos = new JLabel("Puntos: " + puntos);
    private JPanel espacioJ2;
    private JPanel espacioJ3;
    private JPanel espacioJ4;
    private JLabel jugador2;
    private JLabel jugador3;
    private JLabel jugador4;


    public VentanaJuego(String nombreJugador, VentanaGrafica pantallaMenu) {
        this.pantallaMenu = pantallaMenu;
        this.nombre = nombreJugador;

        pantallaJuego = new JFrame("Veneno - Juego de cartas");
        pantallaJuego.setSize(850, 680);
        pantallaJuego.setLocationRelativeTo(null);
        pantallaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelPrincipal = (JPanel) pantallaJuego.getContentPane();
        panelPrincipal.setLayout(new BorderLayout());

        pilasEnMesa();
        cartasEnMano();
        panelesJugadores();

        pantallaJuego.setVisible(true);
    }


    /**
     * Informa al controlador de la carta a tirar. Verifica si la carta es de copa, en dicho caso pide seleccionar
     * la pila de cartas a envenenar.
     * @param indice
     * @param palo
     */
    public void seleccionarPila(int indice, String palo) {

        if (palo.equals("COPA")) {

            JPanel veneno = new JPanel(new GridLayout(2,1));
            JLabel mensaje = new JLabel("Veneno! - Selecciona el pilon de cartas que queres envenenar");
            JPanel opciones = new JPanel(new FlowLayout());

            veneno.add(mensaje);
            veneno.add(opciones);

            JButton oro = new JButton("Oro");
            opciones.add(oro);
            oro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pantallaMenu.getControlador().tirarCarta(indice,"ORO");
                }
            });

            JButton basto = new JButton("Basto");
            opciones.add(basto);
            basto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pantallaMenu.getControlador().tirarCarta(indice, "BASTO");
                }
            });

            JButton espada = new JButton("Espada");
            opciones.add(espada);
            espada.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pantallaMenu.getControlador().tirarCarta(indice,"ESPADA");
                }
            });


            pantallaMenu.nuevoDialogo(veneno);

        }
        else {
            pantallaMenu.getControlador().tirarCarta(indice, palo);
        }
    }

    /**
     * Baja logica de la carta tirada.
     * @param cartaJugada
     */
    public void tirarCarta(int cartaJugada) {
        cartas[cartaJugada].setVisible(false);

    }

    /**
     * Agrega la carta tirada a la pila correspondiente de la mesa y suma el valor acumulado.
     * @param palo
     * @param valor
     */
    public void agregarCartaEnMesa(String palo, double valor) {

        switch (palo) {
            case "ORO" -> {
                sumaValorPilaOro += valor;
                sumOro.setText("Acumulado: " + sumaValorPilaOro);
            }
            case "BASTO" -> {
                sumaValorPilaBasto += valor;
                sumBasto.setText("Acumulado: " + sumaValorPilaBasto);
            }
            case "ESPADA" -> {
                sumaValorPilaEspada += valor;
                sumEspada.setText("Acumulado: " + sumaValorPilaEspada);
            }
        }
    }

    public void reiniciarPila(String pilaAReiniciar) {
        if(pilaAReiniciar.equals("ORO")) {
            sumaValorPilaOro = 0;
            sumOro.setText("Acumulado: 0");

        }
        else if (pilaAReiniciar.equals("BASTO")) {
            sumaValorPilaBasto = 0;
            sumBasto.setText("Acumulado: 0");

        }
        else {
            sumaValorPilaEspada = 0;
            sumEspada.setText("Acumulado: 0");

        }
    }

    public void levantarCartas(int puntos) {
        if (puntos > 0) {

            pantallaMenu.mostrarMensaje("Envenenado! sumas " + puntos + " puntos.");
            this.puntos += puntos;
            panelPuntos.setText("Puntos: " + this.puntos);
        }
        else {
            pantallaMenu.mostrarMensaje("Uuuf, que cerca! No sumaste puntos! :)");
        }
    }






    /* ------------- Funciones para crear componentes de la ventana ------------ */

    /**
     * Crea las pilas de cartas de la mesa.
     */
    private void pilasEnMesa() {

        pilasEnMesa = new JPanel(new FlowLayout());
        pilasEnMesa.setBorder(new LineBorder(Color.RED,5));
        pilas = new JPanel(new GridLayout(1, 3, 20, 50));

        pilasEnMesa.add(pilas);
        panelPrincipal.add(pilasEnMesa,BorderLayout.CENTER);

        pilaBasto = new JPanel(new FlowLayout());
        pilaBasto.setBorder(new LineBorder(Color.DARK_GRAY,5));
        pilaBasto.setPreferredSize(new Dimension(120,170));
        JLabel basto = new JLabel("Basto");
        pilaBasto.add(basto);
        pilaBasto.add(sumBasto);

        pilaOro = new JPanel(new FlowLayout());
        pilaOro.setBorder(new LineBorder(Color.DARK_GRAY,5));
        pilaOro.setPreferredSize(new Dimension(100,150));
        JLabel oro = new JLabel("Oro");
        pilaOro.add(oro);
        pilaOro.add(sumOro);

        pilaEspada = new JPanel(new FlowLayout());
        pilaEspada.setBorder(new LineBorder(Color.DARK_GRAY,5));
        pilaEspada.setPreferredSize(new Dimension(100,150));
        JLabel espada = new JLabel("Espada");
        pilaEspada.add(espada);
        pilaEspada.add(sumEspada);

        pilas.add(pilaBasto);
        pilas.add(pilaOro);
        pilas.add(pilaEspada);
    }

    /**
     * Crea la mano de cartas del jugador.
     */
    private void cartasEnMano() {

        mano = new JPanel(new FlowLayout());
        panelPrincipal.add(mano, BorderLayout.SOUTH);

        cartasEnMano = new JPanel(new GridLayout(1,4,25,10));
        mano.add(cartasEnMano);

        panelInfoJugador = new JPanel(new GridLayout(3,1,5,12));
        mano.add(panelInfoJugador);

        panelInfoJugador.add(new JLabel("Jugador: " + nombre));
        panelInfoJugador.add(panelPuntos);
    }

    /**
     * Crea los espacios que representan al resto de jugadores.
     */
    private void panelesJugadores() {

        //Uso los bloques del border layout como flow layouts

        //J2
        espacioJ2 = new JPanel(new BorderLayout());
        espacioJ2.setPreferredSize(new Dimension(80,200));
        panelPrincipal.add(espacioJ2, BorderLayout.WEST);
        jugador2 = new JLabel("Jugador 2");
        espacioJ2.add(jugador2, BorderLayout.CENTER);

        //J3
        espacioJ3 = new JPanel(new FlowLayout());
        espacioJ3.setPreferredSize(new Dimension(80,200));
        panelPrincipal.add(espacioJ3, BorderLayout.NORTH);
        jugador3 = new JLabel("Jugador 3");
        espacioJ3.add(jugador3, BorderLayout.CENTER);
        //J4
        espacioJ4 = new JPanel(new BorderLayout());
        espacioJ4.setPreferredSize(new Dimension(80,200));
        panelPrincipal.add(espacioJ4, BorderLayout.EAST);
        jugador4 = new JLabel("Jugador 4");
        espacioJ4.add(jugador4, BorderLayout.CENTER);

        espacioJ2.setBorder(new LineBorder(Color.DARK_GRAY,5));
        espacioJ3.setBorder(new LineBorder(Color.DARK_GRAY,5));
        espacioJ4.setBorder(new LineBorder(Color.DARK_GRAY,5));

    }

    /**
     * Crea la vista de una instancia de carta.
     * @param palo
     * @param numero
     * @param indice
     */
    public void generarCarta(String palo, int numero, int indice) {

        vistaCarta = new JPanel(new GridLayout(3,1));
        vistaCarta.setBorder(new LineBorder(Color.BLACK, 3));
        vistaCarta.setPreferredSize(new Dimension(100,150));

        nroCartaSup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vistaCarta.add(nroCartaSup);
        paloCarta = new JPanel(new FlowLayout(FlowLayout.CENTER));
        vistaCarta.add(paloCarta);
        nroCartaInf = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        vistaCarta.add(nroCartaInf);

        nroCarta1 = new JLabel("" + numero);
        nroCartaSup.add(nroCarta1);
        nroCarta2 = new JLabel("" + numero);
        nroCartaInf.add(nroCarta2);
        panelPalo = new JLabel(palo);
        paloCarta.add(panelPalo);

        botonCarta = new JButton();
        botonCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yo soy la carta" + numero + " " + palo + " del indice " + indice);
                seleccionarPila(indice, palo);
            }
        });
        botonCarta.setPreferredSize(new Dimension(100,150));
        botonCarta.add(vistaCarta);
        botonCarta.setVisible(true);

        //Agrego una carta a la mano. desde el controlador se indica que se generen todas.
        cartasEnMano.add(botonCarta);
        //Guardo el boton de la carta en un array para poder acceder al elemento despues.
        cartas[indice] = botonCarta;

        System.out.println("Se genero la carta " + numero + " " + palo + " en el indice " + indice);

    }

    /**
     * Reinicia la mano de cartas del jugador al inicio de cada ronda.
     */
    public void reiniciarMano() {

        cartasEnMano.removeAll();

    }


}
