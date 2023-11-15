package vista.grafica;

import controlador.Controlador;
import vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaGrafica implements IVista {

    private Controlador controlador;
    private JFrame pantallaMenu;
    private VentanaJuego pantallaJuego;
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
    JDialog colaEspera;

    public VentanaGrafica(Controlador controlador) {
        this.setControlador(controlador);
        this.controlador.setVista(this);

        //Caracteristicas de la ventana
        pantallaMenu = new JFrame("Veneno - Juego de cartas");
        pantallaMenu.setSize(450, 300);
        pantallaMenu.setLocationRelativeTo(null); // Centra la ventana
        pantallaMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Operacion de cierre

        panelesGenerales();

        datosUsuario();

        menuCantidadJugadores();

        botonStart();

        JLabel copyright = new JLabel("Por Juan Brero");
        panelPrincipal.add(copyright, BorderLayout.SOUTH);

    }

    private void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        pantallaMenu.setVisible(true);
    }

    public void colaDeEspera(int conectados, int jugadores) {

        colaEspera = new JDialog(pantallaMenu);
        colaEspera.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        colaEspera.setSize(200, 120);
        colaEspera.setLocationRelativeTo(null);
        colaEspera.setTitle("Cola de espera");
        colaEspera.setLayout(new FlowLayout());
        JPanel mensaje = new JPanel(new GridLayout());
        mensaje.add(new JLabel("Esperando al resto de jugadores.. [" + conectados + "/" + jugadores + "]"));
        colaEspera.getContentPane().add(mensaje);


        colaEspera.setVisible(true);

    }

    public void iniciarPartida() {
        if (colaEspera != null) {
            System.out.println("vista > en cola de espera");
            colaEspera.dispose();
        }
        pantallaMenu.setVisible(false);
        System.out.println("vista > arrancamossss");
        VentanaJuego partida = new VentanaJuego(this.nombre);
    }

    @Override
    public void generarCarta(String palo, int nro, int i) {
        this.pantallaJuego.generarCarta(palo, nro, i);
    }

    public void reiniciarMano() {

        pantallaJuego.reiniciarMano();

    }


    public void mostrarMensaje(String mensaje) {

        JPanel panelMnsj = new JPanel();
        JLabel mnsj = new JLabel(mensaje);
        panelMnsj.add(mnsj);

        nuevoDialogo(panelMnsj);

    }

    public void nuevoDialogo(JPanel panel) {

        JDialog panelDialogo = new JDialog();
        panelDialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton ok = new JButton("OK");
        JPanel botonOk = new JPanel();
        botonOk.add(ok);
        JPanel elementos = new JPanel(new GridLayout(2,1));
        elementos.add(panel);
        elementos.add(botonOk);
        panelDialogo.add(elementos);

        panelDialogo.setSize(250, 250);
        panelDialogo.setLocationRelativeTo(null);


        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDialogo.dispose();
            }
        });

        panelDialogo.setVisible(true);
    }







    /* ------------- Funciones para crear componentes de la ventana ------------ */

    private void panelesGenerales() {

        //Panel general
        panelPrincipal = (JPanel) pantallaMenu.getContentPane();
        panelPrincipal.setLayout(new BorderLayout());

        //Panel contenedor del menu
        panelMenu = new JPanel(new FlowLayout());

        //Panel de opciones
        menuOpciones = new JPanel(new GridLayout(4,1));

        //Agrego el panel de opciones al panel del menu y luego agrego el panel de menu al panel principal.
        panelMenu.add(menuOpciones);
        panelPrincipal.add(panelMenu);

        //Titulo
        JLabel cartelMenuPrincipal = new JLabel("Menu Principal");
        menuOpciones.add(cartelMenuPrincipal); // agrego el titulo del menu
    }

    private void datosUsuario() {

        //Datos del jugador
        datos = new JPanel(new GridLayout(1,2));
        JLabel user = new JLabel("Nombre de usuario");
        nombreUsuario = new JTextField();

        datos.add(user);
        datos.add(nombreUsuario);
        menuOpciones.add(datos);
    }

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

        //Por defecto se selecciona la primer opcion.
        op1.setSelected(true);

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
//                pantallaMenu.dispose();
                System.out.println("vista > me voy a conectar a la partida");
                controlador.conectarse(nombre);
                System.out.println("vista > me conecte");

            }
        });
    }







}
