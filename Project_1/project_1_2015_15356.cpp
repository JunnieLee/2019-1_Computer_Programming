#include <iostream>
#include <cstring>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

bool global_unexpected = false;
bool skip_ask = false;

// parent class
class Vehicle {

    public:
    Vehicle(string graphic,int& index, int speed, int temperature, int& oxygen, int& energy_value, int local_distance, int& total_dist)
    : graphic_index(index), oxygen_rate(oxygen), energy(energy_value), total_distance(total_dist){
        this -> graphic = graphic;
        this -> speed = speed;
        this -> temperature = temperature;
        this -> local_distance = local_distance;
        vehicle_stop = false;
        arrived = (local_distance<=0);
        fixed_local_distance = local_distance;
    }
    int get_speed(){return speed;}
    void set_speed(int num){speed = num;}
    int get_energy(){return energy;}
    void set_energy(int num){energy = num;}
    int get_temperature(){return temperature;}
    int get_oxygen_rate(){return oxygen_rate;}
    void set_oxygen_rate(int num){oxygen_rate = num;}
    int get_local_distance(){return local_distance;}
    int get_fixed_local_distance(){return fixed_local_distance;}
    void set_local_distance(int num){local_distance=num;}
    int get_total_distance(){return total_distance;}
    void set_total_distance(int num){total_distance=num;}
    virtual void update_all_losses(void)=0;
    virtual void move_1(void)=0;
    virtual void move_2(void)=0;
    virtual void print_status(void)=0;
    

    bool end_condition(void){
        if (vehicle_stop) return true;
        if (local_distance<=0) return true;
        if (energy<=0) return true;
        if (oxygen_rate<=0) return true;
        return false;
    }
    char end_cause(void){
        if (vehicle_stop) return 'S';
        if (local_distance<=0) return 'A';
        if (energy<=0) return 'E';
        if (oxygen_rate<=0) return 'O';
        else return 'X';
    }
    void stop_vehicle(void){ vehicle_stop = true; } // for unexpected cases
    void increment_graphic_position(void){graphic_index++;}
    void print_graphic(void){cout<<graphic.substr(0, graphic_index) + "@" + graphic.substr(graphic_index)<<endl;}

    private:
    int speed;
    int& energy;
    int temperature;
    int& oxygen_rate;
    int local_distance;
    int& total_distance;
    bool arrived;
    bool vehicle_stop;
    int& graphic_index;
    string graphic;
    int fixed_local_distance;
};


class Car : public Vehicle{
    private:
    int speed;
    int& energy;
    int temperature; 
    int humidity;
    int& oxygen_rate;
    bool vehicle_stop;
    int local_distance;
    int& total_distance;
    bool arrived;
    int& graphic_index;

    public:
    Car(string graphic, int& index, int temperature, int& oxygen, int& energy_value, int humidity, int local_distance, int& total_dist) 
    : Vehicle(graphic, index,speed=80, temperature, oxygen, energy_value, local_distance, total_dist)
        ,graphic_index(index), oxygen_rate(oxygen), energy(energy_value), total_distance(total_dist)
    {
        this -> humidity = humidity;
        oxygen_rate = 100;
    }

    int get_humidity(void){return humidity;}

    void solar_panel_recharge(void){
        if (humidity < 50){
            if (get_energy() + 200 > 1000) set_energy(1000); // Energy cannot be over 1000
            else set_energy(get_energy() + 200);
        }
    }

    void update_energy_loss(void){
        if ((0 < get_temperature())&&(get_temperature()< 40)){set_energy(get_energy()-5);}
        else if (get_temperature() >= 40) {set_energy(get_energy()-10);}
        else if (get_temperature()==0) {set_energy(get_energy()-8);}

        if (humidity < 50) {set_energy(get_energy()-5);}
        else {set_energy(get_energy()-8);}
        
        if (get_energy()<=0) {set_energy(0);} // energy cannot go below 0
    }

    virtual void update_all_losses(void){update_energy_loss();}

    virtual void move_1(void){ // per unit
        set_local_distance(get_local_distance()-50);
        set_total_distance(get_total_distance()+50);
        update_all_losses();
        increment_graphic_position();
        if (!global_unexpected) cout<<"Successfully moved to next "<< 50 <<" km"<<endl;
    }
    
