package modelo.juego;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import eventos.Evento;
import modelo.baraja.*;
import modelo.jugador.IJugador;
import modelo.jugador.Jugador;
import modelo.serializacion.Serializador;

import java.rmi.RemoteException;
import java.util.*;

public class Juego extends ObservableRemoto implements IJuego {

    private static Serializador serializador = new Serializador("RankingGlobal.dat");
    private static Juego instancia;
    private PilaPalo pilaOro;
    private PilaPalo pilaEspada;
    private PilaPalo pilaBasto;
    private Map<Palo,PilaPalo> pilas = new HashMap<>();
    private Mazo mazo;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private int cantidadJugadores = 0;
    private int jugadoresConectados = 0;
    private int jugadorActual = 0;
    private int cantidadRondas = 2;
    private int rondaActual = 0;
    private int manosJugadas = 0;
    private ICarta cartaJugadaTurnoActual;
    private PilaPalo pilaActualizada;
    private List<Jugador> resultadosFinales = new ArrayList<>();


    private Juego() {
        this.mazo = new Mazo();
        this.pilaBasto = new PilaPalo(Palo.BASTO);
        this.pilaOro = new PilaPalo(Palo.ORO);
        this.pilaEspada = new PilaPalo(Palo.ESPADA);
        this.pilas.put(Palo.BASTO,pilaBasto);
        this.pilas.put(Palo.ORO,pilaOro);
        this.pilas.put(Palo.ESPADA,pilaEspada);
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

    public ArrayList<IJugador> getJugadores() throws RemoteException {

        ArrayList<IJugador> temp = new ArrayList<>();
        temp.addAll(jugadores);
        return temp;
    }

    public int getCantidadJugadores() throws RemoteException {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) throws RemoteException {

        if (this.cantidadJugadores == 0) {
            this.cantidadJugadores = cantidadJugadores;
        }
//        this.setCantidadRondas();
    }

    public int getJugadoresConectados() throws RemoteException {
        return jugadoresConectados;
    }

    private void setCantidadRondas() throws RemoteException {
        this.cantidadRondas = mazo.getCantidadCartasEnMazo() / this.cantidadJugadores;
    }

    public ICarta getCartaJugadaTurnoActual() throws RemoteException {
        return cartaJugadaTurnoActual;
    }


    public IPilaPalo getPilaActualizada() throws RemoteException {
        return pilaActualizada;
    }

    public List<IJugador> getResultadosFinales() throws RemoteException {

        List<IJugador> temp = new ArrayList<>();
        temp.addAll(resultadosFinales);
        return temp;
    }


    /* ----------- Metodos del modelo ----------- */


    /**
     * Cuando un jugador nuevo se conecta a la partida, lo instancia, se agrega a la lista de jugadores y lo retorna al
     * controlador. Notifica a los observadores de la conexion.
     * @param nombre
     * @return
     * @throws RemoteException
     */
    public void agregarJugador(String nombre) throws RemoteException {

        /* Crea un nuevo jugador y lo agrega a la lista,
         * incrementa la cantidad de jugadores conectados.
        */
        System.out.println("juego > cantidad jugadores actuales = " + jugadoresConectados);
        Jugador nuevoJugador = new Jugador(nombre, jugadoresConectados);
        this.jugadores.add(nuevoJugador);
        this.jugadoresConectados++;
        System.out.println("juego > cree el jugador " + nuevoJugador.getNombre() + " id: " + nuevoJugador.getId());
        System.out.println("juego > cantidad jugadores actualizados = " + jugadoresConectados);

        notificarObservadores(Evento.JUGADOR_CONECTADO);

    }

    /**
     * Notifica a los observadores que inicia la partida para crear la vista de la partida e inicia la primera ronda.
     * @throws RemoteException
     */
    public void iniciarJuego() throws RemoteException {

        if(jugadoresConectados == cantidadJugadores) {
            notificarObservadores(Evento.START);
            mazo.mezclar();
            jugarRonda();
        }
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


    public void tirarCarta(ICarta carta, Palo pila) throws RemoteException {

        cartaJugadaTurnoActual = carta;

        if (carta.isCopa() && pila.equals(Palo.COPA)) {
            notificarObservadores(Evento.VENENO);
        }
        else {
            pilas.get(pila).agregarCarta(carta);
            jugadores.get(jugadorActual).tirarCarta(carta);
            pilaActualizada = pilas.get(pila);

            if(verificarSumaPila(pilas.get(pila),jugadorActual)) {
                pilas.get(pila).setReiniciar(true);
                pilas.get(pila).reinicarPila();
            }
            else {
                pilas.get(pila).setReiniciar(false);
            }

            System.out.println("juego > pila actualizada: " + pilaActualizada.getPalo().toString());
            this.manosJugadas++;
            notificarObservadores(Evento.CARTA_JUGADA);
            jugadores.get(jugadorActual).setPuntosALevantar(0); //Reiniciar los puntos a levantar del jugador.
            pasarTurno();

        }

    }


    /**
     * Verifica la suma de la pila en la mesa, si supera los 13 puntos se reinicia la pila y el jugador suma,
     * en el caso de ser necesario, los puntos correspondientes a la cantidad de cartas de copa que posee la pila.
     * @param pila
     * @param jugadorActual
     * @return
     * @throws RemoteException
     */
    private boolean verificarSumaPila(PilaPalo pila, int jugadorActual) throws RemoteException {

        boolean levantarCartas = false;

        if (pila.getSumaValores() >= PilaPalo.getSumaMinima()) {

            jugadores.get(jugadorActual).sumarPuntos(pila.getLevante());
            levantarCartas = true;
        }

        return levantarCartas;
    }


    /**
     * Asigna el turno actual al siguiente jugador mientras haya turnos por jugar.
     * @throws RemoteException
     */
    private void pasarTurno() throws RemoteException {

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


    private void finPartida() throws RemoteException {

        //Genero una lista ordenada de los jugadores segun los puntos obtenidos
        resultadosFinales = jugadores.stream().sorted(Comparator.comparingInt(Jugador::getPuntos)).toList();
        notificarObservadores(Evento.FIN_JUEGO);

        guardarResultados();

        restablecerSesion();

    }


    private void guardarResultados() throws RemoteException {

        if (!resultadosFinales.isEmpty()) {

            for (int i = 0; i < resultadosFinales.size(); i++) {

                System.out.println("juego > buscando historial de jugador: " + resultadosFinales.get(i).getNombre());
                Jugador temp = buscarHistorialJugador(resultadosFinales.get(i));
                System.out.println("juego > puntos actuales: " + resultadosFinales.get(i).getPuntos());
                if (temp != null) {
                    System.out.println("juego > puntos historico: " + temp.getPuntos());
                    //sobreescribir datos
                    /* Actualizo el ratio de puntos por partida del jugador sumando los puntos actuales con el historico*/
                    resultadosFinales.get(i).setPartidasJugadas(temp.getPartidasJugadas() + 1);
                    double ratio = (double) (resultadosFinales.get(i).getPuntos() + temp.getPuntos()) / resultadosFinales.get(i).getPartidasJugadas();
                    resultadosFinales.get(i).setRatio(ratio);
                    System.out.println("juego > encontre al jugador");
                    actualizarRegistros(resultadosFinales.get(i));

                }
                else {
                    //jugador nuevo.
                    /* Actualizo el ratio de puntos por partida del jugador */
                    resultadosFinales.get(i).setPartidasJugadas(1);
                    resultadosFinales.get(i).setRatio(resultadosFinales.get(i).getPuntos());
                    System.out.println("juego > no encontre al jugador, es nuevo");

                    /* Si el archivo de datos esta vacio (primera partida) escribo la cabecera.*/
                    Jugador aux = resultadosFinales.get(i);
                    if (serializador.readObjects() == null) {
                        System.out.println("juego > no hay datos. escribo la cabecera");
                        serializador.writeOneObject(aux);

                    }
                    else {

                        System.out.println("juego > escribo el resto de jugadores");
                        serializador.addOneObject(aux);
                    }

                }
            }

        }
    }


    private Jugador buscarHistorialJugador(Jugador jugador) {
        Object[] datos = serializador.readObjects();
        if (datos != null) {

            System.out.println(datos.length);
            ArrayList<Jugador> recuperado = new ArrayList<>();

            for (int i = 0; i < datos.length; i++) {
                System.out.println("juego > recuperando a " + datos[i].toString());
                recuperado.add((Jugador)datos[i]);
                System.out.println("juego > recupere a " + recuperado.get(i).getNombre());
            }

            for (int j = 0; j < recuperado.size(); j++) {
                if(recuperado.get(j).getNombre().equals(jugador.getNombre())) {
                    return recuperado.get(j);
                }
            }
        }

        return null;
    }


    public ArrayList<IJugador> recuperarDatos() throws RemoteException {

        Object[] recuperado = serializador.readObjects();
        ArrayList<IJugador> datos = new ArrayList<>();

        for (int i = 0; i < recuperado.length; i++) {
            datos.add((IJugador)recuperado[i]);
        }
        for (int i = 0; i < recuperado.length; i++) {
            System.out.println(datos.get(i).getNombre() + datos.get(i).getRatio());
        }

        return datos;
    }


    private void actualizarRegistros(Jugador jugador) throws RemoteException {
        ArrayList<IJugador> modif = new ArrayList<>();
        ArrayList<IJugador> og = recuperarDatos();
        int indice = 0;
        while (indice < og.size()) {
           if (!og.get(indice).getNombre().equals(jugador.getNombre())) {

                modif.add(og.get(indice));
           }
           else {
               modif.add(jugador);
           }
           indice++;
        }

        serializador.borrarArchivo();
        serializador.writeOneObject(modif.get(0));
        for (int i = 1; i < modif.size(); i++) {

            serializador.addOneObject(modif.get(i));
        }

    }


    private void restablecerSesion() {

        pilaOro.reinicarPila();
        pilaEspada.reinicarPila();
        pilaBasto.reinicarPila();
        mazo.juntarCartas();
        cantidadJugadores = 0;
        jugadoresConectados = 0;
        jugadorActual = 0;
//        cantidadRondas = 0;
        rondaActual = 0;
        manosJugadas = 0;
        cartaJugadaTurnoActual = null;
        pilaActualizada = null;
        jugadores.clear();
        resultadosFinales.clear();

    }


    /**
     * Retorno el jugador del turno actual.
     * @return
     * @throws RemoteException
     */
    public IJugador obtenerJugadorActual() throws RemoteException {
        return jugadores.get(jugadorActual);
    }

}
