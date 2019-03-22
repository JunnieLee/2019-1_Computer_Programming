#include <iostream>

using std::cout;
using std::endl;
using std::cin;

void swap(int *a, int *b);
void swap(char *a, char *b);



int main(void)
{
    int num1, num2;
    char char1, char2;

    cout << "Put two numbers for swapping" << endl;
    cin >> num1 >> num2;

    cout << "Put two characters for swapping" << endl;
    cin >> char1 >> char2;

    cout << "Results" << endl;
    swap(&num1, &num2);
    swap(&char1, &char2);

}

void swap(int *a, int *b)
{
    int *c;

    c = b;
    b = a;
    a = c;
    
    cout << *a <<" "<< *b << endl;
}

void swap(char *a, char *b)
{
    char *c;

    c = b;
    b = a;
    a = c;

    cout << *a << " "<< *b << endl;
}