package source.view_controller;


import java.io.IOException;
import java.io.InputStream;

public class AlgorytmSzyfrowanie {

    StringBuilder strBuid = new StringBuilder();
    private int textSize;
    private int passSize;
    private int dl_x = 0;
    private int dl_y = 0;
    private char[][] znaki;
    private char[][] result;
    private char PUSTY_ZNAK = 14;
    private static String KODOWANIE = "windows-1250";
    private String haslo;

    public StringBuilder szyfrowanie(InputStream is, String haslo)  {
        this.haslo = haslo;
        znaki = new char[dl_x][dl_y];
        result = new char[dl_y][dl_x];
        byte[] bytes = new byte[0];
        try {
            bytes = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textSize = bytes.length;
        passSize = haslo.length();

        nrKolumn();
        nrWierzszy();

        wypelnijTabele(bytes);
        podstawienie();
        obrocTabele();
        return pobierzDane();
    }



    private void obrocTabele() {
        for (int y = 0; y < dl_y; y++) {
            for (int x = 0; x < dl_x; x++) {
                this.result[y][x] = this.znaki[x][y];
            }
        }
    }

    private void wypelnijTabele(byte[] bytes) {
        int licznik = 0;
        for (int y = 0; y < dl_y; y++) {
            for (int x = 0; x < dl_x; x++) {
                if ((licznik < textSize)) {
                    this.znaki[x][y] = (char) bytes[licznik];
                    licznik++;
                } else
                    this.znaki[x][y] = PUSTY_ZNAK;
            }
        }
    }

    private StringBuilder pobierzDane() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < dl_x; y++) {
            for (int x = 0; x < dl_y; x+=2) {
                if (x + 1 < dl_y)
                    sb.append(result[x+1][y]);
                sb.append(result[x][y]);
            }
        }
        return sb;
    }

    private void podstawienie() {
        for (int y = 0; y < dl_y; y++) {
            for (int x = 0; x < dl_x; x++) {
                this.znaki[x][y] = permutuj(this.znaki[x][y], x, y);
            }
        }
    }

    private void nrKolumn() {
        if (textSize < (5 * passSize))
            dl_x = passSize;
        else if (textSize < (10 * passSize))
            dl_x = 5 * passSize;
        else
            dl_x = 10 * passSize;
    }

    private void nrWierzszy() {
        if (textSize % dl_x == 0)
            dl_y = textSize / dl_x;
        else
            dl_y = (textSize / dl_x) + 1;
    }

    private char permutuj(int znak, int x, int y) {
        return (char) ((znak + przesuniecie(x , y)) % 256);
    }

    private int przesuniecie(int x, int y) {
        return ((haslo.charAt(x % passSize) * x + passSize + y) % 256);
    }

}
