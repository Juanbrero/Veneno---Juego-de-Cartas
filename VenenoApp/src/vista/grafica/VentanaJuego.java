package vista.grafica;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego {

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

    public VentanaJuego(String nombreJugador) {

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


    /* ------------- Funciones para crear componentes de la ventana ------------ */

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
//                seleccionarPila(indice, palo);
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

    public void reiniciarMano() {

        cartasEnMano.removeAll();

    }


}
