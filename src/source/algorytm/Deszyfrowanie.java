package source.algorytm;


import static source.Main.PUSTY_ZNAK;

public class Deszyfrowanie {

    private int textSize;
    private int passSize;
    private int dl_x = 0;
    private int dl_y = 0;
    private char[][] znaki;
    private char[][] result;
    private String haslo;

    public StringBuilder deszyfrowanie(String text, String haslo) {
        this.haslo = haslo;
        textSize = text.length();
        passSize = haslo.length();

        nrKolumn();
        nrWierzszy();
        znaki = new char[dl_x][dl_y];
        result = new char[dl_y][dl_x];

        wypelnijTabele(text);
        obrocTabele();
        podstawienie();
        return pobierzDane();
    }

    private void obrocTabele() {
        for (int y = 0; y < dl_y; y++) {
            for (int x = 0; x < dl_x; x++) {
                this.result[y][x] = this.znaki[x][y];
            }
        }
    }

    private void wypelnijTabele(String text) {
        int i =0;
        for (int y = 0; y < dl_x; y++) {
            for (int x = 0; x < dl_y; x++) {
                if (x + 1 < dl_y) {
                    this.znaki[y][x + 1] = text.charAt(i++);
                }
                this.znaki[y][x++] = text.charAt(i++);
            }
        }
    }

    private StringBuilder pobierzDane() {
        StringBuilder sb = new StringBuilder();
            for (int x = 0; x < dl_y; x++) {
        for (int y = 0; y < dl_x; y++) {
                int i = 0;
                if (result[x][y]==PUSTY_ZNAK)
                    continue;
                sb.append(result[x][y]);
            }
        }
        return sb;
    }

    private void podstawienie() {
        for (int y = 0; y < dl_y; y++) {
            for (int x = 0; x < dl_x; x++) {
                this.result[y][x] = permutuj(this.result[y][x], x, y);
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
        int per = znak - przesuniecie(x, y);
        return (char) (per<0 ? per+256 : per);
    }

    private int przesuniecie(int x, int y) {
        return ((haslo.charAt(x % passSize) * x + passSize + y) % 256);
    }

}
