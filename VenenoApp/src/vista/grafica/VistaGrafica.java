package vista.grafica;

import modelo.baraja.ICarta;
import modelo.baraja.IPilaPalo;
import modelo.baraja.Palo;
import modelo.jugador.IJugador;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VistaGrafica{

    private VistaInicio pantallaMenu;
    private JFrame pantallaJuego;
    private IJugador jugador;
    private JPanel panelPrincipal;
    private JPanel pilasEnMesa;
    private JPanel pilas;
    private JPanel pilaBasto;
    private JPanel pilaOro;
    private JPanel pilaEspada;
    private JPanel cartasEnMano;
    private JPanel mano;
    private JPanel[] cartas = new JPanel[4];
    private JPanel vistaCarta;
    private Map<Palo,JLabel> acumulado = new HashMap<>();
    private JLabel sumOro = new JLabel("Acumulado: 0");
    private JLabel sumBasto = new JLabel("Acumulado: 0");
    private JLabel sumEspada = new JLabel("Acumulado: 0");
    private Map<Palo,JLabel> levante = new HashMap<>();
    private JLabel levanteOro = new JLabel("Puntos a levantar: 0");
    private JLabel levanteBasto = new JLabel("Puntos a levantar: 0");
    private JLabel levanteEspada = new JLabel("Puntos a levantar: 0");
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


    public VistaGrafica(IJugador jugador, VistaInicio pantallaMenu) {
        this.pantallaMenu = pantallaMenu;
        this.jugador = jugador;

        pantallaJuego = new JFrame("Veneno - Juego de cartas");
        pantallaJuego.setSize(850, 680);
        pantallaJuego.setLocationRelativeTo(null);
        pantallaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPrincipal = (JPanel) pantallaJuego.getContentPane();
        panelPrincipal.setLayout(new BorderLayout());

        pilasEnMesa();
        cartasEnMano();
        panelesJugadores();

        pantallaJuego.setVisible(true);
    }


    public JFrame getPantallaJuego() {
        return pantallaJuego;
    }


    /*--------------------- Metodos de la clase ---------------------*/


    /**
     * Baja logica de la carta tirada.
     * @param cartaJugada
     */
    public void tirarCarta(ICarta cartaJugada) {
        cartas[cartaJugada.getId()].setVisible(false);

    }


    /**
     * Agrega la carta tirada a la pila correspondiente de la mesa y suma el valor acumulado.
     * @param pila
     */
    public void agregarCartaEnMesa(IPilaPalo pila) {

        System.out.println("vista > pila a actualizar: " + pila.getPalo());

        acumulado.get(pila.getPalo()).setText("Acumulado: " + pila.getSumaValores());
        levante.get(pila.getPalo()).setText("Puntos a levantar: " + pila.getLevante());

    }


    /** Reinicia el valor de la pila de la mesa cuando un jugador debe levantar las cartas.
     *
     * @param pilaAReiniciar
     */
    public void reiniciarPila(IPilaPalo pilaAReiniciar) {

        System.out.println("vista > pila a reiniciar: " + pilaAReiniciar);

        acumulado.get(pilaAReiniciar.getPalo()).setText("Acumulado: 0");
        levante.get(pilaAReiniciar.getPalo()).setText("Puntos a levantar: 0");

    }


    /**
     * Informa al jugador que debe levantar las cartas de la mesa y si suma puntos o no.
     * @param puntos
     */
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



    /* ------------- Funciones para crear o modificar componentes de la ventana ------------ */


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
        pilaBasto.setPreferredSize(new Dimension(130,180));
        JLabel basto = new JLabel("Basto");
        pilaBasto.add(basto);
        pilaBasto.add(sumBasto);
        pilaBasto.add(levanteBasto);

        pilaOro = new JPanel(new FlowLayout());
        pilaOro.setBorder(new LineBorder(Color.DARK_GRAY,5));
        pilaOro.setPreferredSize(new Dimension(130,180));
        JLabel oro = new JLabel("Oro   ");
        pilaOro.add(oro);
        pilaOro.add(sumOro);
        pilaOro.add(levanteOro);

        pilaEspada = new JPanel(new FlowLayout());
        pilaEspada.setBorder(new LineBorder(Color.DARK_GRAY,5));
        pilaEspada.setPreferredSize(new Dimension(130,180));
        JLabel espada = new JLabel("Espada");
        pilaEspada.add(espada);
        pilaEspada.add(sumEspada);
        pilaEspada.add(levanteEspada);

        pilas.add(pilaBasto);
        pilas.add(pilaOro);
        pilas.add(pilaEspada);

        acumulado.put(Palo.BASTO,sumBasto);
        acumulado.put(Palo.ORO,sumOro);
        acumulado.put(Palo.ESPADA,sumEspada);
        levante.put(Palo.BASTO,levanteBasto);
        levante.put(Palo.ORO,levanteOro);
        levante.put(Palo.ESPADA,levanteEspada);
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

        panelInfoJugador.add(new JLabel("Jugador: " + jugador.getNombre()));
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
     * Crea las vistas de las cartas del jugador.
     * @param cartasmano
     */
    public void generarCartas(ArrayList<ICarta> cartasmano) {

        for (ICarta c : cartasmano) {

            vistaCarta = new JPanel(new GridLayout(3,1));
            vistaCarta.setBorder(new LineBorder(Color.BLACK, 3));
            vistaCarta.setPreferredSize(new Dimension(100,150));

            nroCartaSup = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vistaCarta.add(nroCartaSup);
            paloCarta = new JPanel(new FlowLayout(FlowLayout.CENTER));
            vistaCarta.add(paloCarta);
            nroCartaInf = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            vistaCarta.add(nroCartaInf);

            nroCarta1 = new JLabel("" + c.getNro());
            nroCartaSup.add(nroCarta1);
            nroCarta2 = new JLabel("" + c.getNro());
            nroCartaInf.add(nroCarta2);
            panelPalo = new JLabel(c.getPalo().toString());
            paloCarta.add(panelPalo);

            vistaCarta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    pantallaMenu.getControlador().tirarCarta(c);

                }
            });

            //Agrego una carta a la mano. desde el controlador se indica que se generen todas.
            cartasEnMano.add(vistaCarta);
            //Guardo la vista de la carta en un array para poder acceder al elemento despues.
            cartas[cartasmano.indexOf(c)] = vistaCarta;

        }

    }


    /**
     * Reinicia la mano de cartas del jugador al inicio de cada ronda.
     */
    public void reiniciarMano() {

        cartasEnMano.removeAll();

    }

    public void restablecerSesion() {

//        reiniciarPila("BASTO");
//        reiniciarPila("ORO");
//        reiniciarPila("ESPADA");
        puntos = 0;
        panelPuntos.setText("Puntos: " + puntos);
        pantallaJuego.repaint();

    }
}
