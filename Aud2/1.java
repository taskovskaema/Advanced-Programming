import java.util.Scanner;

interface Operation{
    int apply(int a,int b);
}

interface MessageProvider{
    String getMessage();
}

class MathOperations implements Operation, MessageProvider{
    private int x;
    private int y;

    public MathOperations(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int apply(int a, int b){
        return a+b;
    }

    @Override
    public String getMessage(){
        return "These are the numbers you want to add number1: "+ x + " number2: "+ y +".";
    }

}

class MathOperationsNoArguments implements Operation, MessageProvider{

    @Override
    public int apply(int a, int b){
        return a+b;
    }

    @Override
    public String getMessage(){
        return "These are the numbers you want to add number1: "+ x + " number2: "+ y +".";
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner input= new Scanner(System.in);

        int num1=input.nextInt();
        int num2=input.nextInt();

        System.out.println("x: "+num1 +" y:"+ num2);

        //1.
        MathOperations mathOp= new MathOperations(num1,num2);
        System.out.println("1. Rezultatot preku MathOperation klasa e: " + mathOp.getMessage());

        //2. klasa koja go implementira interfejsot so argumenti
        Operation op= new MathOperations(num1,num2);
        System.out.println("2. Implementiran interfejs so argumenti rez: "+ op.apply(num1,num2));


        //3.anonimna klasa so argumenti
        Operation op2= new Operation() {
            @Override
            public int apply(int a, int b) {
                return a*b;
            }
        };
        System.out.println("3. Anonimna klasa so argumenti rez: "+ op2.apply(num1,num2));

        //4. lambda izraz so argumenti
        Operation op3=(a,b) -> a-b;
        System.out.println("4. Lambda izraz so argumenti rez: "+op3.apply(num1,num2));


        //5. klasa koj implementira interfejs bez argumenti
        MessageProvider mp= new MathOperationsNoArguments();
        System.out.println("5. Implementiran interfejs bez argumenti rez: "+mp.getMessage());

        //6.  klasa bez argumenti
        MessageProvider mp2=new MessageProvider() {
            @Override
            public String getMessage() {
                return "Ova e poraka od anonimna klasa bez argumenti!";
            }
        };
        System.out.println("6. " + mp2.getMessage()+"<3");

        //7. lambda izraz bez argumenti
        MessageProvider mp3=()->"Lambda izraz bez argumenti!";
        System.out.println("7. "+mp3.getMessage());

    }
}
