// Account.java
public class Account {
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
    public void deposit(double amt) { balance += amt; }
    public void withdraw(double amt) { balance -= amt; }

    // CSV representation: id,name,balance
    public String toCsv() {
        return id + "," + escape(name) + "," + balance;
    }

    public static Account fromCsv(String csvLine) {
        // simplistic split (names should not contain commas for this simple example)
        String[] parts = csvLine.split(",", 3);
        if (parts.length < 3) return null;
        int id = Integer.parseInt(parts[0]);
        String name = unescape(parts[1]);
        double bal = Double.parseDouble(parts[2]);
        return new Account(id, name, bal);
    }

    private static String escape(String s) {
        return s.replace(",", " "); // simple escape for demo
    }

    private static String unescape(String s) {
        return s;
    }

    @Override
    public String toString() {
        return "Account ID: " + id + ", Name: " + name + ", Balance: " + balance;
    }
}
