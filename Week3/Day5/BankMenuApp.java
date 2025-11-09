import java.util.*;

// Custom exception for invalid operations
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Account class
class Account {
    private int id;
    private String name;
    private double balance;

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw method with exception handling
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new InsufficientFundsException("Invalid withdrawal amount.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient balance for withdrawal.");
        }
        balance -= amount;
        System.out.println("Withdrawn $" + amount + " successfully.");
    }

    // Transfer method between accounts
    public void transfer(Account target, double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new InsufficientFundsException("Invalid transfer amount.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient balance for transfer.");
        }
        balance -= amount;
        target.deposit(amount);
        System.out.println("Transferred $" + amount + " from " + name + " to " + target.getName() + ".");
    }

    // Display account details
    public void display() {
        System.out.println("Account ID: " + id + " | Name: " + name + " | Balance: $" + balance);
    }
}

// Main class — file must be named BankMenuApp.java
public class BankMenuApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();

        // Create sample accounts
        accounts.add(new Account(1, "Alice", 5000));
        accounts.add(new Account(2, "Bob", 3000));
        accounts.add(new Account(3, "Charlie", 7000));

        int choice = 0;
        do {
            System.out.println("\n====== BANK MENU ======");
            System.out.println("1. Display All Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1:
                        for (Account acc : accounts) {
                            acc.display();
                        }
                        break;

                    case 2:
                        System.out.print("Enter Account ID: ");
                        int depId = Integer.parseInt(sc.nextLine().trim());
                        Account depAcc = findAccount(accounts, depId);
                        if (depAcc != null) {
                            System.out.print("Enter amount to deposit: ");
                            double depAmt = Double.parseDouble(sc.nextLine().trim());
                            depAcc.deposit(depAmt);
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Account ID: ");
                        int wId = Integer.parseInt(sc.nextLine().trim());
                        Account wAcc = findAccount(accounts, wId);
                        if (wAcc != null) {
                            System.out.print("Enter amount to withdraw: ");
                            double wAmt = Double.parseDouble(sc.nextLine().trim());
                            wAcc.withdraw(wAmt);
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter From Account ID: ");
                        int fromId = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter To Account ID: ");
                        int toId = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter amount to transfer: ");
                        double tAmt = Double.parseDouble(sc.nextLine().trim());
                        Account fromAcc = findAccount(accounts, fromId);
                        Account toAcc = findAccount(accounts, toId);
                        if (fromAcc != null && toAcc != null) {
                            fromAcc.transfer(toAcc, tAmt);
                        } else {
                            System.out.println("Invalid account(s).");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting system. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        } while (choice != 5);

        sc.close();
    }

    // Helper method to find account by ID (must be inside the class)
    public static Account findAccount(ArrayList<Account> accounts, int id) {
        for (Account acc : accounts) {
            if (acc.getId() == id)
                return acc;
        }
        return null;
    }
}
