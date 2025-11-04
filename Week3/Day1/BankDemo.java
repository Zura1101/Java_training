public class BankDemo {
    public static void main(String[] args) {
        Account acc1 = new Account("Alice", 500);
        Account acc2 = new Account("Bob", 1000);

        acc1.deposit(200);
        acc1.withdraw(100);
        acc1.displayBalance();

        System.out.println("--------------------------");

        acc2.deposit(300);
        acc2.withdraw(500);
        acc2.displayBalance();
    }
}
