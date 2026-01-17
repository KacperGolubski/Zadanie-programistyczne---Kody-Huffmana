package org.example;

import org.example.CustomPriorityQueue;
import org.example.HuffmanCoder;
import org.example.HuffmanNode;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HuffmanCoder coder = new HuffmanCoder();
        boolean running = true;

        System.out.println("=== PROGRAM DO KODOWANIA HUFFMANA ===");

        while (running) {
            System.out.println("\nWYBIERZ OPCJE:");
            System.out.println("1. Demo Kolejki Priorytetowej");
            System.out.println("2. Kompresuj plik");
            System.out.println("3. Dekompresuj plik");
            System.out.println("4. Wyjdź");
            System.out.print("Twój wybór: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runQueueDemo(scanner);
                    break;
                case "2":
                    System.out.print("Podaj nazwę pliku do kompresji (np. dane.txt): ");
                    String inFileC = scanner.nextLine();
                    System.out.print("Podaj nazwę pliku wynikowego (np. wynik.huff): ");
                    String outFileC = scanner.nextLine();
                    coder.compressFile(inFileC, outFileC);
                    break;
                case "3":
                    System.out.print("Podaj nazwę pliku do dekompresji (np. wynik.huff): ");
                    String inFileD = scanner.nextLine();
                    System.out.print("Podaj nazwę pliku wyjściowego (np. odzyskany.txt): ");
                    String outFileD = scanner.nextLine();
                    coder.decompressFile(inFileD, outFileD);
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }
        scanner.close();
    }

    private static void runQueueDemo(Scanner scanner) {
        CustomPriorityQueue demoQueue = new CustomPriorityQueue(10);
        boolean inDemo = true;

        System.out.println("\n--- DEMO KOLEJKI PRIORYTETOWEJ ---");
        System.out.println("Kolejka jest teraz pusta.");

        while (inDemo) {
            System.out.println("\nOperacje:");
            System.out.println("1. Dodaj pojedynczy element (Insert)");
            System.out.println("2. Pobierz element o najmniejszym priorytecie (Extract Min)");
            System.out.println("3. Zbuduj całą kolejkę z własnych danych (Build)");
            System.out.println("4. Zmniejsz priorytet elementu (Decrease Key)");
            System.out.println("5. Sprawdź czy pusta (IsEmpty)");
            System.out.println("6. Pokaż zawartość kolejki");
            System.out.println("7. Powrót do menu głównego");
            System.out.print("Wybór: ");

            String qChoice = scanner.nextLine();

            try {
                switch (qChoice) {
                    case "1":
                        System.out.print("Podaj znak: ");
                        String charInput = scanner.nextLine();
                        char c = charInput.length() > 0 ? charInput.charAt(0) : '?';

                        System.out.print("Podaj częstotliwość (liczbę): ");
                        int f = Integer.parseInt(scanner.nextLine());

                        demoQueue.insert(new HuffmanNode(c, f));
                        System.out.println("Dodano element [" + c + ": " + f + "]");
                        break;

                    case "2":
                        HuffmanNode min = demoQueue.extractMin();
                        if (min != null) {
                            System.out.println("Pobrano i usunięto element: " + min);
                        } else {
                            System.out.println("Błąd: Kolejka jest pusta!");
                        }
                        break;

                    case "3":
                        System.out.println("--- BUDOWANIE KOLEJKI (Nadpisuje obecną!) ---");
                        System.out.print("Ile elementów chcesz wprowadzić? ");
                        int count = Integer.parseInt(scanner.nextLine());

                        if (count <= 0) {
                            System.out.println("Liczba elementów musi być większa od 0.");
                            break;
                        }

                        HuffmanNode[] userNodes = new HuffmanNode[count];
                        for (int i = 0; i < count; i++) {
                            System.out.println("Element " + (i + 1) + "/" + count + ":");
                            System.out.print("  Podaj znak: ");
                            String s = scanner.nextLine();
                            char nc = s.length() > 0 ? s.charAt(0) : '?';

                            System.out.print("  Podaj częstotliwość: ");
                            int nf = Integer.parseInt(scanner.nextLine());

                            userNodes[i] = new HuffmanNode(nc, nf);
                        }

                        demoQueue.buildQueue(userNodes);
                        System.out.println("Kolejka została zbudowana z " + count + " elementów.");
                        break;

                    case "4":
                        System.out.println("Podgląd indeksów:");
                        demoQueue.printQueue();
                        System.out.print("Podaj INDEKS w tablicy (patrz wyżej): ");
                        int idx = Integer.parseInt(scanner.nextLine());

                        System.out.print("Podaj nową, MNIEJSZĄ częstotliwość: ");
                        int newFreq = Integer.parseInt(scanner.nextLine());

                        demoQueue.decreasePriority(idx, newFreq);
                        break;

                    case "5":
                        System.out.println("Czy kolejka jest pusta? " + (demoQueue.isEmpty() ? "TAK" : "NIE"));
                        break;

                    case "6":
                        demoQueue.printQueue();
                        break;

                    case "7":
                        inDemo = false;
                        break;

                    default:
                        System.out.println("Nie ma takiej opcji.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Błąd: Wprowadzono tekst zamiast liczby!");
            } catch (Exception e) {
                System.out.println("Wystąpił błąd: " + e.getMessage());
            }
        }
    }
}