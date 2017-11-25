package source.view_controller;

import java.io.*;

public class Printing {

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

    public StringBuilder szyfrowanie(InputStream is, String haslo) {
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

        strBuid.append("D³ugoœæ has³a:  " + passSize);
        strBuid.append("\n");
        strBuid.append("D³ugoœæ tekstu: " + textSize);
        strBuid.append("\n");
        strBuid.append("\n");
        strBuid.append("Haslo\n");
        for (int i = 0; i < dl_x; i++) {
            strBuid.append(String.format("%3s ", haslo.charAt(i % passSize)));
        }
        strBuid.append("\n");

        wypelnijTabele(bytes);

        printTab(this.znaki, dl_x, dl_y, "Tekst szyfrowany");

        strBuid.append("x: " + dl_x + "\n");
        strBuid.append("y: " + dl_y + "\n");

        printPrzestawienie(this.znaki, dl_x, dl_y, "Tabela po podstawieniu (permutacji)");

        podstawienie();

        obrocTabele();
        printTab(result, dl_y, dl_x, "Tabela po obróceniu");

        strBuid.append("Dane pobrane z tabeli: \n");
        StringBuilder sb = pobierzDane();
        strBuid.append(sb);
        strBuid.append("\n");

        try {
            writeFile("C:\\Users\\Kitsunee\\Desktop\\wynik.txt", strBuid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    public static void writeFile(String path, String text) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(path)), KODOWANIE);
        try {
            writer.write(text);
        } finally {
            writer.close();
        }
    }

    private void printTab(char[][] tab, int X, int Y, String tytul) {
        strBuid.append(tytul + "\n");
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3s ", tab[x][y]));
            }
            strBuid.append("\n");
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3d ", (int) tab[x][y]));
            }
            strBuid.append("\n");

        }
        strBuid.append("\n");
    }


    private void printPrzestawienie(char[][] tab, int X, int Y, String tytul) {
        strBuid.append(tytul + "\n");
        StringBuilder sba = new StringBuilder();
        strBuid.append(String.format("%25s", "Znak has³a: "));
        for (int x = 0; x < X; x++) {
            strBuid.append(String.format("%3c ", haslo.charAt(x % passSize)));
        }
        strBuid.append("\n");
        strBuid.append(String.format("%25s", "Nr. znaku has³a: "));
        for (int x = 0; x < X; x++) {
            strBuid.append(String.format("%3d ", (int) haslo.charAt(x % passSize)));
        }
        strBuid.append("\n");
        for (int y = 0; y < Y; y++) {
            strBuid.append(String.format("%25s", "Znak wiadomoœci: "));
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3s ", tab[x][y]));
            }
            strBuid.append("\n");
            strBuid.append(String.format("%25s", "Nr. znaku wiadomoœci: "));
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3d ", (int) tab[x][y]));
            }
            strBuid.append("\n");
            strBuid.append(String.format("%25s", "Wartoœæ przesuniêcia: "));
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3d ", przesuniecie(x, y)));
                sba.append(drukLinie(this.znaki[x][y], (int) haslo.charAt(x % passSize), x, y));
            }
            strBuid.append("\n");
            strBuid.append(String.format("%25s", "Znak zaszyfrowany: "));
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3c ", permutuj(this.znaki[x][y], x, y)));
            }
            strBuid.append("\n");
            strBuid.append(String.format("%25s", "Nr. znaku zaszyfr.: "));
            for (int x = 0; x < X; x++) {
                strBuid.append(String.format("%3d ", (int) permutuj(this.znaki[x][y], x, y)));
            }
            strBuid.append("\n\n");
        }
        strBuid.append("\n");
        strBuid.append(sba.toString());
        strBuid.append("\n");
    }

    String drukLinie(int znak, int znakHasla, int x, int y) {
        return (char) znak + " = p(" + String.format("%2d", x) + "," + String.format("%2d", y) + ") = (" + String.format("%3d ", znak) + " + ((" + String.format("%3d ", znakHasla) +
                " * " + String.format("%2d", x) + " + " + passSize + " + " + String.format("%2d", y) + ") % 256)) % 256 = " + String.format("%3d ", (int) permutuj(znak, x, y)) +
                " -> " + permutuj(znak, x, y) + "\n";
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
            for (int x = 0; x < dl_y; x += 2) {
                if (x + 1 < dl_y)
                    sb.append(result[x + 1][y]);
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
        return (char) ((znak + przesuniecie(x, y)) % 256);
    }

    private int przesuniecie(int x, int y) {
        return ((haslo.charAt(x % passSize) * x + passSize + y) % 256);
    }


}