    virtual void move_2(void){
        int before_move = get_total_distance();
        for (int i=0; i < ((get_fixed_local_distance()/50)+1); i++){
            if (!end_condition()){
                set_local_distance(get_local_distance()-50);
                set_total_distance(get_total_distance()+50);
                update_all_losses();
                increment_graphic_position();
            }
        }        
        if (!global_unexpected) cout<<"Successfully moved to next "<< get_total_distance()-before_move  <<" km"<<endl;
    }

    virtual void print_status(void){
        cout<<"Current Status: "<<"Car"<< endl;
        cout<<"Distance: "<< get_total_distance() <<" km"<<endl;
        cout<<"Speed: "<< get_speed() <<" km/hr"<<endl;
        cout<<"Energy: "<<  get_energy() << endl;
        cout<<"Temeperature: "<< get_temperature() <<" C"<<endl;
        cout<<"Humidity: "<< humidity <<endl;
    }
};

class Airplane : public Vehicle{

    private:
    int speed;
    int& energy;
    int temperature;
    int humidity;
    int altitude;
    int& oxygen_rate;
    int air_density;
    int local_distance;
    int& total_distance;
    bool arrived;
    bool vehicle_stop;
    int& graphic_index;

    public:
    Airplane(string graphic, int& index, int temperature, int& oxygen, int& energy_value, int humidity, int altitude, int air_density, int local_distance, int& total_dist) 
    : Vehicle(graphic, index, speed=900, temperature, oxygen, energy_value, local_distance, total_dist)
        , graphic_index(index), oxygen_rate(oxygen), energy(energy_value), total_distance(total_dist)
    {
        this -> humidity = humidity;
        this -> altitude = altitude;
        this -> air_density = air_density;
        arrived = false;
        update_speed_loss();
    }
    int get_humidity(void){return humidity;}
    int get_altitude(void){return altitude;}
    int get_air_density(void){return air_density;}

    void update_energy_loss(void){
        if ((0 < get_temperature())&&(get_temperature()< 40)){set_energy(get_energy()-5);}
        else if (get_temperature() >= 40) {set_energy(get_energy()-10);}
        else if (get_temperature()==0) {set_energy(get_energy()-8);}

        if (humidity < 50) {set_energy(get_energy()-5);}
        else {set_energy(get_energy()-8);}
        
        if (get_energy()<=0) {set_energy(0);} // energy cannot go below 0
    }

    void update_speed_loss(void){
        if ((air_density >= 30)&&(air_density < 50)) set_speed(get_speed()-200);
        else if ((air_density >= 50)&&(air_density < 70)) set_speed(get_speed()-300);
        else if (air_density >= 70) set_speed(get_speed()-500);
    }

    void update_oxygen_rate_loss(void){
        set_oxygen_rate(get_oxygen_rate()-((altitude/1000)*10));
        if (get_oxygen_rate() <= 0) set_oxygen_rate(0);  // oxygen cannot go below 0
    }

    virtual void update_all_losses(void){
        update_energy_loss();
        update_oxygen_rate_loss();
    }

    virtual void move_1(void){ // per unit
        set_local_distance(get_local_distance()-1000);
        set_total_distance(get_total_distance()+1000);
        update_all_losses();
        increment_graphic_position();
        if (!global_unexpected) cout<<"Successfully moved to next "<< 1000 <<" km"<<endl;
    }

    virtual void move_2(void){
        int before_move = get_total_distance();
        for (int i=0; i < ((get_fixed_local_distance()/1000)+1); i++){
            if (!end_condition()){
                set_local_distance(get_local_distance()-1000);
                set_total_distance(get_total_distance()+1000);
                update_all_losses();
                increment_graphic_position();
            }
        }        
        if (!global_unexpected) cout<<"Successfully moved to next "<< get_total_distance()-before_move  <<" km"<<endl;
    }
    virtual void print_status(void){
        cout<<"Current Status: "<<"Airplane"<< endl;
        cout<<"Distance: "<< get_total_distance() <<" km"<<endl;
        cout<<"Speed: "<< get_speed() <<" km/hr"<<endl;
        cout<<"Energy: "<<  get_energy() << endl;
        cout<<"Oxygen Level: "<<  get_oxygen_rate() << endl;
        cout<<"Temeperature: "<< get_temperature() <<" C"<<endl;
        cout<<"Humidity: "<< humidity <<endl;
        cout<<"Altitude: "<< altitude <<endl;
        cout<<"Air Density: "<< air_density <<endl;
    }
};

