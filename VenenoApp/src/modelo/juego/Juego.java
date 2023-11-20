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
import java.util.Comparator;
import java.util.List;

public class Juego extends ObservableRemoto implements IJuego {

    private static Juego instancia;
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
    private PilaPalo pilaActualizada;
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

    private void setCantidadRondas() throws RemoteException {
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

    public PilaPalo getPilaActualizada() throws RemoteException {
        return pilaActualizada;
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

    /**
     * Notifica a los observadores que inicia la partida para crear la vista de la partida e inicia la primera ronda.
     * @throws RemoteException
     */
    public void iniciarJuego() throws RemoteException {

        notificarObservadores(Evento.START);
        mazo.mezclar();
        jugarRonda();

    }


    /**
     * Se reparten las cartas a los jugadores logicos del modelo.
     */
    public void repartirCartas() {

        try {
            System.out.println("juego > se reparten las cartas");
            for (int i = 0; i < getCantidadJugadores(); i++) {
                System.out.println("juego > jugador " + jugadores.get(i).getNombre() + " recibe sus cartas" );
                jugadores.get(i).recibirCartas(mazo.repartir());
                System.out.println("juego > se repartieron " + jugadores.get(i).getCartasEnMano().size()+ " cartas");
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Maneje la logica de jugar una ronda, repartiendo cartas el inicio de esta y notificando el primer turno.
     * @throws RemoteException
     */
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


    /**
     * Maneja la logica de tirar una carta. Verifica si se debe reiniciar la pila de la mesa y pasa el turno.
     * @param carta
     * @param palo
     * @throws RemoteException
     */
    public void tirarCarta(int carta, String palo) throws RemoteException {

        cartaJugadaTurnoActual = jugadores.get(jugadorActual).getCartasEnMano().get(carta);
        indiceCartaJugadaTurnoActual = carta;
        System.out.println("se tiro la carta " + cartaJugadaTurnoActual.getPalo() + " del indice " + carta);
        reiniciarPila = false;
        if (palo.equals(Palo.BASTO.toString())) {
            pilaBasto.agregarCarta(cartaJugadaTurnoActual); //agrego primero la carta a la pila de la mesa
            jugadores.get(jugadorActual).tirarCarta(carta); //tiro la carta de la mano del jugador
            pilaActualizada = pilaBasto;

            if(verificarSumaPila(pilaBasto,jugadorActual)) {
                reiniciarPila = true;
                pilaBasto.reinicarPila();
            }
        }
        else if (palo.equals(Palo.ORO.toString())) {
            pilaOro.agregarCarta(cartaJugadaTurnoActual);
            jugadores.get(jugadorActual).tirarCarta(carta);
            pilaActualizada = pilaOro;

            if(verificarSumaPila(pilaOro,jugadorActual)) {
                reiniciarPila = true;
                pilaOro.reinicarPila();
            }
        }
        else if (palo.equals(Palo.ESPADA.toString())) {
            pilaEspada.agregarCarta(cartaJugadaTurnoActual);
            jugadores.get(jugadorActual).tirarCarta(carta);
            pilaActualizada = pilaEspada;

            if(verificarSumaPila(pilaEspada,jugadorActual)) {
                reiniciarPila = true;
                pilaEspada.reinicarPila();
            }
        }

        System.out.println("juego > pila actualizada: " + pilaActualizada.getPalo().toString());
        this.manosJugadas++;
        notificarObservadores(Evento.CARTA_JUGADA);
        jugadores.get(jugadorActual).setPuntosALevantar(0); //Reiniciar los puntos a levantar del jugador.
        pasarTurno();
    }


    /**
     * Verifica la suma de la pila en la mesa, si supera los 13 puntos se reinicia la pila y el jugador suma,
     * en el caso de ser necesario, los puntos correspondientes a la cantidad de cartas de copa que posee la pila.
     * @param pila
     * @param jugadorActual
     * @return
     * @throws RemoteException
     */
    public boolean verificarSumaPila(PilaPalo pila, int jugadorActual) throws RemoteException {

        boolean levantarCartas = false;

        if (pila.getSumaValores() >= PilaPalo.getSumaMinima()) {

            /*Si supera el limite de valor acumulado, calculo cuantos puntos se le sumarian al jugador
            (cada carta de copa que levante se le suma un punto) */

            int puntos = 0;
            System.out.println("juego > cartas en la pila " + pila.getPalo().toString() + pila.getCartasEnMesa());
            for (Carta c : pila.getCartasEnMesa()) {
                System.out.println(c.getPalo().toString());
                if(c.isCopa()) {
                    puntos ++;
                    System.out.println("juego > carta de copa + 1 punto");
                }
            }
            System.out.println("juego > verificar suma: " + puntos + " puntos");
            jugadores.get(jugadorActual).sumarPuntos(puntos);
            levantarCartas = true;
        }

        return levantarCartas;
    }


    /**
     * Asigna el turno actual al siguiente jugador mientras haya turnos por jugar.
     * @throws RemoteException
     */
    public void pasarTurno() throws RemoteException {

        /* jugador anterior setea turno en false y pasa al siguiente */
        jugadores.get(jugadorActual).setMiTurno(false);
        jugadorActual = (jugadorActual + 1) % cantidadJugadores;
        jugadores.get(jugadorActual).setMiTurno(true);

        /* Se va a pasar turnos hasta que todos los jugadores tiren sus 4 cartas
         * luego se inicia una nueva ronda en caso de quedar rondas por jugar o
         * termina la partida */
        if (manosJugadas < this.cantidadJugadores * 4) {
            notificarObservadores(Evento.TURNO);
        }
        else {
            System.out.println("nueva ronda");
            this.manosJugadas = 0;

            /* Aca se crea el bucle de la cantidad de rondas */
            if (rondaActual < cantidadRondas) {
                System.out.println(rondaActual);
                jugarRonda();
            }
            else {

                finPartida();
            }
        }

    }


    public void finPartida() throws RemoteException {

        //Genero una lista ordenada de los jugadores segun los puntos obtenidos
        resultadosFinales = jugadores.stream().sorted(Comparator.comparingInt(Jugador::getPuntos)).toList();
        notificarObservadores(Evento.FIN_JUEGO);
    }


    /**
     * Retorno el jugador del turno actual.
     * @return
     * @throws RemoteException
     */
    public Jugador obtenerJugadorActual() throws RemoteException {
        return jugadores.get(jugadorActual);
    }

}
