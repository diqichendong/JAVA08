package juego;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class HiloServidor extends Thread {

    private Socket j1, j2;
    private boolean jugando, turnoJ1;
    private Tablero tablero;


    public HiloServidor(Socket j1, Socket j2) {
        this.j1 = j1;
        this.j2 = j2;
        this.jugando = true;
        this.turnoJ1 = true;
        this.tablero = new Tablero();
    }

    @Override
    public void run() {
        ObjectOutputStream oos1, oos2;
        ObjectInputStream ois1, ois2;
        try {
            oos1 = new ObjectOutputStream(j1.getOutputStream());
            oos2 = new ObjectOutputStream(j2.getOutputStream());
            ois1 = new ObjectInputStream(j1.getInputStream());
            ois2 = new ObjectInputStream(j2.getInputStream());

            oos1.writeObject("READY");
            oos1.flush();
            oos1.writeInt(1);
            oos1.flush();
            oos2.writeObject("READY");
            oos2.flush();
            oos2.writeInt(2);
            oos2.flush();
            oos1.writeObject(tablero);
            oos1.flush();

            while (true) {
                if (turnoJ1) {
                    tablero = (Tablero) ois1.readObject();
                    turnoJ1 = !turnoJ1;
                    oos2.writeObject(tablero);
                    oos2.flush();
                } else {
                    tablero = (Tablero) ois2.readObject();
                    turnoJ1 = !turnoJ1;
                    oos1.writeObject(tablero);
                    oos1.flush();
                }

                if (tablero.getGanador() != 0) {
                    break;
                }
            }
        } catch (SocketException e) {
        } catch (EOFException e) {
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
