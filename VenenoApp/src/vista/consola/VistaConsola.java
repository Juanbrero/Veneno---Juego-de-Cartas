package vista.consola;

import controlador.Controlador;
import modelo.baraja.Carta;
import vista.IVista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VistaConsola extends JFrame implements IVista {

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
    private JTextArea chatArea;
    private JTextField inputField;


    public VistaConsola(Controlador controlador) {

        this.controlador = controlador;

//        consola = new JFrame("Veneno - Juego de cartas");
//        consola.setSize(750, 580);
//        consola.setLocationRelativeTo(null);
//        consola.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        panelGral = (JPanel) consola.getContentPane();
//        panelGral.setLayout(new BorderLayout());
//
//        /* panel de texto de la consola.*/
//        panelTexto = new JPanel(new FlowLayout());
//        panelTexto.setPreferredSize(new Dimension(750,500));
//        JTextField campoTexto = new JTextField();
//        campoTexto.setPreferredSize(new Dimension(750,500));
//        panelTexto.add(campoTexto);
//
//        panelGral.add(panelTexto, BorderLayout.CENTER);
//
//        /* panel de comandos */
//        panelComandos = new JPanel(new GridLayout(1,2));
//        panelComandos.setBorder(new LineBorder(Color.DARK_GRAY));
//        panelComandos.setPreferredSize(new Dimension(750,80));
//        JPanel ingresoComando = new JPanel(new FlowLayout());
//        ingresoComando.setPreferredSize(new Dimension(700,80));
//        JTextArea lineaComando = new JTextArea();
//        ingresoComando.add(lineaComando,FlowLayout.LEFT);
//        panelComandos.add(ingresoComando);
//
//        /* Boton para enviar el comando */
//        JPanel enviarComando = new JPanel(new FlowLayout());
//        enviarComando.setPreferredSize(new Dimension(50,80));
//        JButton enviar = new JButton("Listo");
//        enviarComando.add(enviar);
//        panelComandos.add(enviarComando);
//
//        panelGral.add(panelComandos, BorderLayout.SOUTH);

        setTitle("Chat Console");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(enviarButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);


    }

    private void enviarMensaje() {
        String mensaje = inputField.getText();
        if (!mensaje.isEmpty()) {
            chatArea.append("Tú: " + mensaje + "\n");
            inputField.setText("");
        }
    }

    @Override
    public void iniciar() {
        setVisible(true);
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
