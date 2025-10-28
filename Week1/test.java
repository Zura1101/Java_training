
//Combine factorial, palindrome, reverse, even/odd checks in one program. Test concepts learned.
import java.util.Scanner;

public class test {

    public static int factorial(int n) {
        int r = 1;
        for (int i = 2; i <= n; i++) {
            r = r * i;

        }
        return r;

    }

    public static int reverse(int n) {
        int rev = 0;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;

        }
        return rev;
    }

    public static Boolean palindrome(int n) {
        int rev = 0;
        int a = n;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;

        }
        return rev == a;
    }

    public static String EvenOrOdd(int n) {
        if (n % 2 == 0) {
            return "Even";

        }
        return "ODD";

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice, num;
        System.out.println("Enter your choice");
        System.out.println("1: Factorial");
        System.out.println("2 palindrome");
        System.out.println("3 reverse");
        System.out.println("4 even/odd ");
        System.out.println("5 exit");
        choice = sc.nextInt();

        num = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("factorial of " + num + "=" + factorial(num));
                break;
            case 2:
                System.out.println("Enter your choice" + num + "is" + palindrome(num));
                break;
            case 3:
                System.out.println("Revers of " + num + " iS  " + reverse(num));
                break;
            case 4:
                System.out.println(num + "is" + EvenOrOdd(num));
                break;

            default:
                System.out.println("Wrong choice");
                break;
        }
        sc.close();

    }
}