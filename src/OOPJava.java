public class OOPJava {
    int ID;
    String name;

    void StudentID() {
        System.out.println("Student ID is "+ ID);
        System.out.println("Student Name is "+ name);
    }





    public static void main(String[] args) {
        OOPJava obj = new OOPJava();
        obj.ID= 123;
        obj.name="ABD";
        obj.StudentID();
    }
}
