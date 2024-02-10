package modelo.juego;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.baraja.Carta;
import modelo.baraja.ICarta;
import modelo.baraja.Palo;
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

    ICarta getCartaJugadaTurnoActual() throws RemoteException;

    int getIndiceCartaJugadaTurnoActual() throws RemoteException;

    boolean isReiniciarPila() throws RemoteException;

    PilaPalo getPilaActualizada() throws RemoteException;

    List<Jugador> getResultadosFinales() throws RemoteException;


    void agregarJugador(String nombre) throws RemoteException;

    void iniciarJuego() throws RemoteException;

    void repartirCartas() throws RemoteException;

    void jugarRonda() throws RemoteException;

    void tirarCarta(ICarta carta, Palo pila) throws RemoteException;

    boolean verificarSumaPila(PilaPalo pila, int jugadorActual) throws RemoteException;

    void pasarTurno() throws RemoteException;

    void finPartida() throws RemoteException;

    Jugador obtenerJugadorActual() throws RemoteException;

    ArrayList<Jugador> recuperarDatos() throws RemoteException;

}