class Submarine : public Vehicle{
    private:
    int speed;
    int& energy;
    int temperature;
    int depth;
    int& oxygen_rate;
    int water_flow;
    int local_distance;
    int& total_distance;
    bool arrived;
    bool vehicle_stop;
    int& graphic_index;

    public:
    Submarine(string graphic, int& index, int temperature, int& oxygen, int& energy, int depth, int water_flow, int local_distance, int& total_dist) 
    : Vehicle(graphic, index, speed=20, temperature, oxygen, energy, local_distance, total_dist),
      graphic_index(index), oxygen_rate(oxygen), energy(energy), total_distance(total_dist) {
        this -> depth = depth;
        this -> water_flow = water_flow;
        arrived = false;
        update_speed_loss();
    }

    void light(void){
        set_energy(get_energy()-30);
        if (get_energy()<=0) {set_energy(0);}  // energy cannot go below 0
    }

    int get_depth(void){return depth;}
    int get_water_flow(void){return water_flow;}

    void update_energy_loss(void){
        light();

        if ((0 < get_temperature())&&(get_temperature()< 40)){set_energy(get_energy()-5);}
        else if (get_temperature() >= 40) {set_energy(get_energy()-10);}
        else if (get_temperature()==0) {set_energy(get_energy()-8);}

        if (get_energy()<=0) {set_energy(0);} // energy cannot go below 0
    }

    void update_oxygen_rate_loss(void){
        set_oxygen_rate(get_oxygen_rate()-((depth/50)*5));
        if (get_oxygen_rate() <= 0) set_oxygen_rate(0);  // oxygen cannot go below 0
    }

    void update_speed_loss(void){
        if ((water_flow >= 30)&&(water_flow < 50)) set_speed(get_speed()-3);
        else if ((water_flow >= 50)&&(water_flow < 70)) set_speed(get_speed()-5);
        else if (water_flow >= 70) set_speed(get_speed()-10);
    }

    virtual void update_all_losses(void){
        update_energy_loss();
        update_oxygen_rate_loss();
    }

    virtual void move_1(void){ // per unit
        set_local_distance(get_local_distance()-10);
        set_total_distance(get_total_distance()+10);
        update_all_losses();
        increment_graphic_position();
        if (!global_unexpected) cout<<"Successfully moved to next "<< 10 <<" km"<<endl;
    }

    virtual void move_2(void){
        int before_move = get_total_distance();
        for (int i=0; i < ((get_fixed_local_distance()/10)+1); i++){
            if (!end_condition()){
                set_local_distance(get_local_distance()-10);
                set_total_distance(get_total_distance()+10);
                update_all_losses();
                increment_graphic_position();
            }
        }        
        if (!global_unexpected) cout<<"Successfully moved to next "<< get_total_distance()-before_move  <<" km"<<endl;
    }

    virtual void print_status(void){
        cout<<"Current Status: "<<"Submarine"<< endl;
        cout<<"Distance: "<< get_total_distance() <<" km"<<endl;
        cout<<"Speed: "<< get_speed() <<" km/hr"<<endl;
        cout<<"Energy: "<<  get_energy() << endl;
        cout<<"Oxygen Level: "<<  get_oxygen_rate() << endl;
        cout<<"Temeperature: "<< get_temperature() <<" C"<<endl;
        cout<<"Depth: "<< depth <<endl;
        cout<<"Water Flow: "<< water_flow <<endl;
    }
};


class Environment{
    private:
    string type_name;
    int temperature;
    int distance;

    public:
    Environment(int dist, int temp, string name="undefined"){
        distance = dist;
        temperature = temp;
        type_name = name;
    }
    int get_temperature(void){return temperature;}
    int get_distance(void){return distance;}
    string get_type_name(void){return type_name;}
};

class Road_Environment: public Environment{
    private:
    int temperature;
    int humidity;
    int distance;

