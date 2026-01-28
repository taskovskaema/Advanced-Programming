package januarski_ispit;

//funkciski interfejsi od java

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.*;

class Student {
    String ime;
    final int indeks;
    int ocena;
    int prisustvo;

    public Student(String ime, int index, int ocena, int prisustvo) {
        this.ime = ime;
        this.indeks = index;
        this.ocena = setOcena(ocena);
        this.prisustvo = prisustvo;
    }

    public int getOcena() {
        return ocena;
    }

    public int getPrisustvo() {
        return prisustvo;
    }

    public String getIme() {
        return ime;
    }

    public int getIndeks() {
        return indeks;
    }

    public int setOcena(int ocena) {
        if (ocena >= 5 && ocena <= 10) {
            this.ocena = ocena;
        } else if (ocena > 10) {
            this.ocena = 10;
        } else {
            this.ocena = 5;
        }
        return this.ocena;
    }

    @Override
    public String toString() {
        return "Student: " + "ime= " + ime + ",indeks: " + indeks + ", ocena= " + ocena + ", prisustvo= " + prisustvo;
    }

}


class Course {
    Student[] students;
    String naslov;
    int size;

    public Course(String naslov, int br_studenti) {
        this.students = new Student[br_studenti];
        this.naslov = naslov;
        this.size=0;
    }

    //___________1.SUPPLIER_________________________
//    public boolean enroll(Supplier<Student> supplier){
//        //dodava nov student vo kursot, ako ima slobodno mesto
//        if(students.length <= size){
//            return false;
//        }
//        students[size++]=supplier.get();
//        return true;
//
//    }

    //  ILI VAKA

    void addStudent(Student s) {
        if (size < students.length) {
            students[size++] = s;
        }
    }

    void enroll(Supplier<Student> supp_student) {
        addStudent(supp_student.get());
    }

    //__________________2/7.CONSUMER______________________
    void forEach(Consumer<Student> cons_student) {
        for (int i = 0; i < size; i++) {
            cons_student.accept(students[i]);
        }
    }

    void mutate(Consumer<Student> mutator) {
        for (int i = 0; i < size; i++) {
            mutator.accept(students[i]);
        }
    }

    //__________________3/4/5.PREDICATE______________________
    int count(Predicate<Student> condition) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (condition.test(students[i])) {
                counter++;
            }
        }
        return counter;
    }

    Student findFirst(Predicate<Student> condition2) {
        for (int i = 0; i < size; i++) {
            if (condition2.test(students[i])) {
                return students[i];
            }
        }
        return null;
    }
    //vaka bi se povikalo
    //Student s = course.findFirst(st -> st.getIndex() == 123);

    Student[] filter(Predicate<Student> condition3) {
        int j = 0, golemina_new_niza = 0;
        //ako nie dozvoleno null vrednosti vo nizata deka vo slobodnite mesta ke ima null
        //ako nemozi null treba coulter koj ke izbroj prven kolku lugje spagjat vo nizata
        // pa tolka golemina da ja naprajme nizata, pr:
        for (int i = 0; i < size; i++) {
            if (condition3.test(students[i])) {
                golemina_new_niza++;
            }
        }

        Student[] new_students = new Student[golemina_new_niza];

        for (int i = 0; i < size; i++) {
            if (condition3.test(students[i])) {
                new_students[j] = students[i];
                j++;
            }
        }

        return new_students;
    }

    //_________________8.PREDICATE+CONSUMER______________________
    void conditionalMutate(Predicate<Student> condition, Consumer<Student> consumer) {
        for (int i = 0; i < size; i++) {
            if (condition.test(students[i])) {
                consumer.accept(students[i]);
            }
        }
    }

    //_________________6.FUNCTION-MAPPER____________________
    //mapToLabels(Function<Student, String> mapper)
    //Враќа низа од текстуални описи, добиени со трансформирање
    // на секој студент со дадената функција.

    String[] mapToLabels(Function<Student, String> mapper) {
        String[] map_students = new String[size];
        for (int i = 0; i < size; i++) {
            map_students[i] = mapper.apply(students[i]);
        }
        return map_students;
    }


    @Override
    public String toString() {
        return "Course: " + "naslov='" + naslov + ",students=" + Arrays.toString(students) + ", size=" + size;

    }


}

