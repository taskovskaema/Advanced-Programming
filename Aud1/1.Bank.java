//sodrzhi objasnuvanje za kako funkcionirat interfejsi i nekoj podobreni delovi vo kodot + main - check i out

import java.util.Arrays;
import java.util.Scanner;


abstract class Account{
    private String ime;
    private long brSmetka;
    private double momentalniPari;


    public Account(String ime, long brSmetka, double momentalniPari) {
        this.ime = ime;
        this.brSmetka = brSmetka;
        this.momentalniPari = momentalniPari;
    }


    //metod za pristap do momentalnata suma na pari
    public double getMomentalniPari() {
        return momentalniPari;
    }


    // dodavanje pari
    public void AddMoney(double dodadi){
        momentalniPari=momentalniPari+dodadi;
    }


    //odzemanje pari
    public void TakeMoney(double odzemi){
        momentalniPari=momentalniPari-odzemi;
    }
}


/*
(nisto ne praj samo konstruktor povikva, super() e funkcija koja go povikva
 osnovniot (glavniot vo Account) konstruktor)
 */
class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String coek, int num, double accSum){
        super(coek,num,accSum);
    }
}


/*
   najednostavno objasnato kako funkcionira interfejsot, koga rabotime so
   golema baza na podatoci, eve na pr. karakteri vo igra, nekoj se od ist
   tip- nekoj od razlicen (attack,heal,tanker bla bla) site ovie se vo ista baza no so svoja licna klasa
   e sega vo sluchajot kaj karakteri koi imaat attack powers, implementiran e interfejsot
   vo samite klasi kaj attack karakterite i preku glavniot interfejs mu
   pomaga da gi isfiltrira klasite (site karakteri) na bazata
   i da odberi na koi treba da mu pushti attack ili ultimate attack itn.
   *INTERFEJSOT OVOZMOZHUVA RAZLICHNI KLASI DA IMAAT ISTO ODNESUVANJE*
                     bez pritoa da se vo ista klasa
*/
interface InterestBearingAccount{
    void addInterest();
}



//nasleduvanje od klasata Account, ke ni treba plus broj na akaunti i max broj na akaunti
class Bank{
    private Account[] accounts;
    private int brNaAkaunti;
    private int MaxBrAkaunti;


    public Bank(int maxAcc){
        this.brNaAkaunti=0; //ke gi dodavame od 0 zato pocnuva od 0
        this.MaxBrAkaunti=maxAcc; //vkupno mozni akaunti
        accounts= new Account[MaxBrAkaunti];
    }


    //mora da se klaj funkcija za dodavanje na akaunti
    public void addAccount(Account account){
        if(brNaAkaunti == accounts.length){
            //vaka e podobro bidejki na drugiot nacin ne ja apdejtnuvame novata brojka na maAcc
            MaxBrAkaunti*=2;
            accounts = Arrays.copyOf(accounts,MaxBrAkaunti);
            /*Se kreira nova niza so dolzhina od 2*maxBrAcc
            starite podatoci se prefrlaat na pocetokot od
            nizata a ostanatiot del e popolnat so null vrednosti*/
        }
        accounts[brNaAkaunti++]=account; //dodavame akaunt i go zgolemvame br.na akaunti
    }


    //sumata na site smetki
    public double totalAssets(){
        double sum=0;
        //for (Account account: accounts) -> ne vaka oti ke frli NullPointerException za null poziciite
        for (int i = 0; i < brNaAkaunti; i++) {
            sum+= accounts[i].getMomentalniPari();
        }
        return sum;
    }


    //metod koj go povikuva interfejsot za dodavanje na kamata
    public void addInterest(){
        for(Account account: accounts){//gi vrti site akaunti
            //dali akauntot spagja vo onie na koi treba da mu se napravi neshto so kamatata?
            if(account instanceof InterestBearingAccount){
                //sekoj akaunt koj go ima interfejsot privremeno stanva I.B.A. i mu se dodava kamatata
                InterestBearingAccount x=(InterestBearingAccount) account;
                x.addInterest();
            }
        }
    }
}



//dodavanje na kamata
class InterestCheckingAccount extends Account implements InterestBearingAccount{

    public static final double KAMATA = 0.03; //final znaci deka nemozi da se promeni

    //mora da se klaj super konstruktor za da funkcionira extend Account
    public InterestCheckingAccount(String ime, long brSmetka, double momentalniPari) {
        super(ime, brSmetka, momentalniPari);
    }

    @Override
    public void addInterest(){
        AddMoney(getMomentalniPari()*KAMATA);
        //kamatata koj mu se pushta na metodot AddMoney vo Account
    }
}



//pomala kamata za tie so Platnium
class PlatinumCheckingAccount extends Account implements InterestBearingAccount{

    //mozi i nova kamata da napishite ili da ja zemite od I.C.A. (vo addInterest)
    public static final double KAMATAp = 0.03 * 2;

    public PlatinumCheckingAccount(String ime, long brSmetka, double momentalniPari) {
        super(ime, brSmetka, momentalniPari);
    }

    @Override
    public void addInterest(){
        //AddMoney(getMomentalniPari() * InterestCheckingAccount.KAMATA * 2);
        AddMoney(getMomentalniPari() * KAMATAp);

    }
}



public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1️ Kreirame banka so maksimum 5 akaunti
        Bank bank = new Bank(5);

        // 2️ Dodavame razlicni tipovi na akaunti
        bank.addAccount(new NonInterestCheckingAccount("Marko", 1111, 1000));
        bank.addAccount(new InterestCheckingAccount("Ana", 2222, 2000));
        bank.addAccount(new PlatinumCheckingAccount("Elena", 3333, 5000));
        bank.addAccount(new NonInterestCheckingAccount("Stefan", 4444, 1500));

        // 3️ Prikazuvame vkupna suma na site akaunti pred kamata
        System.out.printf("Vkupna suma na site akaunti: %.2f den.%n", bank.totalAssets());

        // 4 Dodavame kamata SAMO na onie so InterestBearingAccount (Ana i Elena)
        bank.addInterest();

        // 5 Prikazuvame vkupna suma posle dodavanje kamata
        System.out.printf("Po dodavanje na kamatata: %.2f den.%n", bank.totalAssets());

        // 6 (Opcionalno) Dodavanje novi pari na akaunt i test
        System.out.println("\nKolku pari da dodademe na akauntot na Marko?");
        double iznos = input.nextDouble();
        // Mora da zememe referenca do akauntot (na pozicija 0 vo nizata)
        // Ovde samo za test koristime direktno indeksiranje (ako imash getter moze i po ime)
        // No vo ovoj primer gi dodavame direktno vo samiot akaunt
        bank.addAccount(new NonInterestCheckingAccount("Test", 5555, iznos));

        System.out.printf("Nova vkupna suma na site akaunti: %.2f den.%n", bank.totalAssets());

    }
}
