package modelo.juego;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import eventos.Evento;
import modelo.baraja.Carta;
import modelo.baraja.Mazo;
import modelo.baraja.Palo;
import modelo.baraja.PilaPalo;
import modelo.jugador.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Juego extends ObservableRemoto implements IJuego {

    static Juego instancia;
    private PilaPalo pilaOro;
    private PilaPalo pilaEspada;
    private PilaPalo pilaBasto;
    private Mazo mazo;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private int cantidadJugadores;
    private int jugadoresConectados = 0;
    private int jugadorActual = 0;
    private int cantidadRondas = 2;
    private int rondaActual = 0;
    private int manosJugadas = 0;
    private Carta cartaJugadaTurnoActual;
    private int indiceCartaJugadaTurnoActual;
    private String pilaAReiniciar;
    private boolean reiniciarPila;
    private List<Jugador> resultadosFinales = new ArrayList<>();


    private Juego() {
        this.mazo = new Mazo();
        this.pilaBasto = new PilaPalo(Palo.BASTO);
        this.pilaOro = new PilaPalo(Palo.ORO);
        this.pilaEspada = new PilaPalo(Palo.ESPADA);
        System.out.println("juego > me cree");
    }

    public static Juego getInstancia() {
        if (Juego.instancia == null) {
            Juego.instancia = new Juego();
            System.out.println("juego > me instancie");
        }
        return Juego.instancia;
    }

    /* ----------- Getters & Setters ----------- */

    public ArrayList<Jugador> getJugadores() throws RemoteException {
        return jugadores;
    }

    public int getCantidadJugadores() throws RemoteException {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) throws RemoteException {
        this.cantidadJugadores = cantidadJugadores;
        this.setCantidadRondas();
    }

    public int getJugadoresConectados() throws RemoteException {
        return jugadoresConectados;
    }

    public void setCantidadRondas() throws RemoteException {
        this.cantidadRondas = mazo.getCantidadCartasEnMazo() / this.cantidadJugadores;
    }

    public Carta getCartaJugadaTurnoActual() throws RemoteException {
        return cartaJugadaTurnoActual;
    }

    public int getIndiceCartaJugadaTurnoActual() throws RemoteException {
        return indiceCartaJugadaTurnoActual;
    }

    public boolean isReiniciarPila() throws RemoteException {
        return reiniciarPila;
    }

    public String getPilaAReiniciar() throws RemoteException {
        return pilaAReiniciar;
    }

    public List<Jugador> getResultadosFinales() throws RemoteException {
        return this.resultadosFinales;
    }

    /* ----------- Metodos del modelo ----------- */


    /**
     * Cuando un jugador nuevo se conecta a la partida, lo instancia, se agrega a la lista de jugadores y lo retorna al
     * controlador. Notifica a los observadores de la conexion.
     * @param nombre
     * @return
     * @throws RemoteException
     */
    public Jugador agregarJugador(String nombre) throws RemoteException {

        /* Crea un nuevo jugador y lo agrega a la lista,
         * incrementa la cantidad de jugadores conectados.
        */
        Jugador nuevoJugador = new Jugador(nombre, jugadoresConectados);
        this.jugadores.add(nuevoJugador);
        this.jugadoresConectados++;
        System.out.println("juego > cree el jugador " + nuevoJugador.getNombre());
        System.out.println("juego > id jugador creado = " + nuevoJugador);

        notificarObservadores(Evento.JUGADOR_CONECTADO);

        return nuevoJugador;
    }

    public void iniciarJuego() throws RemoteException {

        notificarObservadores(Evento.START);
        jugarRonda();

    }

    public void repartirCartas() {

        try {
            System.out.println("juego > se reparten las cartas");
            for (int i = 0; i < getCantidadJugadores(); i++) {
                System.out.println("juego > jugador " + jugadores.get(i).getNombre() + " recibe sus cartas" );
                jugadores.get(i).recibirCartas(mazo.repartir());

            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void jugarRonda() throws RemoteException {

        this.rondaActual++;

        repartirCartas();

        System.out.println("juego > inicia ronda");
        notificarObservadores(Evento.INICIO_RONDA);

        // Asigno los turnos
        System.out.println("manos jugadas " + manosJugadas);

        jugadores.get(jugadorActual).setMiTurno(true);
        notificarObservadores(Evento.TURNO);
    }

    public Jugador obtenerJugadorActual() throws RemoteException {
        return jugadores.get(jugadorActual);
    }




}
