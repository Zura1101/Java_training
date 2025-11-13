class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}

public class BankAccount {
    private int accountId;
    private String name;
    private double balance;

    public BankAccount(int accountId, String name, double balance) throws InvalidOperationException {
        if (balance < 0)
            throw new InvalidOperationException("Initial balance cannot be negative!");
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) throws InvalidOperationException {
        if (amount <= 0)
            throw new InvalidOperationException("Deposit amount must be positive!");
        balance += amount;
        System.out.println("Deposited $" + amount + " successfully.");
    }

    public void withdraw(double amount) throws InvalidOperationException, InsufficientFundsException {
        if (amount <= 0)
            throw new InvalidOperationException("Withdrawal amount must be positive!");
        if (amount > balance)
            throw new InsufficientFundsException("Insufficient funds! Available balance: $" + balance);
        balance -= amount;
        System.out.println("Withdrew $" + amount + " successfully.");
    }

    public void display() {
        System.out.println("Account ID: " + accountId + ", Name: " + name + ", Balance: $" + balance);
    }
}
