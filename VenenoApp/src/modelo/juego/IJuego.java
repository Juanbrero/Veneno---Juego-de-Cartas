package modelo.juego;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.baraja.Carta;
import modelo.baraja.PilaPalo;
import modelo.jugador.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IJuego extends IObservableRemoto {

    void setCantidadJugadores(int cantidadJugadores) throws RemoteException;

    int getCantidadJugadores() throws RemoteException;

    ArrayList<Jugador> getJugadores() throws RemoteException;

    int getJugadoresConectados() throws RemoteException;

    void setCantidadRondas() throws RemoteException;

    Carta getCartaJugadaTurnoActual() throws RemoteException;

    int getIndiceCartaJugadaTurnoActual() throws RemoteException;

    boolean isReiniciarPila() throws RemoteException;

    String getPilaAReiniciar() throws RemoteException;

    List<Jugador> getResultadosFinales() throws RemoteException;


    Jugador agregarJugador(String nombre) throws RemoteException;

    Jugador obtenerJugadorActual() throws RemoteException;

    void jugarRonda() throws RemoteException;

}
