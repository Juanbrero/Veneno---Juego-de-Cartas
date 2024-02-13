package modelo.juego;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.baraja.*;
import modelo.jugador.IJugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IJuego extends IObservableRemoto {

    void setCantidadJugadores(int cantidadJugadores) throws RemoteException;

    int getCantidadJugadores() throws RemoteException;

    ArrayList<IJugador> getJugadores() throws RemoteException;

    int getJugadoresConectados() throws RemoteException;

    ICarta getCartaJugadaTurnoActual() throws RemoteException;

    IPilaPalo getPilaActualizada() throws RemoteException;

    List<IJugador> getResultadosFinales() throws RemoteException;


    void agregarJugador(String nombre) throws RemoteException;

    void iniciarJuego() throws RemoteException;

    void repartirCartas() throws RemoteException;

    void jugarRonda() throws RemoteException;

    void tirarCarta(ICarta carta, Palo pila) throws RemoteException;

    IJugador obtenerJugadorActual() throws RemoteException;

    ArrayList<IJugador> recuperarDatos() throws RemoteException;

}