    public:
    Road_Environment(int dist, int temp, int humd):Environment(dist, temp, "Road"){
        humidity = humd;
    }
    int get_humidity(void){return humidity;}
};

class Sky_Environment: public Environment{
    private:
    int temperature;
    int humidity;
    int altitude;
    int air_density;
    int distance;

    public:
    Sky_Environment(int dist, int temp, int humd, int alt, int aird):Environment(dist, temp, "Sky"){
        humidity = humd;
        altitude = alt;
        air_density = aird;
    }
    int get_humidity(void){return humidity;}
    int get_altitude(void){return altitude;}
    int get_air_density(void){return air_density;}
};

class Ocean_Environment: public Environment{
    private:
    int temperature;
    int depth;
    int waterflow;
    int distance;

    public:
    Ocean_Environment(int dist, int temp, int d, int watfl):Environment(dist, temp, "Ocean"){
        depth = d;
        waterflow = watfl;
    }
    int get_depth(void){return depth;}
    int get_waterflow(void){return waterflow;}
};

class Environment_X: public Environment{
    private:
    int temperature;
    int distance;

    public:
    Environment_X(int dist, int temp):Environment(dist, temp, "X"){}
};

class Environment_Y: public Environment{
    private:
    int temperature;
    int distance;

    public:
    Environment_Y(int dist, int temp):Environment(dist, temp, "Y"){}
};


class Journey{
    public:
    bool journey_finish;
    int graphic_index;
    int extra_mode_num;
    int move_mode;

    Journey(vector<Environment*> env_list, string text_graphic, int extra_mode_num){
        this-> extra_mode_num = extra_mode_num;
        this -> env_list = env_list;
        this -> text_graphic = text_graphic;
        total_energy = 1000;
        total_oxygen = 100;
        journey_finish = false;
        total_distance=0;
        graphic_index=1;
        
        current_env = new Environment(0,0); // dummy data
        car_charger_broken = false;
        last_loop = false;
    }

    void move(int mode){
        move_mode = mode;

        if (gotta_change_env) change_env();
        gotta_change_env = false; 
    
        if (global_unexpected) {
            skip_ask = true;
            change_env();
        }
        global_unexpected = false;
        
        if (!current_vehicle->end_condition()){
            switch(mode){
            case 1:
                current_vehicle-> move_1(); 
                if (!((last_loop)&&(current_vehicle -> end_condition()))) { 
                    current_vehicle-> print_status();
                    if (extra_mode_num==1) current_vehicle-> print_graphic();
                }
                break;

            case 2:
                current_vehicle-> move_2();
                if (!((last_loop)&&(current_vehicle -> end_condition()))) {
                    current_vehicle-> print_status();
                    if (extra_mode_num==1) current_vehicle-> print_graphic();
                } 
                break;
            }
        } 
        

        if (current_vehicle -> end_condition()){
            switch(current_vehicle -> end_cause()){
                case 'A': 
                    oxygen_change.push_back(total_oxygen);
                    energy_change.push_back(total_energy);

                    if (last_loop){ 
                        finish_journey('A');
                        journey_finish = true;
                        return;
                    }
                    gotta_change_env= true; // 다음 루프때 change해라!
                    skip_ask = false; 
                    break;
                case 'O':
                    finish_journey('O');
                    journey_finish = true;
                    break;
                case 'E':
                    finish_journey('E');
                    journey_finish = true;
                    break; 
                case 'S':
                    finish_journey('S');
                    journey_finish = true;
                    break; 
            }
            return;
        }
    }

