package juego;

import java.io.Serializable;

public class Tablero implements Serializable {
    private int[][] tablero;

    public Tablero() {
        tablero = new int[3][3];
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setMovimientoJugador1(int f, int c) {
        this.tablero[f][c] = 1;
    }

    public void setMovimientoJugador2(int f, int c) {
        this.tablero[f][c] = 2;
    }

    public boolean isCasillaLibre(int f, int c) {
        return tablero[f][c] == 0;
    }

    public int getGanador() {
        // Horizontales
        if (tablero[0][0] != 0 && tablero[0][0] == tablero[0][1] && tablero[0][0] == tablero[0][2]) return tablero[0][0];
        if (tablero[1][0] != 0 && tablero[1][0] == tablero[1][1] && tablero[1][0] == tablero[1][2]) return tablero[1][0];
        if (tablero[2][0] != 0 && tablero[2][0] == tablero[2][1] && tablero[2][0] == tablero[2][2]) return tablero[2][0];

        // Verticales
        if (tablero[0][0] != 0 && tablero[0][0] == tablero[1][0] && tablero[0][0] == tablero[2][0]) return tablero[0][0];
        if (tablero[0][1] != 0 && tablero[0][1] == tablero[1][1] && tablero[0][1] == tablero[2][1]) return tablero[0][1];
        if (tablero[0][2] != 0 && tablero[0][2] == tablero[1][2] && tablero[0][2] == tablero[2][2]) return tablero[0][2];

        // Diagonales
        if (tablero[0][0] != 0 && tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2]) return tablero[0][0];
        if (tablero[0][2] != 0 && tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0]) return tablero[0][2];


        return 0;
    }

    public boolean isEmpate() {
        boolean res = true;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == 0) {
                    res = false;
                    break;
                }
            }
        }
        return getGanador() == 0 && res;
    }
}
