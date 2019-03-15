import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Student {

    // 멤버 정의

    public String surname;
    public String first_name;
    public int student_id;
    // public List<Integer> student_id = Arrays.asList(new Integer[9]); // 9 digit number

    private int age;
    public String address;
    private int Height;

    public void setAge(int age){
            this.age = age;
        }
    public boolean isOld(){
        return (this.age>19);
    }



    // main함수
    public static void main(String[] args) {
        System.out.println("\n[Usage of Student Class Program]\n");
        Scanner scanner = new Scanner(System.in);

        // instantiation
        Student student = new Student();

        // 1. ask for a student information

        System.out.println("Student's surname :");
        String surname = scanner.nextLine();
        student.surname = surname;

        System.out.println("Student's first name :");
        String first_name = scanner.nextLine();
        student.first_name = first_name;


        System.out.println("Student ID : ");
        int id = Integer.parseInt(scanner.nextLine());
        while (String.valueOf(id).length() != 9){
            System.out.println("Please try again. (9 digits of numbers only)");
            System.out.println("Student ID : ");
            id = Integer.parseInt(scanner.nextLine());
        }
        student.student_id = id;


        System.out.println("Student's age : ");
        int age = scanner.nextInt();
        student.setAge(age);

        scanner.nextLine();
        System.out.println("Student's address :");
        String address = scanner.nextLine();
        student.address = address;

        // 2. print all possible student information

        System.out.println("\n<< Student Information >>\n");
        System.out.println("- Surname : " + student.surname);
        System.out.println("- First name : " + student.first_name);
        System.out.println("- Student ID : " + student.student_id);
        System.out.println("- Address : " + student.address);
        System.out.println(student.isOld()? "- This student is over 19." : "- This student is not over 19.");

    }
}
