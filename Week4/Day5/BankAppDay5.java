// BankAppDay5.java
import java.util.*;
public class BankAppDay5 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank("accounts_day5.txt");

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createAccount(); break;
                    case "2": listAccounts(); break;
                    case "3": deposit(); break;
                    case "4": withdraw(); break;
                    case "5": transfer(); break;
                    case "6": running = false; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Exiting. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- Enhanced Banking System ---");
        System.out.println("1) Create account");
        System.out.println("2) List accounts");
        System.out.println("3) Deposit");
        System.out.println("4) Withdraw");
        System.out.println("5) Transfer");
        System.out.println("6) Exit");
        System.out.print("Choose: ");
    }

    private static void createAccount() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Initial deposit: ");
        double bal = Double.parseDouble(scanner.nextLine().trim());
        int id = bank.nextId();
        Account a = new Account(id, name, bal);
        bank.addAccount(a);
        System.out.println("Created: " + a);
    }

    private static void listAccounts() {
        System.out.println("Accounts:");
        bank.getAllAccounts().forEach(System.out::println);
    }

    private static void deposit() {
        System.out.print("Account ID: "); int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Amount: "); double amt = Double.parseDouble(scanner.nextLine().trim());
        bank.deposit(id, amt);
        System.out.println("Deposited.");
    }

    private static void withdraw() throws InsufficientFundsException {
        System.out.print("Account ID: "); int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Amount: "); double amt = Double.parseDouble(scanner.nextLine().trim());
        bank.withdraw(id, amt);
        System.out.println("Withdrawn.");
    }

    private static void transfer() throws InsufficientFundsException {
        System.out.print("From ID: "); int from = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("To ID: "); int to = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Amount: "); double amt = Double.parseDouble(scanner.nextLine().trim());
        bank.transfer(from, to, amt);
        System.out.println("Transferred.");
    }
}
