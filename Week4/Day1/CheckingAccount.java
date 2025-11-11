public class CheckingAccount extends Account {
    private final double overdraftLimit;
    private final double overdraftFee;
    private final double annualInterestRate;

    public CheckingAccount(int id, String name, double balance, double overdraftLimit, double overdraftFee, double annualInterestRate) {
        super(id, name, balance);
        if (overdraftLimit < 0) throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        if (overdraftFee < 0) throw new IllegalArgumentException("Overdraft fee cannot be negative.");
        if (annualInterestRate < 0) throw new IllegalArgumentException("Interest rate cannot be negative.");
        this.overdraftLimit = overdraftLimit;
        this.overdraftFee = overdraftFee;
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        double projected = balance - amount;
        if (projected < -overdraftLimit) {
            throw new InsufficientFundsException(String.format(
                "Exceeds overdraft limit (allowed: -%.2f). Current: %.2f, Requested: %.2f",
                overdraftLimit, balance, amount));
        }
        boolean willBeOverdraft = projected < 0 && balance >= 0;
        balance = projected;
        if (willBeOverdraft) {
            balance -= overdraftFee;
        }
    }

    public double calculateInterestMonths(int months) {
        if (months < 0) throw new IllegalArgumentException("Months cannot be negative.");
        if (balance <= 0 || annualInterestRate == 0.0) return 0.0;
        double monthlyRate = annualInterestRate / 12.0;
        return balance * monthlyRate * months;
    }

    public void applyInterestMonths(int months) {
        double interest = calculateInterestMonths(months);
        balance += interest;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public double getOverdraftFee() { return overdraftFee; }

    @Override
    public String toString() {
        return String.format("CheckingAccount[id=%d, name=%s, balance=%.2f, overdraftLimit=%.2f, fee=%.2f, rate=%.4f]",
            id, name, balance, overdraftLimit, overdraftFee, annualInterestRate);
    }
}
