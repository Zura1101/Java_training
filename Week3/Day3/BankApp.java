// File: BankApp.java

class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message) {
        super(message);
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount);
    }

    public void withdraw(double amount) throws InvalidTransactionException {
        try {
            if (amount <= 0) {
                throw new InvalidTransactionException("Withdrawal amount must be positive!");
            }
            if (amount > balance) {
                throw new InvalidTransactionException("Insufficient funds! Cannot withdraw $" + amount);
            }

            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } 
        catch (InvalidTransactionException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            throw e; // rethrow if you want to handle it outside too
        } 
        finally {
            System.out.println("Transaction complete. Current balance: $" + balance);
            System.out.println("------------------------------------------");
        }
    }

    public void displayBalance() {
        System.out.println("Account: " + accountNumber + " | Balance: $" + balance);
    }
}

public class BankApp {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount("A123", 500);
        acc.displayBalance();

        try {
            System.out.println("\n--- Normal withdrawal ---");
            acc.withdraw(200);  // Valid

            System.out.println("\n--- Invalid negative withdrawal ---");
            acc.withdraw(-50);  // Custom exception

        } catch (InvalidTransactionException e) {
            System.out.println("Handled in main: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Insufficient funds scenario ---");
            acc.withdraw(400);  // Custom exception
        } catch (InvalidTransactionException e) {
            System.out.println("Handled in main: " + e.getMessage());
        }

        System.out.println("\n--- Final Balance ---");
        acc.displayBalance();
    }
}
