package juego;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {

    private JButton[][] botones;
    private Tablero tablero;
    private boolean turno;
    private int jugador;

    public PanelJuego(int jugador) {
        initComponents();
        this.jugador = jugador;
    }

    private void initComponents() {
        this.setLayout(new GridLayout(3, 3));
        botones = new JButton[3][3];
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                botones[i][j] = new JButton();
                int finalI = i, finalJ = j;
                botones[i][j].addActionListener(e -> {
                    if (turno && tablero.isCasillaLibre(finalI, finalJ)) {
                        if (jugador == 1) {
                            tablero.setMovimientoJugador1(finalI, finalJ);
                            turno = !turno;
                        } else {
                            tablero.setMovimientoJugador2(finalI, finalJ);
                            turno = !turno;
                        }
                        pintarTablero();
                    }
                });
                botones[i][j].setFont(new Font("Arial", Font.PLAIN, 100));
                this.add(botones[i][j]);
            }
        }
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
        pintarTablero();
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    private void pintarTablero() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                switch (tablero.getTablero()[i][j]) {
                    case 0: botones[i][j].setText(""); break;
                    case 1: botones[i][j].setText("X"); botones[i][j].setForeground(Color.BLUE); break;
                    case 2: botones[i][j].setText("O"); botones[i][j].setForeground(Color.RED); break;
                }
            }
        }
    }
}
