package cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
import vista.IVista;
import vista.consola.VistaConsola;
import vista.grafica.VistaInicio;

public class AppCliente {
    
    public static void main(String[] args) {

        final ArrayList<String>[] ips = new ArrayList[]{Util.getIpDisponibles()};
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips[0].toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );

        /**
         * Crea las opciones para elegir entre modo consola y modo grafico.
         */

        String modo  = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el modo de juego (Grafico [1]/Consola [2]", "Modo de juego",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                1
        );

        Random generador = new Random();
        int num = generador.nextInt();
        System.out.println(num);
        Controlador controlador = new Controlador(num);
        System.out.println("cliente > id controlador: " + controlador);

        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        if(modo.equals("1")) {

            IVista iVista = new VistaInicio(controlador);
            iVista.iniciar();
        }
        else {
            IVista iVista = new VistaConsola(controlador);
            iVista.iniciar();
        }
        try {
            c.iniciar(controlador);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
