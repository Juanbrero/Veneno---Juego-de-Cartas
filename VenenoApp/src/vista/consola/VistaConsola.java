package vista.consola;

import controlador.Controlador;
import modelo.baraja.ICarta;
import modelo.baraja.IPilaPalo;
import modelo.baraja.Palo;
import vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VistaConsola extends JFrame implements IVista {

    private Controlador controlador;
    private VistaInicioConsola inicio;
    private int op;
    private double sumaValorPilaOro = 0;
    private double sumaValorPilaBasto = 0;
    private double sumaValorPilaEspada = 0;
    private int levanteOro = 0;
    private int levanteBasto = 0;
    private int levanteEspada = 0;
    private int puntos = 0;
    private JTextArea areaTexto;
    private JTextField inputAccion;
    private ArrayList<ICarta> mano = new ArrayList<>();
    private Map<String,Palo> envenenado = new HashMap<>();


    public VistaConsola(Controlador controlador) {

        this.controlador = controlador;
        this.controlador.setVista(this);
        this.inicio = new VistaInicioConsola(this);
        envenenado.put("B", Palo.BASTO);
        envenenado.put("O", Palo.ORO);
        envenenado.put("E", Palo.ESPADA);

        setTitle("Veneno - Juego de cartas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        inputAccion = new JTextField();
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String comando = inputAccion.getText();
                inputAccion.setText("");
                areaTexto.append(inicio.getNombre() + ": " + comando + "\n");

                if (!comando.isEmpty() && Character.isDigit(comando.charAt(0))) {
                    op = Integer.parseInt(comando);
                    if (op > 0 && op < 5 && mano.get(op - 1).isEnMano()) {
                        controlador.tirarCarta(mano.get(op - 1));
                    }
                    else {
                        areaTexto.append("Sistema: Opcion invalida. Intente otra vez.\n");
                    }
                }
                else if (!comando.isEmpty() && !Character.isDigit(comando.charAt(0))){
                    controlador.envenenar(envenenado.get(comando.toUpperCase()));
                }
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

    public JTextArea getAreaTexto() {
        return areaTexto;
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
        mostrarMensaje("\nPuntos a levantar:\n\tBasto [" + levanteBasto + "]\tOro [" + levanteOro + "]\tEspada [" + levanteEspada + "]\n");
    }

    @Override
    public void mostrarMensaje(String s) {
        areaTexto.append(s);
    }

    @Override
    public void tirarCarta(ICarta cartaJugada) {

        this.mano.get(cartaJugada.getId()).setEnMano(false);
    }

    @Override
    public void envenenar() {

        mostrarMensaje("Selecciona la pila a envenenar:\n\t[B]Basto\t[O]Oro\t[E]Espada\n");
    }

    @Override
    public void agregarCartaEnMesa(IPilaPalo pila) {

        switch (pila.getPalo()) {
            case BASTO -> {
                sumaValorPilaBasto = pila.getSumaValores();
                levanteBasto = pila.getLevante();
            }
            case ORO -> {
                sumaValorPilaOro = pila.getSumaValores();
                levanteOro = pila.getLevante();
            }
            case ESPADA -> {
                sumaValorPilaEspada = pila.getSumaValores();
                levanteEspada = pila.getLevante();
            }
        }

    }

    @Override
    public void reiniciarPila(IPilaPalo pilaAReiniciar) {
        switch (pilaAReiniciar.getPalo()) {
            case BASTO -> {
                sumaValorPilaBasto = 0;
                levanteBasto = 0;
            }
            case ORO -> {
                sumaValorPilaOro = 0;
                levanteOro = 0;
            }
            case ESPADA -> {
                sumaValorPilaEspada = 0;
                levanteEspada = 0;
            }
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

        sumaValorPilaOro = 0;
        sumaValorPilaBasto = 0;
        sumaValorPilaEspada = 0;
        levanteOro = 0;
        levanteBasto = 0;
        levanteEspada = 0;
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
