package vista.consola;

import controlador.Controlador;
import modelo.baraja.Carta;
import modelo.baraja.ICarta;
import modelo.baraja.Palo;
import modelo.jugador.IJugador;
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
    private ArrayList<ICarta> mano = new ArrayList<>();


    public VistaConsola(Controlador controlador) {

        this.controlador = controlador;
        this.controlador.setVista(this);
        this.inicio = new VistaInicioConsola(this);

        setTitle("Veneno - Juego de cartas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        inputAccion = new JTextField();
        JButton enviarButton = new JButton("Enviar");
//        enviarButton.addActionListener(new ActionListener() {
//            @Override
////            public void actionPerformed(ActionEvent e) {
////                SeleccionarPila();
////            }
//        });

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

    public JTextArea getAreaTexto() {
        return areaTexto;
    }


    @Override
    public Palo getEnvenenado() {
        return null;
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
    public void generarCartas(ArrayList<ICarta> cartas) {
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
        mano.clear();
    }

    @Override
    public void jugarTurno() {
        mostrarMensaje("-Tu turno!\n\nPuntaje: " + puntos + "\n\n-Selecciona una carta de tu mano:\n");

        for (int j = 0; j < mano.size(); j++) {
            if (mano.get(j).isEnMano()) {
                areaTexto.append("\t<" + (j+1) + "> ");
            }
        }
        areaTexto.append("\n");

        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).isEnMano()) {
                areaTexto.append("\t" + mano.get(i).toString() + " ");
            }
        }
        areaTexto.append("\n");

        mostrarMensaje("\nSuma acumulada en mesa:\n\tBasto [" + sumaValorPilaBasto + "]\tOro [" + sumaValorPilaOro + "]\tEspada [" + sumaValorPilaEspada + "]\n");
    }

    @Override
    public void mostrarMensaje(String s) {
        areaTexto.append(s);
    }


//    private void SeleccionarPila() {
//        String mensaje = inputAccion.getText();
//
//        if (!mensaje.isEmpty()) {
//
//            areaTexto.append(inicio.getNombre() + ": " + mensaje + "\n");
//            inputAccion.setText("");
//            if (!envenenar) {   //Si es false el input corresponde a la seleccion de la carta a tirar
//
//                if(Character.isDigit(mensaje.charAt(0))){
//
//                    op = Integer.parseInt(mensaje);
//                }
//
//                if (op > 0 && op < 5 && mano.get(op - 1).isEnMano()) { //es una carta valida para jugar
//                    if (mano.get(op - 1).isCopa()) {
//                        mostrarMensaje("Selecciona la pila a envenenar:\n\t[B]Basto\t[O]Oro\t[E]Espada\n");
//                        envenenar = true;
//
//                    } else {
//                        controlador.tirarCarta(op - 1, mano.get(op - 1).getPalo().toString());
//                        envenenar = false;
//                    }
//                }
//                else {
//                    areaTexto.append("Sistema: Opcion invalida. Intente otra vez.\n");
//                }
//            }
//            else {  //si es true entonces el input corresponde a la pila a envenenar.
//
//                switch (mensaje.toUpperCase()) {
//                    case "B":
//                        controlador.tirarCarta(op - 1, "BASTO");
//                        break;
//                    case "O":
//                        controlador.tirarCarta(op - 1, "ORO");
//                        break;
//                    case "E":
//                        controlador.tirarCarta(op - 1, "ESPADA");
//                        break;
//                    default:
//                        areaTexto.append("Sistema: Opcion invalida. Intente otra vez.\n");
//                        break;
//                }
//                envenenar = false;
//
//            }
//        }
//
//    }

    @Override
    public void tirarCarta(ICarta cartaJugada) {
//        this.mano.get(cartaJugada).setEnMano(false);
    }

    @Override
    public void envenenar() {

    }

    @Override
    public void agregarCartaEnMesa(String palo, double valor) {

        switch (palo) {
            case "ORO" -> {
                sumaValorPilaOro += valor;
            }
            case "BASTO" -> {
                sumaValorPilaBasto += valor;
            }
            case "ESPADA" -> {
                sumaValorPilaEspada += valor;
            }
        }
    }

    @Override
    public void reiniciarPila(String pilaAReiniciar) {

        if(pilaAReiniciar.equals("ORO")) {
            sumaValorPilaOro = 0;
        }
        else if (pilaAReiniciar.equals("BASTO")) {
            sumaValorPilaBasto = 0;
        }
        else {
            sumaValorPilaEspada = 0;
        }
    }

    @Override
    public void levantarCartas(int puntos) {
        if (puntos > 0) {

            mostrarMensaje("Envenenado! sumas " + puntos + " puntos.\n");
            this.puntos += puntos;
            mostrarMensaje("Puntos acumulados: " + this.puntos + "\n");
        }
        else {
            mostrarMensaje("Uuuf, que cerca! No sumaste puntos! :)\n");
        }
    }

    @Override
    public void finJuego(ArrayList<String> resultados) {
        JPanel panelFin = new JPanel(new FlowLayout());

        JLabel tituloFin = new JLabel("¡FIN DEL JUEGO!\n");
        panelFin.add(tituloFin);

        JLabel labelResultados = new JLabel("Resultados de la partida:\n");
        panelFin.add(labelResultados);

        for (int i = 0; i < resultados.size(); i++) {
            JLabel jugador = new JLabel(resultados.get(i) + "\n");
            panelFin.add(jugador);
        }

        JDialog panelDialogo = new JDialog(this);
        panelDialogo.setLocationRelativeTo(this);
        panelDialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton ok = new JButton("OK");
        JPanel botonOk = new JPanel();
        botonOk.add(ok);
        JPanel elementos = new JPanel(new GridLayout(2,1));
        elementos.add(panelFin);
        elementos.add(botonOk);
        panelDialogo.add(elementos);

        panelDialogo.setSize(250, 250);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDialogo.dispose();
                dispose();
                inicio.getPantallaMenu().setVisible(true);
            }
        });

        panelDialogo.setVisible(true);

        restablecerSesion();
    }

    public void restablecerSesion() {

        envenenar = false;
        sumaValorPilaOro = 0;
        sumaValorPilaBasto = 0;
        sumaValorPilaEspada = 0;
        puntos = 0;
        areaTexto.setText("");
        mano.clear();
    }

    @Override
    public void ranking(ArrayList<String> resultados) {

        JPanel panelMnsj = new JPanel();
        panelMnsj.setLayout(new GridLayout(15,1));
        int i = 0;
        while(i < 10 && i < resultados.size()) {

            JLabel mnsj = new JLabel(resultados.get(i) + "\n");
            panelMnsj.add(mnsj);
            i++;
        }

        JDialog panelDialogo = new JDialog(this.inicio.getPantallaMenu());
        panelDialogo.setAlwaysOnTop(true);
        panelDialogo.setLocationRelativeTo(this.inicio.getPantallaMenu());
        panelDialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel elementos = new JPanel(new GridLayout(10,1));
        elementos.add(panelMnsj);
        panelDialogo.add(panelMnsj);

        panelDialogo.setSize(450, 450);

        panelDialogo.setVisible(true);
    }


}
