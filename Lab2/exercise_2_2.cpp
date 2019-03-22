#include <iostream>

using namespace std;

void swap(char *c);

int main(void)
{
    char alphabet;
    bool out = false; // terminate when == true

    // FILL IN
    while (!out){
        cout << "Enter Capital or Small letter <0 for exit>:";
        cin >> alphabet ;
        if (alphabet=='0') out = true;
        swap(&alphabet);
    };
    

    return 0;
}

void swap(char *c){
    char output;
    if (*c>='A' && *c<='Z'){ //capital input
        output = *c+32;
    } else { // lower case input
        output = *c-32;
    };
  
    cout << "input :" << *c << " "<<"output:"<< output << endl;
}