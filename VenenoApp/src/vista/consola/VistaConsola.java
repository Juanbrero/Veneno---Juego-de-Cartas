package vista.consola;

import controlador.Controlador;
import modelo.baraja.Carta;
import vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VistaConsola extends JFrame implements IVista {

    private Controlador controlador;
    private VistaInicioConsola inicio;
    private boolean envenenar = false;
    private int op;
    private double sumaValorPilaOro = 0;
    private double sumaValorPilaBasto = 0;
    private double sumaValorPilaEspada = 0;
    private int puntos = 0;
    private JTextArea areaTexto;
    private JTextField inputAccion;
    private ArrayList<Carta> mano = new ArrayList<>();


    public VistaConsola(Controlador controlador) {

        this.controlador = controlador;
        this.controlador.setVista(this);
        this.inicio = new VistaInicioConsola(this);

        setTitle("Veneno - Juego de cartas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        inputAccion = new JTextField();
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeleccionarPila();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputAccion, BorderLayout.CENTER);
        inputPanel.add(enviarButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);

    }

    public Controlador getControlador() {
        return controlador;
    }


    @Override
    public void iniciar() {
        setVisible(false);
        this.inicio.getPantallaMenu().setVisible(true);
    }

    @Override
    public void colaDeEspera(int conectados, int jugadores) {
        areaTexto.append("Esperando al resto de jugadores.. [" + conectados + "/" + jugadores + "]\n");
    }

    @Override
    public void iniciarPartida() {

        areaTexto.append("Inicia la partida!\n");
    }

    @Override
    public void generarCartas(ArrayList<Carta> cartas) {
        mano = cartas;
        areaTexto.append("Tus cartas son: ");
        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).isEnMano()) {
                areaTexto.append(mano.get(i).toString() + " ");
            }
        }
        areaTexto.append("\n");
    }

    @Override
    public void reiniciarMano() {

    }

    @Override
    public void jugarTurno() {
        mostrarMensaje("Tu turno!\nSelecciona una carta de tu mano {");

        for (int j = 0; j < mano.size(); j++) {
            if (mano.get(j).isEnMano()) {
                areaTexto.append(" <" + (j+1) + "> ");
            }
        }
        areaTexto.append("}\n");

        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).isEnMano()) {
                areaTexto.append(mano.get(i).toString() + " ");
            }
        }
        areaTexto.append("\n");
    }

    @Override
    public void mostrarMensaje(String s) {
        areaTexto.append(s);
    }

    private void SeleccionarPila() {
        String mensaje = inputAccion.getText();

        if (!mensaje.isEmpty()) {

            areaTexto.append(inicio.getNombre() + ": " + mensaje + "\n");
            inputAccion.setText("");
            if (!envenenar) {   //Si es false el input corresponde a la seleccion de la carta a tirar

                if(Character.isDigit(mensaje.charAt(0))){

                    op = Integer.parseInt(mensaje);
                }

                if (op > 0 && op < 5 && mano.get(op - 1).isEnMano()) { //es una carta valida para jugar
                    if (mano.get(op - 1).isCopa()) {
                        mostrarMensaje("Selecciona la pila a envenenar:\n[B]Basto [O]Oro [E]Espada\n");
                        envenenar = true;

                    } else {
                        controlador.tirarCarta(op - 1, mano.get(op - 1).getPalo().toString());
                        envenenar = false;
                    }
                }
                else {
                    areaTexto.append("Sistema: Opcion invalida. Intente otra vez.\n");
                }
            }
            else {  //si es true entonces el input corresponde a la pila a envenenar.

                switch (mensaje.toUpperCase()) {
                    case "B":
                        controlador.tirarCarta(op - 1, "BASTO");
                        break;
                    case "O":
                        controlador.tirarCarta(op - 1, "ORO");
                        break;
                    case "E":
                        controlador.tirarCarta(op - 1, "ESPADA");
                        break;
                    default:
                        areaTexto.append("Sistema: Opcion invalida. Intente otra vez.\n");
                        break;
                }
                envenenar = false;

            }
        }

    }

    @Override
    public void tirarCarta(int cartaJugada) {
        this.mano.get(cartaJugada).setEnMano(false);
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

    public static void main(String[] args) {

        VistaConsola consola = new VistaConsola(new Controlador());
        consola.iniciar();

    }

}
