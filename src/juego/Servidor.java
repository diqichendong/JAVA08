package juego;

import java.io.IOException;
import java.net.*;

public class Servidor {

    private static final int PUERTO = 4444;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(PUERTO);
            System.out.println("Servidor OK.");
            while (true) {
                new HiloServidor(ss.accept(), ss.accept()).start();
            }
        } catch (IOException ex) {
            System.err.println("No se ha podido conectar al servidor.");
        }
    }
}
