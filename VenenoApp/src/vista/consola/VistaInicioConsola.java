package vista.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class VistaInicioConsola{

    private VistaConsola consola;
    private JFrame pantallaMenu;
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

    public VistaInicioConsola(VistaConsola consola) {

        this.consola = consola;
        pantallaMenu = new JFrame("Veneno - Juego de cartas");
        pantallaMenu.setSize(450, 300);
        pantallaMenu.setLocationRelativeTo(null); // Centra la ventana
        pantallaMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Operacion de cierre

        panelesGenerales();

        datosUsuario();

        menuCantidadJugadores();

        verEstadisticas();

        botonStart();

        JLabel copyright = new JLabel("Por Juan Brero");
        panelPrincipal.add(copyright, BorderLayout.SOUTH);

    }

    public JFrame getPantallaMenu() {
        return pantallaMenu;
    }

    public String getNombre() {
        return nombre;
    }

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
        menuOpciones = new JPanel(new GridLayout(5,1));

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
                consola.getControlador().cantidadJugadores(2);
            }
        });
        op2 = new JRadioButton("3");
        op2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consola.getControlador().cantidadJugadores(3);
            }
        });
        op3 = new JRadioButton("4");
        op3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consola.getControlador().cantidadJugadores(4);
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
     * Crea el menu para ver el ranking de jugadores.
     */
    public void verEstadisticas() {

        JButton stats = new JButton("Ranking Global");
        stats.setPreferredSize(new Dimension(80,40));
        menuOpciones.add(stats);

        stats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                consola.getControlador().verEstadisticas();

            }
        });

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
                System.out.println("vista > me voy a conectar a la partida");
                consola.getControlador().conectarse(nombre);
                nombreUsuario.setText("");
                pantallaMenu.setVisible(false);
                consola.setVisible(true);
                System.out.println("vista > me conecte");

                try {
                    consola.getControlador().inicio();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

}
