import java.util.Scanner;

public class SumOfEvenNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter N (number of even numbers to sum): ");
        int n = sc.nextInt();
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += 2 * i; // first even numbers are 2, 4, 6, ...
        }

        System.out.println("Sum of first " + n + " even numbers = " + sum);
        sc.close();
    }
}
