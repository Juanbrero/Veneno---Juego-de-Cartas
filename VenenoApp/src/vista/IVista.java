package vista;

public interface IVista {
    
    void iniciar();

    void colaDeEspera(int conectados, int jugadores);

    void iniciarPartida();

    void generarCarta(String string, int nro, int i);

    void reiniciarMano();

    void jugarTurno();

    void mostrarMensaje(String s);

    void tirarCarta(int cartaJugada);

    void agregarCartaEnMesa(String string, double valor);

    void reiniciarPila(String pilaAReiniciar);

    void levantarCartas(int puntos);
}
