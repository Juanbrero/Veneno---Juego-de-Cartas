package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import eventos.Evento;
import modelo.baraja.ICarta;
import modelo.baraja.IPilaPalo;
import modelo.baraja.Palo;
import modelo.baraja.PilaPalo;
import modelo.juego.IJuego;
import modelo.jugador.IJugador;
import modelo.jugador.Jugador;
import vista.IVista;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controlador implements IControladorRemoto, Serializable {

    private IVista vista;
    private IJugador jugador;
    private int id;
    private IJuego juego;
    private boolean miTurno;


    public Controlador() {

    }

    public int getId() {
        return id;
    }

    public void setVista(IVista vista) {
        this.vista = vista;
    }


    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.juego = (IJuego) modeloRemoto;
    }


    @Override
    public void actualizar(IObservableRemoto modelo, Object evento) throws RemoteException {

        if (evento instanceof Evento) {
            switch ((Evento) evento) {
                case JUGADOR_CONECTADO:
                    updateJugadorConectado();
                    break;
                case INICIO_RONDA:
                    updateInicioRonda();
                    break;
                case START:
                    vista.iniciarPartida();
                    break;
                case TURNO:
                    updateTurno();
                    break;
                case CARTA_JUGADA:
                    updateCartaJugada(juego.getCartaJugadaTurnoActual(), juego.getPilaActualizada());
                    break;
                case VENENO:
                    updateSeleccionarPila();
                    break;
                case FIN_JUEGO:
                    updateFinJuego(this.juego.getResultadosFinales());
                    break;
            }
        }

    }


    /**
     * Seteo la cantidad de jugadores de la partida
     * @param cantidad
     */
    public void cantidadJugadores(int cantidad) {

        try {
            this.juego.setCantidadJugadores(cantidad);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Conecta el controlador al modelo. Guarda la referencia a la instancia de jugador creada en el modelo.
     * @param nombre
     */
    public IJugador conectarse(String nombre) {

        try {
            this.id = juego.getJugadoresConectados();
            System.out.println("controlador > me voy a conectar como jugador " + nombre + " id: " + this.id);
            juego.agregarJugador(nombre);
            System.out.println("controlador > soy " + jugador.getNombre() + ", id: " + jugador.getId());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

        return this.jugador;
    }

    public void inicio() throws RemoteException {
        juego.iniciarJuego();
    }


    /**
     * Recibe la carta a tirar y se la pasa al modelo.
     * @param carta
     */
    public void tirarCarta(ICarta carta) {
        try {
            if (miTurno) {
                juego.tirarCarta(carta, carta.getPalo());
            }
            else {
                vista.mostrarMensaje("Turno del jugador " + juego.obtenerJugadorActual().getNombre() + "!\n");
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void envenenar(Palo pila) {
        try {
            if (miTurno) {
                juego.tirarCarta(juego.getCartaJugadaTurnoActual(), pila);
            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void verEstadisticas() {

        try {
            List<Jugador> temp;
            temp = juego.recuperarDatos().stream().sorted(Comparator.comparingDouble(Jugador::getRatio)).toList();
            ArrayList<String> resultados = new ArrayList<>();

            for (int i = 0; i < temp.size(); i++) {
                resultados.add("#" + (i+1) + " Player " + temp.get(i).getNombre() + "     Ratio puntos/partida: " + temp.get(i).getRatio() + "   Partidas jugadas: " + temp.get(i).getPartidasJugadas());
            }

            vista.ranking(resultados);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /* ------------- Metodos de actualizacion  ------------ */


    /**
     * Si faltan jugadores por conectarse, creo una cola de espera.
     */
    private void updateJugadorConectado() {

        try {
            this.jugador = juego.getJugadores().get(this.id);
            System.out.println("controlador > ya me conecte como " + jugador.getNombre());
            if (this.juego.getJugadoresConectados() < this.juego.getCantidadJugadores()) {
                /* Si faltan jugadores por conectarse agrego al jugador actual a una cola de espera */
                System.out.println("controlador > " + this.jugador.getNombre() + " en cola de espera... " + this.juego.getJugadoresConectados() + " / "+ this.juego.getCantidadJugadores());

                /* Solo agrego a la cola de espera cuando le dan al boton start.*/
                if((this.juego.getJugadoresConectados() - 1 ) == this.id){

                    vista.colaDeEspera(this.juego.getJugadoresConectados(), this.juego.getCantidadJugadores());
                }
            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * Informa el inicio de cada ronda. Genera las cartas correspondientes a cada jugador
     */
    private void updateInicioRonda() {

        try {

            if (!this.jugador.getCartasEnMano().isEmpty()){

                System.out.println("controlador > reinicio la mano del jugador");
                vista.reiniciarMano();
            }

            this.jugador = juego.getJugadores().get(this.id);

            System.out.println("controlador > soy " + jugador.getNombre());

            System.out.println("el jugador tiene " + this.jugador.getCartasEnMano().size() + " cartas");
            System.out.println("controlador > se generan las cartas graficas");

            vista.generarCartas(this.jugador.getCartasEnMano());

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * Informa al jugador sobre el turno actual.
     */
    private void updateTurno() {

        try {
            if (this.juego.obtenerJugadorActual().getId() == this.jugador.getId()) {
                miTurno = true;
                System.out.println("controlador > tu turno!");
                vista.jugarTurno();
            }
            else {
                miTurno = false;
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Informa sobre la carta jugada en el turno actual sacandola de la mano.
     * Determina si se debe agregar la carta a la mesa o se debe reiniciar la y sumar puntos.
     * @param carta
     * @param pilaActualizada
     */
    private void updateCartaJugada(ICarta carta, IPilaPalo pilaActualizada) {

        try {
            System.out.println("controlador > pila a actualizar: " + pilaActualizada.getPalo().toString());
            if (miTurno) {

                /* Actualizo la informacion del jugador luego de jugar el turno. */
                this.jugador = juego.getJugadores().get(this.id);

                vista.tirarCarta(carta);
                /* Si al tirar la carta me toca levantar la pila de la mesa */
                if (pilaActualizada.isReiniciar()) {
                    System.out.println("controlador > updateCartaJugada: " + this.jugador.getPuntosALevantar() + " puntos ");
                    this.vista.levantarCartas(this.jugador.getPuntosALevantar());
                }
            }
            /* Actualizacion para todos los jugadores sobre la pila a actualizar.*/
            if (pilaActualizada.isReiniciar()) {

                vista.reiniciarPila(pilaActualizada);
            }
            else {
                vista.agregarCartaEnMesa(pilaActualizada);
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void updateSeleccionarPila() {

        if(miTurno) {
            vista.envenenar();
        }
    }


    private void updateFinJuego(List<Jugador> resultadosFinales) {

        ArrayList<String> resultados = new ArrayList<>();
        int posicion = 1;
        for (Jugador j : resultadosFinales) {
            resultados.add("#" + posicion + " - " + j.getNombre() + " <" + j.getPuntos() + "> pts.");
            posicion++;
        }

        vista.finJuego(resultados);
    }

}
