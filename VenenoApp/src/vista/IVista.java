package vista;

public interface IVista {
    
    void iniciar();

    void colaDeEspera(int conectados, int jugadores);

    void iniciarPartida();

    void generarCarta(String string, int nro, int i);

    void reiniciarMano();

    void jugarTurno();
}
