// 1~2^10 사이의 자연수를 입력받고, 해당 자연수의 팩토리얼을 출력

import java.util.Scanner;


public class question_1 {

    // 팩토리얼 함수
    private static int factorial(int n){
        if (n==0) return 1;
        else return (n*factorial(n-1));
    }

    public static void main(String[] args) {
        System.out.println("\n[Print Factorial Program]\n");

        Scanner scanner = new Scanner(System.in);
        int num = 0;

        // 숫자 입력받기
        while (true) {
            System.out.println("Enter a positive integer from 1 to 2^10 : ");
            num = scanner.nextInt();
            if ((1<=num)&&(num<=Math.pow(2,10))) break;
        }

        // 팩토리얼 값 전달받기
        int val = factorial(num);

        System.out.println("The factorial of "+ num + " is "+val);
    }
}
