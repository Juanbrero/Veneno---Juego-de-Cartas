package vista.consola;

import modelo.baraja.Carta;
import vista.IVista;
import vista.VistaGeneral;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class VistaConsola {


    private VistaGeneral pantallaMenu;
    private JFrame pantallaJuego;
    private String nombre;
    private double sumaValorPilaOro = 0;
    private double sumaValorPilaBasto = 0;
    private double sumaValorPilaEspada = 0;
    private int puntos = 0;
    private JPanel panelGral;
    private JPanel panelTexto;
    private JPanel panelComandos;



    public VistaConsola(String nombre, VistaGeneral pantallaMenu) {
        this.nombre = nombre;
        this.pantallaMenu = pantallaMenu;

        pantallaJuego = new JFrame("Veneno - Juego de cartas");
        pantallaJuego.setSize(750, 580);
        pantallaJuego.setLocationRelativeTo(null);
        pantallaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelGral = (JPanel) pantallaJuego.getContentPane();
        panelGral.setLayout(new BorderLayout());

        /* panel de texto de la consola.*/
        panelTexto = new JPanel(new FlowLayout());
        panelTexto.setPreferredSize(new Dimension(750,500));
        JTextArea campoTexto = new JTextArea();
        panelTexto.add(campoTexto,FlowLayout.CENTER);


        /* panel de comandos */
        panelComandos = new JPanel(new GridLayout(1,2));
        JPanel ingresoComando = new JPanel(new FlowLayout());
        ingresoComando.setPreferredSize(new Dimension(700,80));
        JTextField lineaComando = new JTextField();
        ingresoComando.add(lineaComando,FlowLayout.LEFT);

        /* Boton para enviar el comando */
        JPanel enviarComando = new JPanel(new FlowLayout());
        enviarComando.setPreferredSize(new Dimension(50,80));
        JButton enviar = new JButton("Listo");
        enviarComando.add(enviar);


        panelComandos.add(ingresoComando);
        panelComandos.add(enviarComando);

        panelGral.add(panelTexto,BorderLayout.CENTER);
        panelGral.add(panelComandos,BorderLayout.SOUTH);

        pantallaJuego.setVisible(true);

    }


    public JFrame getConsolaJuego() {
        return this.pantallaJuego;
    }


    /**
     * Informa al controlador de la carta a tirar. Verifica si la carta es de copa, en dicho caso pide seleccionar
     * la pila de cartas a envenenar.
     * @param indice
     * @param palo
     */
    public void seleccionarPila(int indice, String palo) {


    }


    /**
     * Baja logica de la carta tirada.
     * @param cartaJugada
     */
    public void tirarCarta(int cartaJugada) {


    }


    /**
     * Agrega la carta tirada a la pila correspondiente de la mesa y suma el valor acumulado.
     * @param palo
     * @param valor
     */
    public void agregarCartaEnMesa(String palo, double valor) {


    }


    /** Reinicia el valor de la pila de la mesa cuando un jugador debe levantar las cartas.
     *
     * @param pilaAReiniciar
     */
    public void reiniciarPila(String pilaAReiniciar) {

    }


    /**
     * Informa al jugador que debe levantar las cartas de la mesa y si suma puntos o no.
     * @param puntos
     */
    public void levantarCartas(int puntos) {

    }



    /* ------------- Funciones para crear o modificar componentes de la ventana ------------ */


    /**
     * Crea las pilas de cartas de la mesa.
     */
    private void pilasEnMesa() {


    }


    /**
     * Crea la mano de cartas del jugador.
     */
    private void cartasEnMano() {


    }



    /**
     * Crea las vistas de las cartas del jugador.
     * @param cartasmano
     */
    public void generarCartas(ArrayList<Carta> cartasmano) {


    }


    /**
     * Reinicia la mano de cartas del jugador al inicio de cada ronda.
     */
    public void reiniciarMano() {


    }

}