    void change_env(void){
       
        if (last_loop){ // arrived till the end of all envs // if ((unsigned)vector_count == env_list.size())
            finish_journey('A');
            journey_finish = true;
            return;
        } 

        string env_type = env_list.at(vector_count) -> get_type_name();
    
        if ((env_type!="X")&&(env_type!="Y")&&(env_type != current_env->get_type_name())) { // X, Y가 아니고, 기존과 다른 환경일때만 블랙박스 기록
            if (env_type =="Road") mode_change.push_back("Car");
            else if (env_type =="Sky") mode_change.push_back("Airplane");
            else if (env_type =="Ocean") mode_change.push_back("Submarine");
        }

        if (env_type=="Road"){
            Road_Environment* my_road = static_cast<Road_Environment*>(env_list.at(vector_count));
            Car* my_car = new Car(text_graphic, graphic_index, my_road->get_temperature(), total_oxygen, total_energy, my_road->get_humidity(), my_road->get_distance(), total_distance);
            speed_change.push_back(my_car->get_speed());
            bool status_change = (env_type!=current_env->get_type_name()); // road->X->road인 경우 status change는 false
            current_env = my_road;
            current_vehicle = my_car;
            if (status_change && !car_charger_broken) my_car->solar_panel_recharge();
        }
        else if (env_type=="Sky"){
            Sky_Environment* my_sky = static_cast<Sky_Environment*>(env_list.at(vector_count));
            Airplane* my_airplane = 
            new Airplane(text_graphic, graphic_index, my_sky->get_temperature(), total_oxygen, total_energy, my_sky->get_humidity(), my_sky->get_altitude(), my_sky->get_air_density(), my_sky->get_distance(), total_distance);
            speed_change.push_back(my_airplane->get_speed());
            current_env = my_sky;
            current_vehicle = my_airplane;
        }
        else if (env_type=="Ocean"){
            Ocean_Environment* my_ocean = static_cast<Ocean_Environment*>(env_list.at(vector_count));
            Submarine* my_submarine = 
            new Submarine(text_graphic, graphic_index, my_ocean->get_temperature(), total_oxygen, total_energy, my_ocean->get_depth(), my_ocean->get_waterflow(), my_ocean->get_distance(), total_distance);
            speed_change.push_back(my_submarine->get_speed());
            current_env = my_ocean;
            current_vehicle = my_submarine;
        }
        else if (env_type=="X") {
            global_unexpected = true;
            if (extra_mode_num==2){ 
                vector_count++;
                if ((unsigned)vector_count == env_list.size()) last_loop = true;
                return;
            }
            srand((unsigned int)time(NULL));
            int random_num = rand()%100; // 0~99사이의 수 생성
            if (random_num<20){ // 0~19 이면 (20/100 -> 20%) vehicle stop
                current_vehicle->stop_vehicle();
            }
            else{
                total_energy-=100;
                if (total_energy<=0) total_energy=0;
            }
        }
        else if (env_type=="Y") {
            global_unexpected = true;
            if (extra_mode_num==2){
                vector_count++;
                if ((unsigned)vector_count == env_list.size()) last_loop = true; 
                return;
            }
            srand((unsigned int)time(NULL));
            int random_num = rand()%100; // 0~99사이의 수 생성
            if (random_num<35){ // 0~34 이면 (35/100 -> 35%) vehicle stop
                current_vehicle->stop_vehicle();
            }
            else{
                srand((unsigned int)time(NULL));
                int random_num_2 = rand()%2; // 0 혹은 1 생성
                if (random_num_2==0){ //50% 확률로 발생
                    if (current_env->get_type_name() =="Road") car_charger_broken = true;
                    else{
                        total_oxygen-=30;
                        if (total_oxygen<=0) total_oxygen=0;
                    }
                }
            }
            
        }
        vector_count++;
        if ((unsigned)vector_count == env_list.size()) last_loop = true;
    }


    void finish_journey(char cause){
        print_final_status();
        if (extra_mode_num==1) current_vehicle-> print_graphic();
        cout<<"\n!FINISHED : ";
        if (cause=='O')  cout<<"Oxygen failure"<<endl;
        else if (cause=='E') cout<<"Energy failure"<<endl;
        else if (cause=='A') cout<<"Arrived"<<endl;
        else if (cause=='S') cout<<"Vehicle stop"<<endl;
        else return;
        print_black_box();
        global_unexpected = false;
    }

    void initial_status_print(void){
        cout<<"Current Status: "<<"Car"<< endl;
        cout<<"Distance: "<< 0 <<" km"<<endl;
        cout<<"Speed: "<< 0 <<" km/hr"<<endl;
        cout<<"Energy: "<<  1000 << endl;
        cout<<"Temeperature: "<< env_list.at(vector_count) -> get_temperature() <<" C"<<endl;
        cout<<"Humidity: "<< static_cast<Road_Environment*>(env_list.at(vector_count))-> get_humidity() <<endl;
        if (extra_mode_num==1) cout<<text_graphic.substr(0, graphic_index) + "@" + text_graphic.substr(graphic_index)<<endl;
    }

