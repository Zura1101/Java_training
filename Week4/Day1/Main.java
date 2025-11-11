import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    // store accounts by id
    private static final Map<Integer, Account> accounts = new HashMap<>();
    private static int nextId = 1;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createSavingsAccount(); break;
                    case "2": createCheckingAccount(); break;
                    case "3": listAccounts(); break;
                    case "4": deposit(); break;
                    case "5": withdraw(); break;
                    case "6": applyInterest(); break;
                    case "7": showAccountInfo(); break;
                    case "0": running = false; break;
                    default: System.out.println("Unknown option. Try again.");
                }
            } catch (InputMismatchException | IllegalArgumentException | InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // clear buffer if needed
            }
            System.out.println();
        }
        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Bank Menu =====");
        System.out.println("1 - Create Savings Account");
        System.out.println("2 - Create Checking Account");
        System.out.println("3 - List Accounts");
        System.out.println("4 - Deposit");
        System.out.println("5 - Withdraw");
        System.out.println("6 - Apply Interest (months)");
        System.out.println("7 - Show Account Info");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");
    }

    private static void createSavingsAccount() {
        System.out.print("Owner name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Annual interest rate (e.g., 0.03 for 3%): ");
        double rate = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Minimum balance: ");
        double min = Double.parseDouble(scanner.nextLine().trim());

        int id = nextId++;
        SavingsAccount s = new SavingsAccount(id, name, balance, rate, min);
        accounts.put(id, s);
        System.out.println("Created: " + s);
    }

    private static void createCheckingAccount() {
        System.out.print("Owner name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Overdraft limit (positive number): ");
        double overdraftLimit = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Overdraft fee: ");
        double overdraftFee = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Annual interest rate (e.g., 0.0 if none): ");
        double rate = Double.parseDouble(scanner.nextLine().trim());

        int id = nextId++;
        CheckingAccount c = new CheckingAccount(id, name, balance, overdraftLimit, overdraftFee, rate);
        accounts.put(id, c);
        System.out.println("Created: " + c);
    }

    private static void listAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts yet.");
            return;
        }
        accounts.values().forEach(a -> System.out.println(a));
    }

    private static Account getAccountByIdPrompt() {
        System.out.print("Account id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Account acct = accounts.get(id);
        if (acct == null) throw new IllegalArgumentException("Account id not found.");
        return acct;
    }

    private static void deposit() {
        Account acct = getAccountByIdPrompt();
        System.out.print("Amount to deposit: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        acct.deposit(amt);
        System.out.println("New balance: " + acct.getBalance());
    }

    private static void withdraw() throws InsufficientFundsException {
        Account acct = getAccountByIdPrompt();
        System.out.print("Amount to withdraw: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        acct.withdraw(amt);
        System.out.println("New balance: " + acct.getBalance());
    }

    private static void applyInterest() {
        Account acct = getAccountByIdPrompt();
        System.out.print("Months to apply: ");
        int months = Integer.parseInt(scanner.nextLine().trim());
        if (acct instanceof SavingsAccount) {
            ((SavingsAccount) acct).applyInterestMonths(months);
            System.out.println("Interest applied. New balance: " + acct.getBalance());
        } else if (acct instanceof CheckingAccount) {
            ((CheckingAccount) acct).applyInterestMonths(months);
            System.out.println("Interest applied (if any). New balance: " + acct.getBalance());
        } else {
            System.out.println("This account type does not support interest.");
        }
    }

    private static void showAccountInfo() {
        Account acct = getAccountByIdPrompt();
        System.out.println(acct);
    }
}
