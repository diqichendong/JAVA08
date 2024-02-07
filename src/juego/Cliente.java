package juego;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    private static final int ANCHO = 600;
    private static final int ALTO = 600;
    private static final String HOST = "localhost";
    private static final int PUERTO = 4444;


    public static void main(String[] args) throws IOException {
        int jugador;
        Socket socket = new Socket(HOST, PUERTO);
        System.out.println("Esperando un oponente...");

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        // Bucle buscar oponente
        while (true) {
            try {
                if (ois.readObject().toString().equals("READY")) {
                    jugador = ois.readInt();
                    System.out.println("Jugando jugador" + jugador);
                    break;
                }
            } catch (ClassNotFoundException e) {
            }
        }

        // Ventana
        JFrame frame = new JFrame("Tres en raya");
        PanelJuego juego = new PanelJuego(jugador);
        frame.add(juego);
        frame.setSize(ANCHO, ALTO);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Bucle juego
        while (true) {
            try {
                // Datos rival
                Tablero tablero = (Tablero) ois.readObject();
                juego.setTablero(tablero);
                juego.setTurno(true);

                // Comprobar rival ganador
                if (tablero.getGanador() != 0 && tablero.getGanador() != jugador){
                    JOptionPane.showMessageDialog(frame, "Has perdido...");
                    System.exit(0);
                }

                // Esperar movimiento
                while(juego.isTurno()){}

                // Obtener tablero nuevo
                tablero = juego.getTablero();

                // Comprobar ganador
                oos.writeObject(juego.getTablero());
                oos.flush();
                if (tablero.getGanador() != 0 && tablero.getGanador() == jugador) {
                    JOptionPane.showMessageDialog(frame, "¡HAS GANADO!");
                    System.exit(0);
                }

            } catch (ClassNotFoundException e) {
            } catch (EOFException e) {}
        }
    }
}
