package januarski_ispit;

//funkciski interfejsi od java
import java.util.function.*;

public class Lambda_Class {
    public static void main(String[] args) {

        //prifakja eden argument (1.String -> sho pushtame, 2.Integer -> sho vrakja
        Function<String, Integer> dolzinaString = string -> string.length();
        System.out.println("Fukcija za dolzhinata na stringot koj go prushtame: " + dolzinaString.apply("Zdravo sp"));

        //prifakja dva argumenti
        //1. prv argument, 2. vtor argument, 3. sho da vrati
        BiFunction <Integer,Integer,Integer> zbirDvaBroja = (a,b) -> a+b;
        System.out.println("Funlcija so dva argumenti za sobiranje: "+ zbirDvaBroja.apply(7,8));

        //Boolean funkcija
        Predicate<Integer> ProverkaBool = broj -> broj % 2 == 0;
        System.out.println("Funkcija za proverka: " + ProverkaBool.test(24));
        System.out.println("Funkcija za proverka: " + ProverkaBool.test(17));

        //Funkcija koja zema string i go pechati vo konzola
        Consumer<String> pecati= str -> System.out.println("Print: "+ str);
        pecati.accept("AAOAOAOAOAOAOAOAOAOAOA");

        //Ne prima argumenti vrakja tekovno vreme vo milisekundi
        Supplier<Long> vreme= ()-> System.currentTimeMillis();
        System.out.println("Vreme: "+vreme.get());
    }
}

