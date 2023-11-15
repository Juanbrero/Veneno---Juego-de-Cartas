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
            }
        }

    }


    public void cantidadJugadores(int cantidad) {

        try {
            this.juego.setCantidadJugadores(cantidad);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void conectarse(String nombre) {

        try {
            System.out.println("controlador > me voy a conectar como jugador " + nombre);
            this.jugador = juego.agregarJugador(nombre);
            System.out.println("controlar > soy " + jugador.getNombre());

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    private void updateJugadorConectado() {

        try {
            if (this.juego.getJugadoresConectados() < this.juego.getCantidadJugadores()) {
                /* Si faltan jugadores por conectarse agrego al jugador actual a una cola de espera */
                System.out.println("controlador > en cola de espera");
                vista.colaDeEspera(this.juego.getJugadoresConectados(), this.juego.getCantidadJugadores());
            }
            else if (this.juego.getJugadoresConectados() == this.juego.getCantidadJugadores()) {
                /* Cuando ingresan todos los jugadores termino la cola de espera e inicia la partida */
                System.out.println("controlador > arranca");
                
                vista.iniciarPartida();

            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void updateInicioRonda() {

        int i = 0;

        if (!this.jugador.getCartasEnMano().isEmpty()){

            vista.reiniciarMano();
        }

        System.out.println("controlador > se reparten las cartas");
        for (Carta c : this.jugador.getCartasEnMano()) {  // CONTROLAR ESTA PARTE QUE NO ENTRA
            System.out.println("Controlador recibe carta " + c.getNro() + " " + c.getPalo() + "en indice " + i);
            vista.generarCarta(c.getPalo().toString(), c.getNro(), i);
            i++;
        }

    }









}
