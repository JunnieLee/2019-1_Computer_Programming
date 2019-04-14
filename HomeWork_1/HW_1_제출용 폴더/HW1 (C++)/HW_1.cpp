#include <string>
#include <vector> // for phonebook
#include <iostream>
#include <ctime> // for getting current date to calculate d-day
#include <regex> // to check input for phone number and birthdays
 
using namespace std;

class Person { 

    public: 
    Person( string &first, string &last, string &phone_num){
        firstName = first;
        //strcpy(firstName,first);
        lastName = last;
        phoneNumber = phone_num;
    }
    void setFirstName( string &first){
        firstName = first;
    }
    string getFirstName(){
        return firstName;
    }
    void setLastName(string &last){
        lastName = last;
    }
    string getLastName(){
        return lastName;
    }
    void setPhoneNumber(string &phone_num){ 
        phoneNumber = phone_num;
    }
    string getPhoneNumber(){
        return phoneNumber;
    }
    virtual void print(){
        cout<<firstName<<"_"<<lastName<<"_"<<phoneNumber<<endl;
    }
    
    private: 
    string firstName; 
    string lastName; 
    string phoneNumber; 
    };


class Work : public Person { 

    public: 
    Work(string &first, string &last, string &phone_num, string &team):Person(first, last, phone_num){
        this -> team = team;
    }
    void setTeam(string &team){
        this -> team = team;
    }
    string getTeam(){
        return team;
    }
    void print(){
        cout<<getFirstName()<<"_"<<getLastName()<<"_"<<getPhoneNumber()<<"_"<< team <<endl;
    }
    
    private: 
    string team; 
    };


class Friend : public Person { 

    public: 
    Friend(string &first, string &last, string &phone_num, int &age):Person(first, last, phone_num){
        this -> age = age;
    }
    void setAge(int &age){
        this -> age = age;
    } 
    int getAge(){
        return age;
    }
    void print(){
        cout<<getFirstName()<<"_"<<getLastName()<<"_"<<getPhoneNumber()<<"_"<< age <<endl;
    }
    
    private: 
    int age;
    };


class Family : public Person { 
    
    public: 
    Family(string &first, string &last, string &phone_num, string &birthday):Person(first, last, phone_num){
        this -> birthday = birthday;
    }
    void setBirthday(string &birthday){  //set birthday (YYMMDD) 
        this -> birthday = birthday;
    }  
    string getBirthday(){
        return birthday;
    }
    int dDay(){  //calculate the date difference between the birthday and current time 
        time_t now = time(0);
        tm *today = localtime(&now);
        // today's date info
        int today_month = 1 + today->tm_mon;
        int today_day = today->tm_mday;
        // parse birthday and get the date info
        int birthday_month = stoi(birthday.substr(2,2));
        int birthday_day = stoi(birthday.substr(4,2));

        // calculate the date difference
        return date_difference(birthday_month, birthday_day, today_month, today_day);
    }
    void print(){
        cout<<getFirstName()<<"_"<<getLastName()<<"_"<<getPhoneNumber()<<"_"<< birthday <<endl;
    } 
       
    private: 
    string birthday;

    int date_difference(int& b_month, int& b_day, int& t_month, int& t_day){
        const int month_days[12] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int t_day_nums=0;
        int b_day_nums=0;
        // today는 이번년도 기준 몇일째인지 계산
            for (int i=0; i<t_month-1;i++){
                t_day_nums += month_days[i];
            }
            t_day_nums+=t_day;
        // birth day는 이번년도 기준 몇일째인지 계산
            for (int i=0; i<b_month-1;i++){
                b_day_nums += month_days[i];
            }
            b_day_nums+=b_day;

        if (b_day_nums >= t_day_nums) {  // 역전되지 않는 상황
            return (b_day_nums - t_day_nums);
        }
        else{  // 역전되는 상황
            return (b_day_nums + 365 - t_day_nums);
        }
    }
    // private member 정의 끝
    };