    private:
    bool gotta_change_env=false;
    bool last_loop;
    bool car_charger_broken;
    int vector_count=0;
    vector<Environment*> env_list;
    int total_distance; 
    int total_energy;
    int total_oxygen;
    vector <string> mode_change;
    vector <int> energy_change;
    vector <int> oxygen_change;
    vector <int> speed_change;
    Environment* current_env;
    Vehicle* current_vehicle;
    string text_graphic; 

    bool end_condition(void){
        if ((total_energy <= 0) || (total_oxygen <= 0)) return true;
        else return false;
    }

    void print_final_status(void){
        cout<<"Final Status:"<<endl;
        cout<<"Distance: "<<total_distance<<" km"<<endl;
        cout<<"Energy: "<<total_energy<<endl;
        cout<<"Oxygen Level: "<<total_oxygen<<endl;
        
        if ((current_vehicle -> end_cause() != 'A')&&(current_vehicle -> end_cause() != 'S')){
            oxygen_change.push_back(total_oxygen);
            energy_change.push_back(total_energy);
        }
    }

    void print_black_box(void){
        cout<<"Black Box:\n";
        cout<<"Mode: ";
        for (int i=0; (unsigned)i < mode_change.size(); i++){
            cout<< mode_change.at(i);
            if (i==mode_change.size()-1) cout<<endl;
            else cout<<" > ";
        }
        cout<<"Energy Level: ";
        for (int i=0; (unsigned)i < energy_change.size(); i++){
            cout<< energy_change.at(i);
            if (i == energy_change.size()-1) cout<<endl;
            else cout<<" > ";
        }
        cout<<"Oxygen Level: ";
        for (int i=0; (unsigned)i < oxygen_change.size(); i++){
            cout<< oxygen_change.at(i);
            if (i == oxygen_change.size()-1) cout<<endl;
            else cout<<" > ";
        }
        cout<<"Speed: ";
        for (int i=0; (unsigned)i < speed_change.size(); i++){
            cout<< speed_change.at(i);
            if (i == speed_change.size()-1) cout<<endl;
            else cout<<" > ";
        }
        cout<<"-------------------------"<<endl;
    }
};


/////////////////////////////
// helper function prototypes
vector<char*> to_journey_list(char TC[]);
vector<Environment*> to_env_list(vector<char*> whole_journey);
string text_graphic_initializer(vector<Environment*>& env_list);


int main(){

    string TC1 = "[R100T10H10],[S3000T10H5A2000D30],[O40T0D100W100]";
    string TC2 = "[R1000T20H49]";
    string TC3 = "[R1000T20H49],[Y],[S1000T20H49A1000D0],[R150T20H49]";
    string TC4 = "[R5050T10H10]";
    string TC5 = "[R50T10H10],[X],[S4000T10H5A2000D30],[R50T10H10],[O90T0D100W100],[R50T10H10]";
    string TC6 = "[R150T20H30],[S3000T10H5A2000D20],[X],[O80T0D100W100],[R150T30H50]";
    string TC7 = "[R200T25H10],[S2000T20H0A1000D20],[Y],[O30T10D500W100]";
    string TC8 = "[R100T10H10],[X],[S3000T10H5A2000D30],[O40T0D100W100]";
    string TC9 = "[R100T10H10],[X],[S4000T10H5A2000D30]";
    string TC10 = "[R500T20H20],[S3000T10H5A2000D30],[O80T0D100W100]";


    // initial state
    cout<<"Mode Select(1 for EXTRA, 2 for NORMAL) :";
    int mode_num;
    cin>> mode_num;

    while (true){
        global_unexpected = false;
        int testcase_num;
        cout<< "\nPJ1.LJH.2015-15356\n"<<"Choose the number of the test case (1~10) : ";
        cin>> testcase_num;
        cin.ignore();
        if (testcase_num==0) break;
        
        cout<<"\nTestcase #"<<testcase_num<<".\n"<<endl;

        char chosen_TC[201];
        switch(testcase_num){
            case 1: strcpy(chosen_TC, TC1.c_str()); break;
            case 2: strcpy(chosen_TC, TC2.c_str()); break;
            case 3: strcpy(chosen_TC, TC3.c_str()); break;
            case 4: strcpy(chosen_TC, TC4.c_str()); break;
            case 5: strcpy(chosen_TC, TC5.c_str()); break;
            case 6: strcpy(chosen_TC, TC6.c_str()); break;
            case 7: strcpy(chosen_TC, TC7.c_str()); break;
            case 8: strcpy(chosen_TC, TC8.c_str()); break;
            case 9: strcpy(chosen_TC, TC9.c_str()); break;
            case 10: strcpy(chosen_TC, TC10.c_str()); break;
        }
        
        vector<Environment*> env_list = to_env_list(to_journey_list(chosen_TC));

        Journey* my_journey = new Journey(env_list, text_graphic_initializer(env_list), mode_num);
        my_journey->initial_status_print();
        my_journey->change_env();

        int move_num;
        while (!(my_journey-> journey_finish)){
            if (!skip_ask){
                cout<<"Next Move? (1,2)"<<endl;
                cout<<"CP-2015-15356>";
                cin>> move_num;
            }
            skip_ask = false;
            my_journey-> move(move_num);
        }
        skip_ask = false;
    }
    return 0;
}


