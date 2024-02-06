package vista;

import modelo.baraja.Carta;
import modelo.jugador.IJugador;

import java.util.ArrayList;

public interface IVista {

    void iniciar();

    void colaDeEspera(int conectados, int jugadores);

    void iniciarPartida();

    void generarCartas(ArrayList<Carta> cartas);

    void reiniciarMano();

    void jugarTurno();

    void mostrarMensaje(String s);

    void tirarCarta(int cartaJugada);

    void agregarCartaEnMesa(String string, double valor);

    void reiniciarPila(String pilaAReiniciar);

    void levantarCartas(int puntos);

    void finJuego(ArrayList<String> resultados);

    void restablecerSesion();

    void ranking(ArrayList<String> resultados);
}
