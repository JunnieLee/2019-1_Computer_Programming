import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook
{
    // public static final String INVALID_INPUT = "[Error] : Wrong format. Please type again.";
    public static final Pattern NAME_PATTERN = Pattern.compile("^([a-zA-Z]+)(\\s{1})([a-zA-Z]+)");
    public static final Pattern PHONE_NUM_PATTERN = Pattern.compile("^(02|010)(-\\d{4}){2}");
    public static final Pattern BIRTH_DAY_PATTERN = Pattern.compile("^\\d{6}");


    public PhoneBook() { phoneBook = new ArrayList<Person>(); }

    public void add_one(Person new_one) {
        this.phoneBook.add(new_one);
        System.out.println("Successfully added new person.");
    }
    public void delete_one(int idx) {
        if (this.phoneBook.size() < idx || idx<=0){
            System.out.println("Person does not exist!");
        }
        else{
            this.phoneBook.remove(idx-1);
            System.out.println("A person is successfully deleted from the Phone Book!");
        }
    }
    public void print_all() {
        System.out.println("Phone Book Print");
        for (int i=0; i < phoneBook.size(); i++){
            System.out.print((i+1)+".");
            phoneBook.get(i).print();
        }
    }

    private List<Person> phoneBook;


class Person {

    public Person(String first, String last, String phone_num){
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phone_num;
    }
    public void setFirstName(String first){
        this.firstName = first;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setLastName(String last){
        this.lastName = last;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setPhoneNumber(String num){
        this.phoneNumber = num;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void print(){
        System.out.println(this.firstName + "_" + this.lastName + "_" + this.phoneNumber);
    }

    private String firstName;
    private String lastName;
    private String phoneNumber;
}

class Work extends Person {

    public Work(String first, String last, String phone_num, String team){
        super(first, last, phone_num);
        this.team = team;
    }
    public void setTeam(String team){
        this.team = team;
    }
    public String getTeam(){
        return this.team;
    }
    public void print(){
        System.out.println(this.getFirstName() + "_" + this.getLastName() + "_" + this.getPhoneNumber() + "_" + this.team);
    }

    private String team;
}

class Friend extends Person {

    public Friend(String first, String last, String phone_num, int age){
        super(first, last, phone_num);
        this.age = age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
    public void print(){
        System.out.println(this.getFirstName() + "_" + this.getLastName() + "_" + this.getPhoneNumber() + "_" + this.age);
    }

    private int age;
}

class Family extends Person {

    public Family(String first, String last, String phone_num, String birth_day){
        super(first, last, phone_num);
        this.birthday = birth_day;
    }
    public void setBirthday(String birth_day){
        this.birthday = birth_day;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public int dDay(){ // d-day 계산하기
        int birthday_month = Integer.parseInt(birthday.substring(2,4));
        int birthday_day = Integer.parseInt(birthday.substring(4));
        return calculate_date_difference(birthday_month, birthday_day);
    }
    public void print(){
        System.out.println(this.getFirstName() + "_" + this.getLastName() + "_" + this.getPhoneNumber() + "_" + this.birthday);
    }

    private String birthday;
}

    // interactive console
    public static void main(String[] args) {
        PhoneBook my_phoneBook = new PhoneBook();
        boolean exit = false;
        //Scanner scanner = new Scanner(System.in);

        while (!exit) {
            int choice=0;
            System.out.print("CP-2015-15356>");
            Scanner scanner = new Scanner(System.in);
            String initial_input = scanner.nextLine();
            boolean INT_TYPE_INPUT = my_phoneBook.IsInt_ByException(initial_input);

            if (initial_input.equals("exit")) {return;}
            else if (initial_input.equals("")) { // 엔터키를 쳤다면
                System.out.println("Phone Book\n1. Add person\n2. Remove person\n3. Print phone book");
                while (true) {
                    choice = scanner.nextInt();
                    if (choice==1 | choice==2 | choice==3) break;
                    // System.out.println(INVALID_INPUT);
                }
            }
            else if (INT_TYPE_INPUT){
                int num = Integer.parseInt(initial_input);
                if (num==1||num==2||num==3){
                    choice = Integer.parseInt(initial_input);
                }
            }
            else{ // 그 외 쌩뚱맞은 input이면 다시 initial state로
                // System.out.println(INVALID_INPUT);
                continue;
            }

            // choice가 제대로 들어왔을 때
            switch (choice) {
                case 1: // Add
                    System.out.println("Select Type\n1. Person\n2. Work\n3. Family\n4. Friend");
                    int select_type = scanner.nextInt();
                    my_phoneBook.Add_to_PhoneBook(select_type);
                    break;
                case 2: // Delete
                    my_phoneBook.Delete_from_PhoneBook();
                    break;
                case 3: // Print
                    my_phoneBook.print_all();
                    break;
            }
        }// end of while문
    } // end of main 함수


    // 보조 메소드들 모음

    public int calculate_date_difference(int birthday_month, int birthday_day){
        long d_day;
        Calendar cal = Calendar.getInstance(); //일단 Calendar 객체를 만들어준다.

        int year = cal.get(Calendar.YEAR); // 현재 연도
        int month = birthday_month;
        int date = birthday_day;

        long now_day = cal.getTimeInMillis(); //현재 시간

        cal.set(year, month-1, date); //목표일을 cal에 set

        long event_day = cal.getTimeInMillis(); //목표일에 대한 시간

        if (now_day <= event_day){ // 역전되지 않는상황
            d_day = (event_day - now_day) / (60*60*24*1000);
        }else{ //역전되는 상황
            d_day = (now_day - event_day) / (60*60*24*1000);
            d_day = 365-d_day;
        }
        return (int)d_day;
    }

    // (string으로 들어온 input이 int형태로 변환 가능한지 확인)
    public boolean IsInt_ByException(String str){
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }

    public void Add_to_PhoneBook(int select_type){
        Scanner s = new Scanner(System.in);

        // name 받기
        String name;
        while (true){
            System.out.print("Name: ");
            name = s.nextLine();
            // name 처리 -- format 맞게 들어왔는지 확인
            Matcher m = NAME_PATTERN.matcher(name);
            boolean right_format = m.matches();
            if (right_format) break;

            // System.out.println(INVALID_INPUT);
        }

        String[] splitStr = name.split("\\s+");
        String first_name = splitStr[0];
        String last_name = splitStr[1];

        String phone_number;
        // phone number 받기
        while (true){
            System.out.print("Phone_number: ");
            phone_number = s.nextLine();
            // phone_number 처리 -- format 맞게 들어왔는지 확인
            Matcher m = PHONE_NUM_PATTERN.matcher(phone_number);
            boolean right_format = m.matches();
            if (right_format) break;

            // System.out.println(INVALID_INPUT);
        }

        if (select_type==1){ // add Person
            this.add_one(new Person(first_name, last_name, phone_number));
        }
        else if (select_type==2){ // add Work
            System.out.print("Team: ");
            String team = s.nextLine();
            this.add_one(new Work(first_name, last_name, phone_number, team));
        }
        else if (select_type==3){ // add Family
            String birthday;
            while (true){
                System.out.print("Birthday: ");
                birthday = s.nextLine();
                // birthday format 맞게 들어왔는지 확인
                Matcher m2 = BIRTH_DAY_PATTERN.matcher(birthday);
                boolean right_format2 = m2.matches();
                if ((right_format2) && !(birthday.equals("000000"))) break; // 생일은 6자리 자연수만 받기
                // System.out.println(INVALID_INPUT);
            } // 입력받기 끝
            this.add_one(new Family(first_name, last_name, phone_number,birthday));
        }
        else if (select_type==4){ // add Friend
            int age;
            while (true){
                System.out.print("Age: ");
                age = s.nextInt();
                if (age > 0) break; // 나이는 자연수 형태만 받기
            }
            this.add_one(new Friend(first_name, last_name, phone_number,age));
        }

    }

    public void Delete_from_PhoneBook(){
        Scanner s = new Scanner(System.in);
        // 삭제할 index 받기
        System.out.print("Enter index of person: ");
        int idx = s.nextInt();
        this.delete_one(idx);
    }

} // end of PhoneBook classs


