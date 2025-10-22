import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nSimple Calculator Menu");
            System.out.println("1) Add");
            System.out.println("2) Subtract");
            System.out.println("3) Multiply");
            System.out.println("4) Divide");
            System.out.println("5) Exit");
            System.out.print("Choose an option (1-5): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid option. Please enter a number 1-5.");
                sc.nextLine(); // consume invalid token
                continue;
            }

            int choice = sc.nextInt();
            if (choice == 5) {
                running = false;
                System.out.println("Exiting calculator. Goodbye!");
                break;
            }

            System.out.print("Enter first number: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid number. Please enter a valid number.");
                sc.nextLine();
                System.out.print("Enter first number: ");
            }
            double a = sc.nextDouble();

            System.out.print("Enter second number: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid number. Please enter a valid number.");
                sc.nextLine();
                System.out.print("Enter second number: ");
            }
            double b = sc.nextDouble();

            switch (choice) {
                case 1:
                    System.out.printf("Result: %.6f%n", a + b);
                    break;
                case 2:
                    System.out.printf("Result: %.6f%n", a - b);
                    break;
                case 3:
                    System.out.printf("Result: %.6f%n", a * b);
                    break;
                case 4:
                    if (b == 0) {
                        System.out.println("Error: Division by zero is not allowed.");
                    } else {
                        System.out.printf("Result: %.6f%n", a / b);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please pick 1-5.");
            }
        }

        sc.close();
    }
}
