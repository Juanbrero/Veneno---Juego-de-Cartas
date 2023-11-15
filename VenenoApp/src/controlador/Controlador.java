package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import eventos.Evento;
import modelo.baraja.Carta;
import modelo.juego.IJuego;
import modelo.jugador.Jugador;
import vista.IVista;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements IControladorRemoto {

    private IVista vista;
    private Jugador jugador;
    private int id;
    private IJuego juego;
    private boolean miTurno;


    public Controlador() {

    }

    public <T extends IObservableRemoto> Controlador(T modelo) {
        try {
            this.setModeloRemoto(modelo);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
                    if(juego.isReiniciarPila()) {
                        updateCartaJugada(juego.getIndiceCartaJugadaTurnoActual(), juego.getCartaJugadaTurnoActual(), juego.isReiniciarPila(), juego.getPilaAReiniciar(), juego.getJugadores().get(this.id).getPuntos());
                    }
                    else {
                        updateCartaJugada(juego.getIndiceCartaJugadaTurnoActual(), juego.getCartaJugadaTurnoActual(), juego.isReiniciarPila() , "", 0);
                    }
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
    public void conectarse(String nombre) {

        try {
            System.out.println("controlador > me voy a conectar como jugador " + nombre);
            this.jugador = juego.agregarJugador(nombre);
            this.id = jugador.getId();
            System.out.println("controlador > id this.jugador = " + jugador);
            System.out.println("controlar > soy " + jugador.getNombre());
            if(juego.getJugadoresConectados() == juego.getCantidadJugadores()) {
                System.out.println("controlador > jugar ronda");
                juego.iniciarJuego();
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Recibe la carta a tirar y se la pasa al modelo.
     * @param carta
     * @param palo
     */
    public void tirarCarta(int carta, String palo) {
        try {
            if (miTurno) {
                juego.tirarCarta(carta, palo);
            }
            else {
                vista.mostrarMensaje("Turno del jugador " + juego.obtenerJugadorActual().getNombre() + "!");
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    /**
     * Si faltan jugadores por conectarse, creo una cola de espera.
     */
    private void updateJugadorConectado() {

        try {
            if (this.juego.getJugadoresConectados() < this.juego.getCantidadJugadores()) {
                /* Si faltan jugadores por conectarse agrego al jugador actual a una cola de espera */
                System.out.println("controlador > en cola de espera");
                vista.colaDeEspera(this.juego.getJugadoresConectados(), this.juego.getCantidadJugadores());
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

        int i = 0;
        try {

            if (!this.jugador.getCartasEnMano().isEmpty()){

                System.out.println("controlador > reinicio la mano del jugador");
                vista.reiniciarMano();
            }

            this.jugador.recibirCartas(juego.getJugadores().get(this.id).getCartasEnMano());


            System.out.println("controlador > soy " + jugador.getNombre());

            System.out.println("el jugador tiene " + this.jugador.getCartasEnMano().size() + " cartas");
            System.out.println("controlador > se reparten las cartas");
            for (Carta c : this.jugador.getCartasEnMano()) {  // CONTROLAR ESTA PARTE QUE NO ENTRA
                System.out.println("Controlador recibe carta " + c.getNro() + " " + c.getPalo() + "en indice " + i);
                vista.generarCarta(c.getPalo().toString(), c.getNro(), i);
                i++;
            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }

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

    private void updateCartaJugada(int cartaJugada, Carta carta, boolean reiniciarPila, String pilaAReiniciar, int puntos) {

        try {
            if (miTurno) {

                /* Actualizo la informacion del jugador luego de jugar el turno. */
                this.jugador = juego.getJugadores().get(this.id);

                vista.tirarCarta(cartaJugada);
                /* Si al tirar la carta me toca levantar la pila de la mesa */
                if (reiniciarPila) {
                    this.vista.levantarCartas(puntos);
                }
            }
            if (!reiniciarPila) {

                vista.agregarCartaEnMesa(carta.getPalo().toString(),carta.getValor());
            }
            else {
                vista.reiniciarPila(pilaAReiniciar);
            }

//            juego.pasarTurno();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }







}