vector<char*> to_journey_list(char TC[]){
    char * pch1;
    const char* delimiter1 = ",";
    vector<char*> whole_journey;
    pch1 = strtok(TC, delimiter1);
    while (pch1!=NULL){
        whole_journey.push_back(pch1);
        pch1 = strtok(NULL, delimiter1);
    }
    return whole_journey;
}


vector<Environment*> to_env_list(vector<char*> whole_journey){

    vector<Environment*> env_list;
    env_list.reserve(100);

    const char* delimiter2 = "[]RTHSADOWXY";
    vector <string> title2 = {"RTH", "STHAD", "OTDW"};
    char * pch2;
    string title;

    for (unsigned int i=0; i < whole_journey.size(); i++){
        vector<char*> each_section_value;
        pch2 = strtok(whole_journey.at(i), delimiter2);
        while (pch2!=NULL){
            each_section_value.push_back(pch2);
            pch2 = strtok(NULL, delimiter2);
        }

        char env_type = whole_journey.at(i)[1];

        if (env_type=='R'){
            int distance = atoi(each_section_value.at(0));
            int temperature = atoi(each_section_value.at(1));
            int humidity = atoi(each_section_value.at(2));
            env_list.push_back(new Road_Environment(distance, temperature, humidity));
        }
        else if (env_type=='S'){
            int distance = atoi(each_section_value.at(0));
            int temperature = atoi(each_section_value.at(1));
            int humidity = atoi(each_section_value.at(2));
            int altitude = atoi(each_section_value.at(3));
            int air_density = atoi(each_section_value.at(4));
            env_list.push_back(new Sky_Environment(distance, temperature, humidity, altitude, air_density));
        }
        else if (env_type=='O'){
            int distance = atoi(each_section_value.at(0));
            int temperature = atoi(each_section_value.at(1));
            int depth = atoi(each_section_value.at(2));
            int water_flow_rate = atoi(each_section_value.at(3));
            env_list.push_back(new Ocean_Environment(distance, temperature, depth, water_flow_rate));
        }
        else if (env_type=='X'){
            env_list.push_back(new Environment_X(0,0)); // dummy data for unexpected envs
        }
        else if (env_type=='Y'){
            env_list.push_back(new Environment_Y(0,0)); // dummy data for unexpected envs
        }

    }

    return env_list;
}

string text_graphic_initializer(vector<Environment*>& env_list){
    string output="|";
    for (int i=0; (unsigned)i< env_list.size(); i++){
        string env_type = env_list.at(i) -> get_type_name();
        if (env_type=="Road"){
            for (int j=0; j < ((env_list.at(i)->get_distance())/50);j++) output.append("=");
        }
        else if (env_type=="Sky"){
            for (int j=0; j < ((env_list.at(i)->get_distance())/1000);j++) output.append("^");
        }
        else if (env_type=="Ocean"){
            for (int j=0; j < ((env_list.at(i)->get_distance())/10);j++) output.append("~");
        }
    }
    output.append("|");
    return output;
}  