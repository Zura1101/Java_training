import java.util.Scanner;

// Class to represent a Bank Customer Account
class CustomerAccount {
    int accountNumber;
    String customerName;
    double accountBalance;

    // Constructor
    public CustomerAccount(int accountNumber, String customerName, double accountBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
    }

    // Display method
    public void showDetails() {
        System.out.println("Account Number: " + accountNumber + 
                           ", Name: " + customerName + 
                           ", Balance: $" + accountBalance);
    }
}

// Main class
public class BankSystem {
    public static void main(String[] args) {
        // Step 1: Create multiple CustomerAccount objects
        CustomerAccount[] customers = new CustomerAccount[3];
        customers[0] = new CustomerAccount(2001, "John Doe", 4500.00);
        customers[1] = new CustomerAccount(2002, "Jane Smith", 7200.50);
        customers[2] = new CustomerAccount(2003, "David Lee", 9100.75);

        // Step 2: Display all accounts
        System.out.println("All Customer Accounts:");
        for (CustomerAccount customer : customers) {
            customer.showDetails();
        }

        // Step 3: Search for account by account number
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter Account Number to Search: ");
        int searchNumber = input.nextInt();

        boolean found = false;
        for (CustomerAccount customer : customers) {
            if (customer.accountNumber == searchNumber) {
                System.out.println("Account Found:");
                customer.showDetails();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Account not found!");
        }

        input.close();
    }
}
