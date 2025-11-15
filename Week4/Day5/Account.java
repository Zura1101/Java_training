// Account.java (same file name as Day4; re-use or merge)
public class Account {
    private int id;
    private String name;
    private double balance;

    public Account(int id, String name, double balance) {
        this.id = id; this.name = name; this.balance = balance;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    public void deposit(double amt) {
        if (amt <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amt;
    }

    public void withdraw(double amt) throws InsufficientFundsException {
        if (amt <= 0) throw new IllegalArgumentException("Withdraw must be positive");
        if (amt > balance) throw new InsufficientFundsException("Insufficient funds for account " + id);
        balance -= amt;
    }

    public String toCsv() { return id + "," + name.replace(",", " ") + "," + balance; }
    public static Account fromCsv(String line) {
        String[] p = line.split(",", 3);
        if (p.length < 3) return null;
        return new Account(Integer.parseInt(p[0]), p[1], Double.parseDouble(p[2]));
    }

    @Override public String toString() {
        return String.format("ID: %d | %s | $%.2f", id, name, balance);
    }
}
