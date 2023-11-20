package vista.consola;

import controlador.Controlador;
import modelo.baraja.Carta;
import vista.IVista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;


public class VistaConsola implements IVista {

    private Controlador controlador;
    private JFrame consola;
    private String nombre;
    private double sumaValorPilaOro = 0;
    private double sumaValorPilaBasto = 0;
    private double sumaValorPilaEspada = 0;
    private int puntos = 0;
    private JPanel panelGral;
    private JPanel panelTexto;
    private JPanel panelComandos;



    public VistaConsola(Controlador controlador) {

        this.controlador = controlador;

        consola = new JFrame("Veneno - Juego de cartas");
        consola.setSize(750, 580);
        consola.setLocationRelativeTo(null);
        consola.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panelGral = (JPanel) consola.getContentPane();
        panelGral.setLayout(new GridLayout(2,1));

        /* panel de texto de la consola.*/
        panelTexto = new JPanel(new FlowLayout());
        panelTexto.setPreferredSize(new Dimension(750,500));
        JTextArea campoTexto = new JTextArea();
        panelTexto.add(campoTexto,FlowLayout.CENTER);

        panelGral.add(panelTexto);

        /* panel de comandos */
        panelComandos = new JPanel(new GridLayout(1,2));
        JPanel ingresoComando = new JPanel(new FlowLayout());
        ingresoComando.setPreferredSize(new Dimension(700,80));
        JTextField lineaComando = new JTextField();
        lineaComando.setBorder(new LineBorder(Color.DARK_GRAY));
        ingresoComando.add(lineaComando,FlowLayout.LEFT);
        panelComandos.add(ingresoComando);

        /* Boton para enviar el comando */
        JPanel enviarComando = new JPanel(new FlowLayout());
        enviarComando.setPreferredSize(new Dimension(50,80));
        JButton enviar = new JButton("Listo");
        enviarComando.add(enviar);
        panelComandos.add(enviarComando);

        panelGral.add(panelComandos);

        consola.setVisible(true);

    }



    @Override
    public void iniciar() {

    }

    @Override
    public void colaDeEspera(int conectados, int jugadores) {

    }

    @Override
    public void iniciarPartida() {

    }

    @Override
    public void generarCartas(ArrayList<Carta> cartas) {

    }

    @Override
    public void reiniciarMano() {

    }

    @Override
    public void jugarTurno() {

    }

    @Override
    public void mostrarMensaje(String s) {

    }

    @Override
    public void tirarCarta(int cartaJugada) {

    }

    @Override
    public void agregarCartaEnMesa(String string, double valor) {

    }

    @Override
    public void reiniciarPila(String pilaAReiniciar) {

    }

    @Override
    public void levantarCartas(int puntos) {

    }

    @Override
    public void finJuego(ArrayList<String> resultados) {

    }

}
