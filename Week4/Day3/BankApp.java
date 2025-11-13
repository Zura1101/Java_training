import java.util.InputMismatchException;
import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = null;

        try {
            System.out.print("Enter Account ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // clear buffer

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            account = new BankAccount(id, name, balance);
            account.display();

            // Test deposit
            System.out.print("\nEnter amount to deposit: ");
            double dep = sc.nextDouble();
            account.deposit(dep);
            account.display();

            // Test withdraw
            System.out.print("\nEnter amount to withdraw: ");
            double wd = sc.nextDouble();
            account.withdraw(wd);
            account.display();

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type! Please enter numeric values where required.");

        } catch (InvalidOperationException e) {
            System.out.println("Operation error: " + e.getMessage());

        } catch (InsufficientFundsException e) {
           System.out.println("Transaction failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            System.out.println("\nThank you for using our banking system.");
            sc.close();
        }
    }
}
