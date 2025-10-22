import java.util.Scanner;

public class SumOfFiveNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;

        System.out.println("Enter 5 numbers:");
        for (int i = 0; i < 5; i++) {
            int num = sc.nextInt();
            sum += num;
        }

        System.out.println("Sum = " + sum);
        sc.close();
    }
}
