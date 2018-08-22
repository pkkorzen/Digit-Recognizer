Napiszemy program rozpoznający cyfry napisane ręcznie.

Napiszemy coś co nazywa się klasyfikatorem (ang. Classifier)
Nasz klasyfikator dostanie na wejście obrazek liczby zapisanej ręczniej i zwróci nam int mówiącego co to za liczba.

Rozwiązujemy zadanie w parach, jedna osoba realizuje podpunkt, git commit i przekazuje klawiature.
Polecam otworzyć 2 projekty w IntelliJ - jeden na właściwe zadanie, jeden do bazgrania

Trochę jak w TDD rozwiązujemy każdy punkt osobno ale tym razem nie piszemy testów jednostkowych.

Sekcje zawierają podpowiedzi co warto użyć.

0. Przygotowanie
    Nowy projekt w IntelliJ
    Nowe git repo
    Nowe git repo na bitbucket/github
    do repo dodajemy pliki z danymi:
        trainingsample.csv
            ten plik posłuży nam do nauczenia naszego klasygikatora jak wyglądają cyfry
        validationsample.csv
            ten plik posłuzy nam do sprawdzenia jak dobrze nasz klasyfikator działa

1. Czytamy dany z pliku "trainingsample.csv"
przydatne będzie:
    java.nio.file Files.readAllLines -> przeczyta plik i zwróci nam List<string> dla każdej lini/wiersza w pliku
    java.nio.file Paths.get

2. Dzielenie wierszy na kolumny
Podziel każdą linię na tablicę Stringów
Dzielić już umiemy - string.split()
Wybierz jedną linie przeczytaną w punkcie 1 i podzielą ją na kolumny.

Jak zrobić to dla wszystkich wierszy? Streams!

List<String> fruits = new ArrayList<String>();
fruits.add("apple");
fruits.add("banana");
fruits.add("orange");
fruits.add("strawberry");

aby z listy zrobić stream uzyjmy metody .stream()
fruits.stream()

aby każdy element listy fruits przetrasformować na coś innego uzyjemy funkcji map()

chcemy otrzymać listę z długościami nazw owoców:
List<Integer> lengths = fruits
    .stream()
    .map(x -> x.length())
    .collect(Collectors.toList());

Użyj funkcji map() aby zamienić nazwy owoców na wielkie litery.

wypisz wynik używając pętli foreach

wypisać wyniki możemy też używając Streams
fruits
    .stream()
    .map(x -> x.length())
    .forEach(x -> System.out.println("I'm doing something for a fruit!"));

popraw kod aby działa tak samo jak pętla for() napisana przed chwilą

3. Wycinamy nagłówki
Nasze pliki CSV mają nagłówki, chcemy się ich pozbyć 

Podpowiedź:
fruits.subList(1,3) - stworzy nam nową listę która jest kawałkiem listy fruits.
Jak stworzyć listę jedynie bez pierwszego elementu fruits?

4. Konwersja z Stringów do intów 
Zauważ że nasza lista składa się z Stringów.
Pozbyliśmy się już nagłówków.
Chcemy teraz zamienić Stringi na inty.
funkcja map() znowu nam się przyda.
Przyda się też wiedza z wczoraj - jak parsowaliśmy Stringi do inta?

5. Konwersja z tablicy do naszej własnej klasy 
Łatwiej będzie nam pracować jeżeli zamiast tablicy intów będziemy pracowali na obiektach naszej własnej klasy.

Zamiast listy tablic będziemy wtedy mieli listę obiektów.

Podpowiedź:
dla naszej listy fruits:
napisz klasę:
class FruitInfo {
    public String Name;
    public bool DoILikeThisFruit;
}
i używając fukcji map przetrasfromuj listę nazw owoców na listę obiektów FruitInfo.
DoILikeThisFruit zawsze ustawiaj na true :)
Przyda nam się znowu funkcja map().
Czy wiesz że wyrażenie lambda może mieć wiele linijek?
...
.map(x -> {
    FruitInfo fi = new FruitInfo();
    fi.Name = ...
    ...
    return fi;
})
...

Jak już uporasz się z owocami napisz klase:
class Record {
    public Integer Number;
    public Integer[] Pixels;
}
i przetransformuj naszą List<int[]> na List<Record>

6. Obliczanie dystansu
Musimy obliczyć odległość między obrazkami.
Co to znaczy odległość między obrazkami?
Odległość obrazków jest ich podobieństwem.
Im mniejsza odległość dwóch obrazków tym bardziej podobne są.

Przypomnienie z matematyki: odległość euklidesowa:
odległość dwóch punktów (x1, y1), (x2, y2) = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))
odległość dwóch punktów 3 wymiarowych: (x1, y1, z1) (x2, y2, z2 ) = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1-z2)*(z1-z2))

Przyda nam sie funkcja o takiej sygnaturze:
int distance(Integer[] a, Integer[] b)

jak ją napisać?

tutaj możemy zastosować TDD.

Zacznijmy od testu który sprawdzi czy potrafimy obliczyć odległość między 2 punktami 2D które są w tym samym miejscu.
Wynik powinien być 0.
Teraz napiszemy test dla 2 punktów 2D które są różne
Teraz napiszemy test dla 2 punktów 3D które są różne
Teraz napiszemy test dla 2 punktów nD które są różne

do napisania tej fukncji użyjemy zwykłego for(int i = 0)...

zacznijmy od tego:

public static int distance(Integer[] a, Integer[] b) {
    return -1;
}

7. Funkcja klasyfikatora
Teraz napiszemy funkcję klasyfikatora,
czyli funkcję której podamy rysunek a funkcja powie nam co ten rysunke przedstawia.

Wejściem do naszej funkcji będzie tablica pikseli oraz zestaw danych z pliku trainingsample.csv.
Funkcja porówna obrazek składający się z pikseli z każdym obrazkiem z naszych dancyh testowych. Nastepnie powie nam który obrazek z danych testowych jest najpodobniejszy naszemu nowem obrazkowi.
Czyli który obrazek z danych testowych jest najbliższy.

Przykład:
jeżeli podany naszej funkcji na wejściu jakieś gryzmoły i funkcja stwierdzi że te gryzmoły najbardziej przypominają obrazek przedstawiający cyfrę 8 to uznamy że gryzmoły przedstawiają własnie cyfrę 8.

chcemy napisać funkcję:
public static int predict(Integer[] pixels, Record[] sampleRecords)

Podpowiedź:
1. napisz małą klasę która będzie przechowywała 2 pola:
    dystans i liczbę.
2. za pomoca funkcji map przetransformuj kazdy record z sampleRecords na jego label i dystans do pixels.
3. użyj .sorted() aby posortować wyniki naszych porównań, przyda się tutaj Comparator.comparingInt(lambda tutaj)
4. wybierz pierwszy element z posortowanych elementów - to będzie najbardziej dopasowany obrazek.

8. Sprawdzamy jak zadziałał nasz klasyfikator
Wczytaj dane z pliku validationsample.csv
Przetwórz je podobnie jak dane z pliku trainingsample.csv
używając klasyfikatora zklasyfikuj każdy wiersz z validationsample.CSV
    sprawdź jaką liczbę przypisze wierszowi (obrazkowi) nasz klasyfikator i czy jest to poprawna cyfra
Jaki % obrazków z validationsample.csv został poprawnie rozpoznany?
