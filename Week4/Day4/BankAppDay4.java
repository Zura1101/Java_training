// BankAppDay4.java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BankAppDay4 {
    private static final String FILE = "accounts_day4.txt";

    public static void main(String[] args) {
        List<Account> accounts = loadAccounts();
        System.out.println("Loaded accounts on start:");
        accounts.forEach(System.out::println);

        // Demo: append a new account
        Account newAcc = new Account(generateId(accounts), "Alice Example", 1500.00);
        appendAccountToFile(newAcc);
        System.out.println("\nAppended account: " + newAcc);

        // Read again to show persisted change
        List<Account> reloaded = loadAccounts();
        System.out.println("\nAfter appending, file contains:");
        reloaded.forEach(System.out::println);
    }

    private static int generateId(List<Account> accounts) {
        return accounts.stream().mapToInt(Account::getId).max().orElse(0) + 1;
    }

    private static List<Account> loadAccounts() {
        List<Account> list = new ArrayList<>();
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return list;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                Account a = Account.fromCsv(line.trim());
                if (a != null) list.add(a);
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
        return list;
    }

    private static void appendAccountToFile(Account a) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(a.toCsv());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error appending account: " + e.getMessage());
        }
    }
}
