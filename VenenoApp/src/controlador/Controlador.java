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









}
