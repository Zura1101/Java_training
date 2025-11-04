import java.util.Scanner;

public class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(ownerName + " deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(ownerName + " withdrew $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount!");
        }
    }

    // Transfer method
    public void transfer(BankAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            receiver.balance += amount;
            System.out.println("Transferred $" + amount + " from " + ownerName + " to " + receiver.ownerName);
        } else {
            System.out.println("Transfer failed: insufficient funds or invalid amount!");
        }
    }

    // Display account details
    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Owner: " + ownerName);
        System.out.println("Balance: $" + balance);
        System.out.println("---------------------------");
    }

    // Main method to take user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create first account
        System.out.print("Enter account number for first account: ");
        String accNum1 = sc.nextLine();
        System.out.print("Enter owner name: ");
        String owner1 = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double bal1 = sc.nextDouble();
        sc.nextLine(); // consume newline
        BankAccount acc1 = new BankAccount(accNum1, owner1, bal1);

        // Create second account
        System.out.print("\nEnter account number for second account: ");
        String accNum2 = sc.nextLine();
        System.out.print("Enter owner name: ");
        String owner2 = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double bal2 = sc.nextDouble();
        BankAccount acc2 = new BankAccount(accNum2, owner2, bal2);

        System.out.println("\n--- Account Details Before Transfer ---");
        acc1.displayAccountDetails();
        acc2.displayAccountDetails();

        // Perform transfer
        System.out.print("Enter amount to transfer from " + owner1 + " to " + owner2 + ": ");
        double transferAmount = sc.nextDouble();
        acc1.transfer(acc2, transferAmount);

        System.out.println("\n--- Account Details After Transfer ---");
        acc1.displayAccountDetails();
        acc2.displayAccountDetails();

        sc.close();
    }
}
