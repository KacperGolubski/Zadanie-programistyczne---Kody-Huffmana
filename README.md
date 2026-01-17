# Implementacja Kodowania Huffmana (Java)

Implementacja algorytmu Huffmana służąca do bezstratnej kompresji i dekompresji plików tekstowych.

## Funkcjonalności
- Własna implementacja Kolejki Priorytetowej (oparta na kopcu binarnym Min-Heap).
- Algorytm budowania drzewa Huffmana.
- Kompresja tekstu do pliku binarnego `.huff`.
- Dekompresja pliku binarnego do tekstu.
- Interfejs tekstowy w terminalu (CLI).
- Obsługa znaków specjalnych w słowniku (Enter, Spacja).
- Tryb demonstracyjny do testowania operacji na kolejce (Dodaj, Usuń Min, Zbuduj, Zmień Priorytet).

## Struktura Projektu

Wszystkie pliki źródłowe znajdują się w głównym katalogu

```text
.
├── Main.java                # Punkt startowy, menu użytkownika
├── HuffmanCoder.java        # Logika kompresji, dekompresji i obsługi plików
├── CustomPriorityQueue.java # Własna struktura danych (Kopiec/Heap)
├── HuffmanNode.java         # Klasa węzła drzewa (implementuje Comparable)
└── README.md                # Dokumentacja projektu

```

## Instrukcja

1. Demo Kolejki Priorytetowej

- Pozwala przetestować działanie kopca binarnego niezależnie od kompresji.

- Insert: Dodaje znak i jego częstotliwość.

- Extract Min: Usuwa korzeń (element o najmniejszej wartości).

- Build: Buduje kopiec z podanej przez użytkownika listy elementów.

- Decrease Key: Zmniejsza wagę elementu pod podanym indeksem).

2. Kompresja Pliku

- Przygotuj plik tekstowy w folderze projektu, np. dane.txt.

- Wybierz opcję 2.

- Podaj nazwę pliku wejściowego: dane.txt.

- Podaj nazwę wyjściową: wynik.huff.

3. Dekompresja Pliku

- Wybierz opcję 3.

- Podaj plik skompresowany: wynik.huff.

- Podaj nazwę docelową: odzyskany.txt.

Format Pliku Wynikowego (.huff)
Plik skompresowany składa się z dwóch sekcji rozdzielonych znakiem nowej linii:

Nagłówek (Słownik): Zapisany tekstem jawnym w formacie ZNAK:CZĘSTOTLIWOŚĆ. Znaki specjalne są "wyciągane":

Przykład nagłówka:

a:5 b:10 \s:2 \n:1 
Dane Binarne: Ciąg bajtów reprezentujący skompresowany tekst, zapisany bezpośrednio po nagłówku.