//zamena za CourseDemo
public class Lambda_Class {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

//Креирај Supplier<Student> кој чита податоци за еден студент од конзолата (индекс, име, оцена, присуство) и враќа нов објект Student.
        Supplier<Student> studentFromSupplier = () -> {
            System.out.println("Vnesi student, indeks, ocena, prisustvo: \n");
            String name = in.next();
            int indeks = in.nextInt();
            int ocena = in.nextInt();
            int prisustvo = in.nextInt();
            in.nextLine();
            return new Student(name, indeks, ocena, prisustvo);

        };
//_________________________________________________________________________
        //Запиши n студенти во курсот користејќи го методот enroll.
        Course course = new Course("NP", 100);
        for (int i = 0; i < n; i++) {
            course.enroll(studentFromSupplier);
        }
//___________________________________________________________________________
        //Користи Consumer<Student> заедно со forEach за да ги испечатиш сите запишани студенти.
        System.out.println("\nZapishani studenti:");
        //course.forEach(System.out::println); //istoto e ko ova podolu

        // course.forEach(student -> System.out.println(student));
        //faktichki se povikva so course.forEach, pa odi gore vo funkcijata
        // i za sekoj cons_student.accept() prifakja za to shoe vnatre kako
        //argument da se izvrshi ova dolu kaj sho se povikva

        //ama poshto bara so consumer ke mora da e:
        Consumer<Student> pecati=System.out::println;
        course.forEach(pecati);
//_______________________________________________________________________________
//        Дефинирај Predicate<Student> за студенти кои:
//        имаат оцена поголема или еднаква на 6
//        имаат присуство најмалку 70%
//        Комбинирај ги двете состојби со .and() и користи го методот filter за да ги прикажеш само тие студенти.
        Predicate<Student> predicate1=s->s.getOcena()>=6;
        Predicate<Student> predicate2= s->s.getPrisustvo()>=70;
        Predicate<Student> predicate1and2 = predicate1.and(predicate2);

        System.out.println("\nStudenti so prolazni ocnki i solidno prisustvo: ");
        Student[] polozeni= course.filter(predicate1and2);
        for (Student s:polozeni){System.out.println(s);}

//__________________________________________________________________________________

        //Користи findFirst за да го пронајдеш и прикажеш првиот студент со оцена поголема
        // или еднаква на 9.
        System.out.println("\nPrv student so ocena >=9:");
        Predicate<Student> predicate_first=student -> student.getOcena()>=9;
        Student prv = course.findFirst(predicate_first);
        System.out.println(prv);


//_________________________________________________________________________________
        System.out.println("\nZgolemeni oceni: ");
        //Користи mutate за да ја зголемиш оцената на сите студенти за 1.
        Consumer<Student> zgolemi= student -> student.setOcena(student.getOcena()+1);
        //Student[] zgolemena=course.mutate(zgolemi); ->NEMOZI ZATO SHOE VOID
        course.mutate(zgolemi);
        course.forEach(pecati);

//_________________________________________________________________________________
        //Користи conditionalMutate за да ја зголемиш оцената за 1 само
        // на студентите со присуство поголемо или еднакво на 90%.

        //---site zgolemeni od prethodnata metoda----

        System.out.println("\nZgolemeni oceni za studenti so prisustvo nas 90%: ");
        Predicate<Student> predicate_promena= student -> student.getPrisustvo()>=90;
        Consumer<Student> consumer_promena= student -> student.setOcena(student.getOcena()+1);
        course.conditionalMutate(predicate_promena,consumer_promena);
        course.forEach(pecati);

//_________________________________________________________________________________
        //Користи mapToLabels за да ги трансформираш сите студенти во текстуални описи и испечати ги.
        Function<Student,String> function = Student::toString;
        String[] mapper= course.mapToLabels(function);
        System.out.println("\nStudenti mapirani:");
        for (String s:mapper){
            System.out.println(s);
        }
        

    }
}







/*
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
 */


//




