public class Costructor {
    String titel;
    String author;


    Costructor(String t,String a){
        titel=t;
        author=a;
    }
    public static void main(String[] args) {
        Costructor c = new Costructor("The Master book ","Blitz");
        System.out.println(c.titel + " for  " + c.author);


    }
}