class PhoneBook
{
    public:
    PhoneBook(){
        phone_book.reserve(100);
    }
    void add_one(Person* new_member){
        if (phone_book.size()>=phone_book.capacity()){
            phone_book.reserve(phone_book.capacity()*2);
        }
        phone_book.push_back(new_member);
        cout<<"Successfully added new person."<<endl; 
    }
    void delete_one(int idx){
        // 먼저 delete가 가능한 요소인지 확인
        if (phone_book.size() <= idx || idx<0){
            cout<<"Person does not exist!"<<endl; 
        }
        else{
            phone_book_it = phone_book.begin()+idx;  // Iterator에 백터의 시작점부터 idx번째 뒤 위치를 알려준다.
            phone_book.erase(phone_book_it); // erase 메소드는 iterator를 인자로 줘야함.
            cout<<"A person is successfully deleted from the Phone Book!"<<endl; 
        }
    }
    void print_all(){
        cout<<"Phone Book Print"<<endl;
        for (int i=0; i < phone_book.size(); i++){
            cout<< i+1 << ".";
            phone_book.at(i)->print(); // child class method에 접근하기 위해서 포인터를 사용해줌
        }
    }

    private:
    vector <Person*> phone_book;
    vector <Person*>::iterator phone_book_it;  //  Person 형식의 Vector를 가르키는 Iterator 선언
};


// interactive console
int main()
{
  PhoneBook my_phone_book = PhoneBook();  

  bool exit=false;
  
  while (!exit){
    cout<<"CP-2015-15356>";
    string num_choice;
    string init_input;
    getline(cin , init_input) ;
    
    if (init_input=="exit"){
        return 0;
        //exit = true;
    }

    else if (init_input=="1"|init_input=="2"|init_input=="3"){
        num_choice = init_input;
    }
    
    else if (init_input.empty()) {
        cout<<"Phone Book\n"<<"1. Add person\n"<< "2. Remove person\n" << "3. Print phone book"<<endl;
        cin>>num_choice;
        cin.ignore();
    }

    if (num_choice=="1"){ // Add
        cout<<"Select Type\n"<<"1. Person\n"<< "2. Work\n" << "3. Family\n" << "4. Friend" <<endl;
        int select_type;
        cin>>select_type;
        cin.ignore();


        string name;
        string phone_num;

        regex right_name_format("^([a-zA-Z]+)(\\s{1})([a-zA-Z]+)"); //알바펫+space+알파벳 only
        while (true){ 
            cout<<"Name: ";
            getline(cin, name);  
            // name 처리 -- format 맞게 들어왔는지 확인
            if (regex_match(name,right_name_format)) break;
            // cout<<"[Error] : Wrong format. Please type again."<<endl;
        }

        // name 처리 -- 통으로 받은 이름을 first name과 last name으로 나누기
        int space = name.find(" ");
        string first_name = name.substr(0, space);
        string last_name = name.substr(space+1);

        regex right_num_format("^(02|010)(-\\d{4}){2}"); //02-XXXX-XXXX or 010-XXXX-XXXX 형태 only
        while (true){ 
            cout<<"Phone_number: ";
            getline(cin, phone_num);    
            // phone_number 처리 -- format 맞게 들어왔는지 확인
            if (regex_match(phone_num,right_num_format)) break;
            // cout<<"[Error] : Wrong format. Please type again."<<endl;
        }

        if (select_type==1){ // add Person
            Person *p = new Person(first_name, last_name, phone_num);   
            my_phone_book.add_one(p);
        }
        else if (select_type==2){ // add Work
            string team;
            cout<<"Team: ";
            getline(cin, team);

            Work *w = new Work(first_name, last_name, phone_num, team);
            my_phone_book.add_one(w);
        }    
        else if (select_type==3){ // add Family
            string birthday;
            regex right_day_format("^\\d{6}");

            while (true){ // 올바른 입력값이 들어올때까지
                cout<<"Birthday: ";
                getline(cin, birthday);  
                // phone_number 처리 -- format 맞게 들어왔는지 확인
                if (regex_match(birthday,right_day_format) && birthday!="000000") break;          
                // cout<<"[Error] : Wrong format. Please type again."<<endl;
            }

            Family *fam = new Family(first_name, last_name, phone_num, birthday);
            my_phone_book.add_one(fam);
        }
        else if (select_type==4){ // add Friend
            int age;
            while (true){ // 올바른 입력값이 들어올때까지
                cout<<"Age: ";
                cin>>age;
                if (age>0) break; // 자연수만 받기         
                // cout<<"[Error] : Wrong format. Please type again."<<endl;
            }

            Friend *fr = new Friend(first_name, last_name, phone_num, age);
            my_phone_book.add_one(fr);
        }
    }
    else if (num_choice=="2"){ //Remove Someone
        int idx;
        cout<<"Enter index of person: ";
        cin>>idx;
        cin.ignore();
        my_phone_book.delete_one(idx-1);
    }
    else if (num_choice=="3"){ //Print Phonebook
        my_phone_book.print_all();
    }    
  } // while문 끝
  // return 0;
  
}