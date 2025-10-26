import java.util.Scanner;

public class Calculator {

    // Method for Addition
    public static double add(double a, double b) {
        return a + b;
    }

    // Method for Subtraction
    public static double subtract(double a, double b) {
        return a - b;
    }

    // Method for Multiplication
    public static double multiply(double a, double b) {
        return a * b;
    }

    // Method for Division
    public static double divide(double a, double b) {
        if (b == 0) {
            System.out.println("Error: Cannot divide by zero.");
            return 0;
        }
        return a / b;
    }

    // Method for Power
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Method to Reverse a Number
    public static int reverseNumber(int num) {
        int reversed = 0;
        while (num != 0) {
            reversed = reversed * 10 + num % 10;
            num /= 10;
        }
        return reversed;
    }

    // Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n===== TEXT-BASED CALCULATOR =====");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Power");
            System.out.println("6. Reverse Number");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter first number: ");
                    double a1 = sc.nextDouble();
                    System.out.print("Enter second number: ");
                    double b1 = sc.nextDouble();
                    System.out.println("Result = " + add(a1, b1));
                    break;

                case 2:
                    System.out.print("Enter first number: ");
                    double a2 = sc.nextDouble();
                    System.out.print("Enter second number: ");
                    double b2 = sc.nextDouble();
                    System.out.println("Result = " + subtract(a2, b2));
                    break;

                case 3:
                    System.out.print("Enter first number: ");
                    double a3 = sc.nextDouble();
                    System.out.print("Enter second number: ");
                    double b3 = sc.nextDouble();
                    System.out.println("Result = " + multiply(a3, b3));
                    break;

                case 4:
                    System.out.print("Enter first number: ");
                    double a4 = sc.nextDouble();
                    System.out.print("Enter second number: ");
                    double b4 = sc.nextDouble();
                    System.out.println("Result = " + divide(a4, b4));
                    break;

                case 5:
                    System.out.print("Enter base: ");
                    double base = sc.nextDouble();
                    System.out.print("Enter exponent: ");
                    double exp = sc.nextDouble();
                    System.out.println("Result = " + power(base, exp));
                    break;

                case 6:
                    System.out.print("Enter number to reverse: ");
                    int num = sc.nextInt();
                    System.out.println("Reversed number = " + reverseNumber(num));
                    break;

                case 7:
                    System.out.println("Exiting... Thank you for using the calculator!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 7);

        sc.close();
    }
}
