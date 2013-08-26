import org.fusesource.jansi.AnsiConsole;
import print.color.Ansi;
import print.color.ColoredPrinter;

import java.util.Scanner;

public class KolkoIKrzyzyk {

    private static ColoredPrinter cp;

    public static void main(String[] age) {

        int liczbaDobrychRuchow = 0;
        String graczX = "GraczX";
        String graczO = "GraczO";
        int numerPola = 1;
        int x;
        int y;
        int nr;
        int numerRundy = 1;
        boolean padlaWygrana = true;
        String[][] pola = new String[3][3];
        Scanner sc = new Scanner(System.in);

        AnsiConsole.systemInstall();  //zainstalowanie obsługi kolorków
        cp = new ColoredPrinter.Builder(1, false).build(); //stworzenie obiektu printera do pisania na kolorowo

        cp.print("Podaj imie gracza ktory gra krzyzykami:", Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.GREEN);
        cp.clear();//resetuje ustawienia kolorów
        graczX = sc.next();
        cp.print("Podaj imie gracza ktory gra kolkami:", Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.GREEN);
        cp.clear();
        graczO = sc.next();
        System.out.println("Graczami są: " + graczX + " oraz " + graczO);
        czyscPola(pola);
        wyswietlPola(pola);
        while (padlaWygrana) {
            if (liczbaDobrychRuchow > 8) break;
            System.out.println("Gracz " + graczX + " zaczyna runde numer " + numerRundy + "." + "[X]");
            System.out.println("Podaj numer pola.");
            nr = sc.nextInt();
            y = (nr - 1) % 3;
            x = (nr - 1) / 3;
            if ("_".equals(pola[x][y])) {
                pola[x][y] = "X";
                liczbaDobrychRuchow++;
                wyswietlPola(pola);
            } else {
                System.out.println("Podales zle pole, tracisz kolejke.");
            }
            if ("X".equals(ktoWygral(pola))) {
                System.out.println("Gracz " + graczX + " tym razem okazal sie lepszy.");
                System.out.println("Wygral!!!");
                padlaWygrana = false;
            } else {
                if (liczbaDobrychRuchow > 8) break;
                System.out.println("Gracz " + graczO + " zaczyna runde numer " + numerRundy + "." + "[O]");
                System.out.println("Podaj numer pola.");
                nr = sc.nextInt();
                y = (nr - 1) % 3;
                x = (nr - 1) / 3;
                if (pola[x][y].equals("_")) {
                    pola[x][y] = "O";
                    wyswietlPola(pola);
                    liczbaDobrychRuchow++;
                } else {
                    System.out.println("Podales zle pole, tracisz kolejke.");
                }
                if ("O".equals(ktoWygral(pola))) {
                    System.out.println("Gracz " + graczO + " tymrazem okazal sie leprzy.");
                    System.out.println("Wygral!!!");
                    padlaWygrana = false;
                } else {
                    numerRundy++;
                }
            }
        }
        if (padlaWygrana) System.out.println("Padl remis, nikt nie wygral.");
    }

    private static void wyswietlPola(String[][] pola) {
        int numeroweniPol = 1;
        for (int i = 0; i < 3; i++) {
            for (int g = 1; g < 4; g++) {
                System.out.print(numeroweniPol + " ");
                numeroweniPol++;
            }
            System.out.print("        ");
            for (int j = 0; j < 3; j++) {
                String znak = pola[i][j];
                switch (znak) {
                    case "X":
                        cp.print(znak + " ", Ansi.Attribute.BOLD, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
                        cp.clear();
                        break;
                    case "O":
                        cp.print(znak + " ", Ansi.Attribute.BOLD, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                        cp.clear();
                        break;
                    default:
                        cp.clear();
                        cp.print(znak + " ");
                }

            }
            System.out.println();
        }
    }

    private static String ktoWygral(String[][] pola) {

        for (int numerWiersza = 0; numerWiersza < 3; numerWiersza++) {
            String symbol = "X";
            if (czyWygralW(pola, numerWiersza, symbol)) return symbol;
            symbol = "O";
            if (czyWygralW(pola, numerWiersza, symbol)) return symbol;
        }
        for (int numerKolumny = 0; numerKolumny < 3; numerKolumny++) {
            String symbol = "X";
            if (czyWygralK(pola, numerKolumny, symbol)) return symbol;
            symbol = "O";
            if (czyWygralK(pola, numerKolumny, symbol)) return symbol;
        }
        if (pola[0][0].equals("X") && pola[1][1].equals("X") && pola[2][2].equals("X")) {
            return "X";
        }
        if (pola[0][0].equals("O") && pola[1][1].equals("O") && pola[2][2].equals("O")) {
            return "O";
        }
        if (pola[0][2].equals("X") && pola[1][1].equals("X") && pola[2][0].equals("X")) {
            return "X";
        }
        if (pola[0][2].equals("O") && pola[1][1].equals("O") && pola[2][0].equals("O")) {
            return "O";
        }
        return null;
    }

    private static boolean czyWygralW(String[][] pola, int nrW, String symbol) {

        if (pola[nrW][0].equals(symbol) && pola[nrW][1].equals(symbol) && pola[nrW][2].equals(symbol)) {
            return true;
        }
        return false;
    }

    private static boolean czyWygralK(String[][] pola, int nrK, String symbol) {

        if (pola[0][nrK].equals(symbol) && pola[1][nrK].equals(symbol) && pola[2][nrK].equals(symbol)) {
            return true;
        }
        return false;
    }

    private static void czyscPola(String[][] pola) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pola[i][j] = "_";
            }
        }
    }
}