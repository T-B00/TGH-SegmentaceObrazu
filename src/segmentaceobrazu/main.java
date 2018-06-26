package segmentaceobrazu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.abs;
import static java.lang.Math.abs;

public class main {
    static int[] cesta;
    public static int[] komponenty;
    static int velikost, pocetVrcholu, pocetHran, i, j;

    public static void main(String args[]) throws IOException {
        int[] pole;

        File f = new File("a.txt");

        BufferedReader in = new BufferedReader(new FileReader(f));
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();// preskoceni radku
        velikost = Integer.parseInt(line);
        pole = new int[velikost * velikost];
        int index = 0;
        while ((line = in.readLine()) != null) {
            String[] casti = line.split(" ");
            for (int i = 0; i < velikost; i++) {
                pole[index] = Integer.parseInt(casti[i]);
                index++;
            }
        }
        pocetVrcholu = velikost * velikost;

        komponenty = new int[pocetVrcholu];
        for (int i = 0; i < komponenty.length; i++) {
            komponenty[i] = i;
        }

        pocetVrcholu = velikost * velikost;
        pocetHran = (velikost + velikost) * (velikost - 1);
        cesta = new int[pocetVrcholu + 1];
        Hrana e[] = new Hrana[pocetHran];
        Hrana t = new Hrana();
        int hrana = 0;
        for (int i = 1; i < pole.length; i++) {
            if (i % velikost != 0) {
                e[hrana] = new Hrana();
                e[hrana].odkud = i - 1;
                e[hrana].kam = i;
                e[hrana].vaha = abs(pole[i] - pole[i - 1]);
                hrana++;
            }
            if (i >= velikost) {
                e[hrana] = new Hrana();
                e[hrana].odkud = i - velikost;
                e[hrana].kam = i;
                e[hrana].vaha = abs(pole[i] - pole[i - velikost]);
                hrana++;
            }
        }

        // Bubble sort
        for (i = 0; i <= pocetHran - 1; i++) {
            for (j = 0; j < pocetHran - i - 1; j++) {
                if (e[j].vaha > e[j + 1].vaha) {
                    t = e[j];
                    e[j] = e[j + 1];
                    e[j + 1] = t;
                }
            }
        }

        for (i = 0; i < pocetVrcholu; i++) {
            cesta[i] = 0;
        }

        i = 0;
        j = 0;

        Hrana kostra[] = new Hrana[pocetVrcholu - 1];
        int max = 0;
        while ((i != pocetVrcholu - 1) && (j != pocetHran)) {
            if (jeCyklus(e[j])) {
                kostra[i] = new Hrana();
                kostra[i].odkud = e[j].odkud;
                kostra[i].kam = e[j].kam;
                kostra[i].vaha = e[j].vaha;
                max = e[j].vaha;
                i++;
            }
            j++;
        }

        for (int i = 0; i < kostra.length; i++) {
            if (max != kostra[i].vaha) {
                pridatHranu(kostra[i].odkud, kostra[i].kam);
            }
        }
        Vypis(komponenty, velikost);
    }

    public static boolean jeCyklus(Hrana e) {
        int u = e.odkud, v = e.kam;
        while (cesta[u] > 0) {
            u = cesta[u];
        }
        while (cesta[v] > 0) {
            v = cesta[v];
        }
        if (u != v) {
            cesta[u] = v;
            return true;
        }
        return false;
    }

    static class Hrana {
        int odkud, kam, vaha;
    }

    public static void Vypis(int[] pole, int delka) {
        System.out.println(delka);
        for (int i = 0; i < pole.length; i++) {
            if (pole[i] == 0) {
                System.out.printf("  0 ");
            } else {
                System.out.printf("  1 ");
            }
            if ((i + 1) % velikost == 0) {
                System.out.println("");
            }
        }
    }

    public static void pridatHranu(int index1, int index2) {
        int temp = komponenty[index1];
        int temp2 = komponenty[index2];
        for (int i = 0; i < komponenty.length; i++) {
            if (komponenty[i] == temp2) {
                komponenty[i] = temp;
            }
        }
    }
}
