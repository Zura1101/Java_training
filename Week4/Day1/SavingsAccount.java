public class SavingsAccount extends Account {
    private final double annualInterestRate; // 0.03 => 3%
    private final double minimumBalance;

    public SavingsAccount(int id, String name, double balance, double annualInterestRate, double minimumBalance) {
        super(id, name, balance);
        if (annualInterestRate < 0) throw new IllegalArgumentException("Interest rate cannot be negative.");
        if (minimumBalance < 0) throw new IllegalArgumentException("Minimum balance cannot be negative.");
        this.annualInterestRate = annualInterestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        double projected = balance - amount;
        if (projected < minimumBalance) {
            throw new InsufficientFundsException(String.format(
                "Withdrawal would drop below minimum balance (%.2f). Current: %.2f, Requested: %.2f",
                minimumBalance, balance, amount));
        }
        balance = projected;
    }

    public double calculateInterestMonths(int months) {
        if (months < 0) throw new IllegalArgumentException("Months cannot be negative.");
        double monthlyRate = annualInterestRate / 12.0;
        return balance * monthlyRate * months;
    }

    public void applyInterestMonths(int months) {
        double interest = calculateInterestMonths(months);
        balance += interest;
    }

    public double getAnnualInterestRate() { return annualInterestRate; }
    public double getMinimumBalance() { return minimumBalance; }

    @Override
    public String toString() {
        return String.format("SavingsAccount[id=%d, name=%s, balance=%.2f, rate=%.4f, minBalance=%.2f]",
            id, name, balance, annualInterestRate, minimumBalance);
    }
}
