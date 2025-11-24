import java.io.*;
import java.util.*;

// Custom Exceptions
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

// Abstract Base Class - Inheritance
abstract class Account implements Serializable {
    protected String accNum;
    protected String owner;
    protected double balance;
    
    public Account(String accNum, String owner, double balance) {
        this.accNum = accNum;
        this.owner = owner;
        this.balance = balance;
    }
    
    // Abstract methods - must be implemented by subclasses
    public abstract String getType();
    public abstract double calcInterest();
    
    public double deposit(double amt) throws InvalidAmountException {
        if (amt <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        balance += amt;
        return balance;
    }
    
    public double withdraw(double amt) throws InvalidAmountException, InsufficientFundsException {
        if (amt <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        if (amt > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amt;
        return balance;
    }
    
    public String getAccNum() { return accNum; }
    public String getOwner() { return owner; }
    public double getBalance() { return balance; }
    
    @Override
    public String toString() {
        return String.format("%s %s: %s - $%.2f", getType(), accNum, owner, balance);
    }
}

// Concrete Class 1 - Polymorphism
class SavingsAccount extends Account {
    private static final double MIN_BAL = 500;
    
    public SavingsAccount(String accNum, String owner, double balance) throws Exception {
        super(accNum, owner, balance);
        if (balance < MIN_BAL) {
            throw new Exception("Minimum balance $" + MIN_BAL + " required");
        }
    }
    
    @Override
    public String getType() {
        return "SAVINGS";
    }
    
    @Override
    public double calcInterest() {
        double interest = balance * 0.03;
        balance += interest;
        return interest;
    }
    
    @Override
    public double withdraw(double amt) throws InvalidAmountException, InsufficientFundsException {
        if (balance - amt < MIN_BAL) {
            throw new InsufficientFundsException("Cannot go below $" + MIN_BAL);
        }
        return super.withdraw(amt);
    }
}

// Concrete Class 2 - Polymorphism
class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000;
    
    public CheckingAccount(String accNum, String owner, double balance) {
        super(accNum, owner, balance);
    }
    
    @Override
    public String getType() {
        return "CHECKING";
    }
    
    @Override
    public double calcInterest() {
        return 0; // No interest on checking
    }
    
    @Override
    public double withdraw(double amt) throws InvalidAmountException, InsufficientFundsException {
        if (amt > balance + OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException("Overdraft limit exceeded");
        }
        balance -= amt;
        return balance;
    }
}

// Bank System - File Persistence
public class Bank {
    private Map<String, Account> accounts;
    private String filename;
    
    public Bank(String filename) {
        this.accounts = new HashMap<>();
        this.filename = filename;
        load();
    }
    
    public Account createAccount(String type, String accNum, String owner, double balance) 
            throws Exception {
        Account acc;
        
        if (type.equals("SAVINGS")) {
            acc = new SavingsAccount(accNum, owner, balance);
        } else if (type.equals("CHECKING")) {
            acc = new CheckingAccount(accNum, owner, balance);
        } else {
            throw new Exception("Invalid account type");
        }
        
        accounts.put(accNum, acc);
        save();
        return acc;
    }
    
    public Account getAccount(String accNum) throws Exception {
        if (!accounts.containsKey(accNum)) {
            throw new Exception("Account " + accNum + " not found");
        }
        return accounts.get(accNum);
    }
    
    public void transfer(String fromNum, String toNum, double amt) 
            throws Exception, InvalidAmountException, InsufficientFundsException {
        Account fromAcc = getAccount(fromNum);
        Account toAcc = getAccount(toNum);
        
        fromAcc.withdraw(amt);
        toAcc.deposit(amt);
        save();
    }
    
    public void applyInterest() {
        // Polymorphism - each account type calculates interest differently
        for (Account acc : accounts.values()) {
            acc.calcInterest();
        }
        save();
    }
    
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
    
    // File Persistence - Save using Java Serialization
    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }
    
    // File Persistence - Load using Java Serialization
    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            accounts = (Map<String, Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }
    
    // Interactive User Menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank("bank.dat");
        
        System.out.println("=== Banking System ===\n");
        
        while (true) {
            try {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Check Balance");
                System.out.println("6. Apply Interest");
                System.out.println("7. View All Accounts");
                System.out.println("8. Exit");
                System.out.print("Choose option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1: // Create Account
                        System.out.print("Account Type (SAVINGS/CHECKING): ");
                        String type = scanner.nextLine().toUpperCase();
                        System.out.print("Account Number: ");
                        String accNum = scanner.nextLine();
                        System.out.print("Owner Name: ");
                        String owner = scanner.nextLine();
                        System.out.print("Initial Balance: $");
                        double balance = scanner.nextDouble();
                        
                        Account acc = bank.createAccount(type, accNum, owner, balance);
                        System.out.println("✓ Created: " + acc);
                        break;
                        
                    case 2: // Deposit
                        System.out.print("Account Number: ");
                        String depAcc = scanner.nextLine();
                        System.out.print("Amount: $");
                        double depAmt = scanner.nextDouble();
                        
                        Account a1 = bank.getAccount(depAcc);
                        a1.deposit(depAmt);
                        bank.save();
                        System.out.println("✓ Deposited. New balance: $" + a1.getBalance());
                        break;
                        
                    case 3: // Withdraw
                        System.out.print("Account Number: ");
                        String withAcc = scanner.nextLine();
                        System.out.print("Amount: $");
                        double withAmt = scanner.nextDouble();
                        
                        Account a2 = bank.getAccount(withAcc);
                        a2.withdraw(withAmt);
                        bank.save();
                        System.out.println("✓ Withdrawn. New balance: $" + a2.getBalance());
                        break;
                        
                    case 4: // Transfer
                        System.out.print("From Account: ");
                        String fromAcc = scanner.nextLine();
                        System.out.print("To Account: ");
                        String toAcc = scanner.nextLine();
                        System.out.print("Amount: $");
                        double transAmt = scanner.nextDouble();
                        
                        bank.transfer(fromAcc, toAcc, transAmt);
                        System.out.println("✓ Transfer completed");
                        break;
                        
                    case 5: // Check Balance
                        System.out.print("Account Number: ");
                        String balAcc = scanner.nextLine();
                        Account a3 = bank.getAccount(balAcc);
                        System.out.println(a3);
                        break;
                        
                    case 6: // Apply Interest
                        bank.applyInterest();
                        System.out.println("✓ Interest applied to all accounts");
                        break;
                        
                    case 7: // View All
                        System.out.println("\n--- All Accounts ---");
                        for (Account account : bank.getAllAccounts()) {
                            System.out.println(account);
                        }
                        break;
                        
                    case 8: // Exit
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                        
                    default:
                        System.out.println("Invalid option!");
                }
                
            } catch (InvalidAmountException | InsufficientFundsException e) {
                System.out.println("✗ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine(); // clear buffer
            }
        }
    }
}