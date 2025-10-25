//Alien (OOP- nasleduvanje- refaktoriranje)

/*

enum AlienType{
    SNAKE_ALIEN(0),
    OGRE_ALIEN(1),
    MARSHMALLOW_MAN_ALIEN(2);

    private int value;

    AlienType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

 */

abstract class Alien{
    private String name;
    //private AlienType value; //go zacuvva tipot na Alien (za enum funkcijata)
    private int health;


    public Alien(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int MinusHealth(int damage){
        return health-damage;
    }

    //sekoj podtip ke go definira apstraktniot metod
    public abstract int getDamage();

    @Override
    public String toString() {
        return "Alien{" +
                "name='" + name + '\'' +
                ", health=" + health +
                '}';
    }
}


class Snake_Alien extends Alien{

    public Snake_Alien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public String toString() {
        return "Snake_Alien{}";
    }
}

class Ogre_Alien extends Alien{

    public Ogre_Alien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage() {
        return 6;
    }
}

class Marshmallow_Alien extends Alien{

    public Marshmallow_Alien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage(){
        return 1;
    }
}

class AlienPack{
    private Alien[] aliens;
//    private int numAlien;

    public AlienPack(int numA) { //kolkav broj na aliens ke treba da zacuvame
//        this.numAlien=numA;
        aliens= new Alien[numA];
    }

    //funkcija za dodavanje na Aliens
    public void addAlien(Alien newAlien,int index){
        if (index>=0 && index<aliens.length)
        aliens[index]=newAlien;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public int calculateDamage(){
        int damageSum=0;
        for (Alien alien:aliens) {
            damageSum+=alien.getDamage();

        }
        return damageSum;

    }

}

public class Main {
    public static void main(String[] args) {

        AlienPack pack = new AlienPack(3);
        pack.addAlien(new Snake_Alien("Slyther", 100), 0);
        pack.addAlien(new Ogre_Alien("Grom", 80), 1);
        pack.addAlien(new Marshmallow_Alien("Fluffy", 50), 2);


        for (Alien a : pack.getAliens()) {
            System.out.println(a + " Type: " + a.getClass().getSimpleName() + " - Damage: " + a.getDamage() + "Health after damage: "+ a.MinusHealth(9));
        }

        System.out.println("Total damage: "+pack.calculateDamage());

    }
}
