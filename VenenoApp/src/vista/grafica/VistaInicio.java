package vista.grafica;

import controlador.Controlador;
import modelo.baraja.Carta;
import modelo.baraja.ICarta;
import modelo.baraja.IPilaPalo;
import modelo.baraja.Palo;
import modelo.jugador.IJugador;
import vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaInicio implements IVista {

    private Controlador controlador;
    private JFrame pantallaMenu;
    private VistaGrafica pantallaJuego;
    private JPanel panelPrincipal;
    private JPanel panelMenu;
    private JPanel menuOpciones;
    private JPanel datos;
    private JPanel selectJugadores;
    private JRadioButton op1;
    private JRadioButton op2;
    private JRadioButton op3;
    private JTextField nombreUsuario;
    private String nombre;
    private IJugador jugador;
    JDialog colaEspera;


    public VistaInicio(Controlador controlador) {
        this.setControlador(controlador);
        this.controlador.setVista(this);

        //Caracteristicas de la ventana
        pantallaMenu = new JFrame("Veneno - Juego de cartas");
        pantallaMenu.setSize(450, 300);
        pantallaMenu.setLocationRelativeTo(null); // Centra la ventana
        pantallaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Operacion de cierre

        panelesGenerales();

        datosUsuario();

        menuCantidadJugadores();

        verEstadisticas();

        botonStart();

        JLabel copyright = new JLabel("Por Juan Brero");
        panelPrincipal.add(copyright, BorderLayout.SOUTH);

    }


    private void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }


    public Controlador getControlador() {
        return this.controlador;
    }


    /**
     * Setea la pantalla principal como visible.
     */
    public void iniciar() {
        pantallaMenu.setVisible(true);
    }


    /**
     * Crea el panel de la cola de espera.
     * @param conectados
     * @param jugadores
     */
    public void colaDeEspera(int conectados, int jugadores) {

        colaEspera = new JDialog(pantallaMenu);
        colaEspera.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        colaEspera.setSize(260, 95);
        colaEspera.setLocationRelativeTo(null);
        colaEspera.setTitle("Cola de espera");
        colaEspera.setLayout(new FlowLayout());
        JPanel mensaje = new JPanel(new GridLayout());
        mensaje.add(new JLabel("Esperando al resto de jugadores.. [" + conectados + "/" + jugadores + "]"));
        colaEspera.getContentPane().add(mensaje);


        colaEspera.setVisible(true);

    }


    /**
     * Crea la pantalla de la partida.
     */
    public void iniciarPartida() {
        if (colaEspera != null) {
            this.colaEspera.dispose();
        }

        System.out.println("vista > arranca");

        this.pantallaMenu.setVisible(false);
        pantallaJuego = new VistaGrafica(jugador, this);

    }


    /**
     * Llama al metodo para crear las vistas de cada carta.
     * @param cartas
     */
    public void generarCartas(ArrayList<ICarta> cartas) {

        this.pantallaJuego.generarCartas(cartas);

    }

    /**
     * Reinicia las cartas de la mano al comenzar una nueva ronda.
     */
    public void reiniciarMano() {
        pantallaJuego.reiniciarMano();
    }


    @Override
    public void jugarTurno() {
        mostrarMensaje("Tu turno!");
    }


    public void tirarCarta(ICarta cartaJugada) {
        pantallaJuego.tirarCarta(cartaJugada);
    }


    public void envenenar() {

        JPanel veneno = new JPanel(new GridLayout(2,1));
        JLabel mensaje = new JLabel("Veneno! - Selecciona el pilon de cartas que queres envenenar");
        JPanel opciones = new JPanel(new FlowLayout());

        veneno.add(mensaje);
        veneno.add(opciones);


        JButton basto = new JButton("Basto");
        opciones.add(basto);
        basto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controlador.envenenar(Palo.BASTO);
            }
        });

        JButton oro = new JButton("Oro");
        opciones.add(oro);
        oro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.envenenar(Palo.ORO);
            }
        });

        JButton espada = new JButton("Espada");
        opciones.add(espada);
        espada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.envenenar(Palo.ESPADA);
            }
        });


        nuevoDialogo(veneno);

    }


    public void agregarCartaEnMesa(IPilaPalo pila) {
        pantallaJuego.agregarCartaEnMesa(pila);
    }


    public void reiniciarPila(IPilaPalo pilaAReiniciar) {

        pantallaJuego.reiniciarPila(pilaAReiniciar);
    }


    public void levantarCartas(int puntos) {
        System.out.println("vista > puntos: " + puntos);
        pantallaJuego.levantarCartas(puntos);
    }


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

        JDialog panelDialogo = new JDialog(pantallaJuego.getPantallaJuego());
        panelDialogo.setLocationRelativeTo(pantallaJuego.getPantallaJuego());
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
                pantallaJuego.getPantallaJuego().dispose();
                pantallaMenu.setVisible(true);
            }
        });

        panelDialogo.setVisible(true);

        restablecerSesion();

    }


    public void restablecerSesion() {

        pantallaJuego.restablecerSesion();

    }


    public void ranking(ArrayList<String> resultados) {

        System.out.println("vista > el ranking tinene " + resultados.size() + " elementos");
        JPanel panelMnsj = new JPanel();
        panelMnsj.setLayout(new GridLayout(15,1));
        int i = 0;
        while(i < 10 && i < resultados.size()) {

            JLabel mnsj = new JLabel(resultados.get(i) + "\n");
            panelMnsj.add(mnsj);
            i++;
        }

        JDialog panelDialogo = new JDialog(this.pantallaMenu);
        panelDialogo.setAlwaysOnTop(true);
        panelDialogo.setLocationRelativeTo(this.pantallaMenu);
        panelDialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel elementos = new JPanel(new GridLayout(10,1));
        elementos.add(panelMnsj);
        panelDialogo.add(panelMnsj);

        panelDialogo.setSize(450, 450);

        panelDialogo.setVisible(true);
    }


    public void mostrarMensaje(String mensaje) {

        JPanel panelMnsj = new JPanel();
        JLabel mnsj = new JLabel(mensaje);
        panelMnsj.add(mnsj);

        nuevoDialogo(panelMnsj);

    }


    /**
     * Crea un nuevo Cartel de dialogo.
     * @param panel
     */
    public void nuevoDialogo(JPanel panel) {

        JDialog panelDialogo = new JDialog(this.pantallaJuego.getPantallaJuego());
        panelDialogo.setAlwaysOnTop(true);
        panelDialogo.setLocationRelativeTo(this.pantallaJuego.getPantallaJuego());
        panelDialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton ok = new JButton("OK");
        JPanel botonOk = new JPanel();
        botonOk.add(ok);
        JPanel elementos = new JPanel(new GridLayout(2,1));
        elementos.add(panel);
        elementos.add(botonOk);
        panelDialogo.add(elementos);

        panelDialogo.setSize(250, 250);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDialogo.dispose();
            }
        });

        panelDialogo.setVisible(true);

    }


    /* ------------- Funciones para crear componentes de la ventana ------------ */


    /**
     * Crea los paneles principales de la ventana.
     */
    private void panelesGenerales() {

        //Panel general
        panelPrincipal = (JPanel) pantallaMenu.getContentPane();
        panelPrincipal.setLayout(new BorderLayout());

        //Panel contenedor del menu
        panelMenu = new JPanel(new FlowLayout());

        //Panel de opciones
        menuOpciones = new JPanel(new GridLayout(6,1));

        //Agrego el panel de opciones al panel del menu y luego agrego el panel de menu al panel principal.
        panelMenu.add(menuOpciones);
        panelPrincipal.add(panelMenu);

        //Titulo
        JPanel titulo = new JPanel(new FlowLayout());
        JLabel cartelMenuPrincipal = new JLabel("Menu Principal");
        titulo.add(cartelMenuPrincipal);
        menuOpciones.add(titulo); // agrego el titulo del menu
    }


    /**
     * Crea los paneles donde van los datos del jugador.
     */
    private void datosUsuario() {

        //Datos del jugador
        datos = new JPanel(new GridLayout(1,2));
        JLabel user = new JLabel("Nombre de usuario");
        nombreUsuario = new JTextField();

        datos.add(user);
        datos.add(nombreUsuario);
        menuOpciones.add(datos);
    }


    /**
     * Crea los paneles del menu de botones para seleccionar la cantidad de jugadores.
     */
    private void menuCantidadJugadores() {

        //Panel que contiene los radio buttons para indicar cantidad de jugadores
        selectJugadores = new JPanel(new GridLayout(1, 4));
        JLabel cantJugadores = new JLabel("Jugadores: ");
        selectJugadores.add(cantJugadores);
        // agrego el panel de cantidad de jugadores al panel de opciones
        menuOpciones.add(selectJugadores);

        //Creo los botones de opciones
        op1 = new JRadioButton("2");
        op1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cantidadJugadores(2);
            }
        });
        op2 = new JRadioButton("3");
        op2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cantidadJugadores(3);
            }
        });
        op3 = new JRadioButton("4");
        op3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cantidadJugadores(4);
            }
        });

        // Agrupo los botones para que solo se pueda seleccionar uno a la vez.
        ButtonGroup opCantidadJugadores = new ButtonGroup();
        opCantidadJugadores.add(op1);
        opCantidadJugadores.add(op2);
        opCantidadJugadores.add(op3);

        //Agrego los botones al panel
        selectJugadores.add(op1);
        selectJugadores.add(op2);
        selectJugadores.add(op3);
    }


    /**
     * Crea el boton de inicio y define su actionListener.
     */
    private void botonStart() {

        //Boton de start
        JButton start = new JButton("Jugar");
        start.setPreferredSize(new Dimension(80,40));
        menuOpciones.add(start);

        //Accion del boton start, se conecta a la partida.
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Si el usuario no ingresa un nombre, se le asigna por defecto AnonPlayer + un id
                nombre = nombreUsuario.getText();
                if (nombre.isEmpty()) {
                    nombre = "AnonPlayer" + this.hashCode();
                }
                System.out.println("vista > me voy a conectar a la partida como " + nombre);
                nombreUsuario.setText("");
                jugador = controlador.conectarse(nombre);
                System.out.println("vista > me conecte como " + jugador.getNombre());
                try {
                    controlador.inicio();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }


    /**
     * Crea el menu para ver el ranking de jugadores.
     */
    public void verEstadisticas() {

        JButton stats = new JButton("Ranking Global");
        stats.setPreferredSize(new Dimension(80,40));
        menuOpciones.add(stats);

        stats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                controlador.verEstadisticas();

            }
        });

    }

}
