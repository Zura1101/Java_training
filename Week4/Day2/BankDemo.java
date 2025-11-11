// Parent class
class Account {
    int accountId;
    String holderName;
    double balance;

    Account(int accountId, String holderName, double balance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
    }

    // Method to withdraw money
    void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(holderName + " withdrew $" + amount + ", new balance: $" + balance);
        } else {
            System.out.println("Insufficient funds for " + holderName);
        }
    }

    // Method to display account type
    void displayAccountType() {
        System.out.println("This is a general Account");
    }
}

// Child class 1
class SavingsAccount extends Account {
    double interestRate = 0.05;

    SavingsAccount(int accountId, String holderName, double balance) {
        super(accountId, holderName, balance);
    }

    // Overriding withdraw() method
    @Override
    void withdraw(double amount) {
        if (balance - amount < 500) {
            System.out.println("Cannot withdraw. Minimum balance of $500 required for " + holderName);
        } else {
            balance -= amount;
            System.out.println(holderName + " withdrew $" + amount + " from Savings. New balance: $" + balance);
        }
    }

    @Override
    void displayAccountType() {
        System.out.println("This is a Savings Account");
    }
}

// Child class 2
class CheckingAccount extends Account {
    double overdraftLimit = 1000;

    CheckingAccount(int accountId, String holderName, double balance) {
        super(accountId, holderName, balance);
    }

    // Overriding withdraw() method
    @Override
    void withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println(holderName + " withdrew $" + amount + " from Checking. New balance: $" + balance);
        } else {
            System.out.println("Overdraft limit exceeded for " + holderName);
        }
    }

    @Override
    void displayAccountType() {
        System.out.println("This is a Checking Account");
    }
}

// Main class to test overriding
public class BankDemo {
    public static void main(String[] args) {
        // Parent class reference -> Child class object
        Account acc1 = new SavingsAccount(101, "Alice", 2000);
        Account acc2 = new CheckingAccount(102, "Bob", 1000);

        // Calls overridden methods based on actual object type
        acc1.displayAccountType();
        acc1.withdraw(1800); // SavingsAccount rule applies

        acc2.displayAccountType();
        acc2.withdraw(1500); // CheckingAccount rule applies
    }
}
