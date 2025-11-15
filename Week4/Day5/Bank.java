// Bank.java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Bank {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final String fileName;

    public Bank(String fileName) {
        this.fileName = fileName;
        load();
    }

    public void addAccount(Account a) {
        accounts.put(a.getId(), a);
        save();
    }

    public Account getAccount(int id) { return accounts.get(id); }

    public Collection<Account> getAllAccounts() { return accounts.values(); }

    public void deposit(int id, double amt) {
        Account a = accounts.get(id);
        if (a == null) throw new NoSuchElementException("Account not found: " + id);
        a.deposit(amt);
        save();
    }

    public void withdraw(int id, double amt) throws InsufficientFundsException {
        Account a = accounts.get(id);
        if (a == null) throw new NoSuchElementException("Account not found: " + id);
        a.withdraw(amt);
        save();
    }

    public void transfer(int fromId, int toId, double amt) throws InsufficientFundsException {
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null) throw new NoSuchElementException("One or more accounts not found");
        // simple transactional behavior: attempt withdraw, then deposit. If withdraw fails nothing changes.
        from.withdraw(amt);
        to.deposit(amt);
        save();
    }

    private void load() {
        Path p = Paths.get(fileName);
        if (!Files.exists(p)) return;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                Account a = Account.fromCsv(line.trim());
                if (a != null) accounts.put(a.getId(), a);
            }
        } catch (IOException e) {
            System.err.println("Failed to load accounts: " + e.getMessage());
        }
    }

    private void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            for (Account a : accounts.values()) {
                bw.write(a.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save accounts: " + e.getMessage());
        }
    }

    public int nextId() {
        return accounts.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}
