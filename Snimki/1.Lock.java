package vezba;

public class vezbanjeSnimki {

    private int combination;
    private boolean isOpen;
    private static int Default_Comb=100;

    public vezbanjeSnimki(int combination){// throws invalidCombinationException {
        if(CombinationValid(combination)){
            this.combination = combination;
        }else{
            this.combination =Default_Comb;
        }
        //else throw new invalidCombinationException();

        //mesto iskluchok mozi i nekoja defoault vrednost da mu se dodeli

        this.isOpen=false;
    }

    private boolean CombinationValid(int comb){
        return comb>=100 && comb<=999;
    }

    public boolean open(int combination){
        if (this.combination==combination){
            this.isOpen=true;
        }
        return this.isOpen;
    }


    public boolean changeCombo(int oldCombination, int newCombination){
        if(open(oldCombination)&& CombinationValid(oldCombination)){
            this.combination=newCombination;
            return true;
        }
        return false;
    }


    public void lock(){
        this.isOpen=false;
    }

    //  MAIN
    public static void main(String[] args) {
        vezbanjeSnimki validLock=new vezbanjeSnimki(234);

        System.out.println("TEST IS OPEN");
        System.out.println(validLock.isOpen);

        System.out.println("TEST OPEN");
        System.out.println(validLock.open(233));
        System.out.println(validLock.open(237));
        System.out.println(validLock.open(234));

        validLock.lock();

        System.out.println("TEST CHANGE COMBINATION");
        System.out.println(validLock.changeCombo(234,333));
        System.out.println(validLock.open(333));


        //so invalid key

        vezbanjeSnimki invalidLock=new vezbanjeSnimki(12); //default lock ->100

        System.out.println("TEST IS OPEN");
        System.out.println(invalidLock.isOpen);

        System.out.println("TEST OPEN");
        System.out.println(invalidLock.open(111));
        System.out.println(invalidLock.open(23));
        System.out.println(invalidLock.open(100));

        validLock.lock();

        System.out.println("TEST CHANGE COMBINATION");
        System.out.println(invalidLock.changeCombo(100,987));
        System.out.println(invalidLock.open(987));



    }
}

