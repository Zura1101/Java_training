import java.util.*;

class ErrorFundsException extends Exception{
    public ErrorFundsException(String msg){
        super(msg);
    }
}

class Account{
    int id;
    String name;
    double balance;
    
    Account(int id,String name,double balance){
        this.id =id;
        this.name = name;
        this.balance = balance;
    }

    void deposit(double amount){
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + " into " + name + " account.");
        } else {
            System.out.println("Amt must be positive.");
        }

    }
    void withdraw(double amount) throws ErrorFundsException {
        if (amount > balance) {
            throw new ErrorFundsException("Insufficient funds");
        } else if (amount <= 0) {
            System.out.println("Amt must be positive.");
        } else {
            balance -= amount;
            System.out.println("Withdrew " + amount + " from " + name + " account");
        }
        
    }
    void transfer(Account target, double amount) throws ErrorFundsException {
        if (amount <= 0) {
            System.out.println("Amt must be positive");
            return;
        }
        if (amount > balance) {
            throw new ErrorFundsException("Insufficient funds");
        }
        this.balance -= amount;
        target.balance += amount;
        System.out.println("Transferred $" + amount + " from " + this.name + " to " + target.name);

    }
    void display(){
        System.out.println("Account ID: " + id + " | Name: " + name + " | Balance: $" + balance);
    }
}


public class bankapplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1,"Bhargava",200));
        accounts.add(new Account(2,"Ashwad",400));
        
        while(true){
            System.out.println("\nEnter your choice");
            System.out.println("1. Display");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            try {
                switch(choice){
                    case 1:
                        for (Account acc: accounts) acc.display();
                        break;

                    case 2:
                        System.out.println("Enter account Id ");
                        int did = sc.nextInt();
                        System.out.print("Enter amount: ");
                        double damt = sc.nextDouble();
                        find(accounts,did).deposit(damt);
                        break;
                    case 3:
                        System.out.print("Enter account ID: ");
                        int wid = sc.nextInt();
                        System.out.print("Enter amount: ");
                        double wamt = sc.nextDouble();
                        find(accounts, wid).withdraw(wamt);
                        break;
                    case 4:
                        System.out.print("From account ID: ");
                        int from = sc.nextInt();
                        System.out.print("To account ID: ");
                        int to = sc.nextInt();
                        System.out.print("Enter amount: ");
                        double tamt = sc.nextDouble();
                        find(accounts, from).transfer(find(accounts, to), tamt);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (ErrorFundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Account not found!");
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine(); // clear bad input
            }

            

        }
    
    }
    static Account find(ArrayList<Account> list, int id) {
        for (Account a : list) {
            if (a.id == id) return a;
        }
        return null;
    }


    
}
