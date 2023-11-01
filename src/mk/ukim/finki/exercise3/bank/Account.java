package mk.ukim.finki.exercise3.bank;

public abstract class Account {

    private static long ID = 1L;

    private String accountHolder;
    private long accountID;
    private double balance;
    private AccountType accountType;

    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountID = ID++;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public long getAccountID() {
        return accountID;
    }

    public double getBalance() {
        return balance;
    }

    public abstract AccountType getAccountType();

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f", accountHolder, accountID, balance);
    }
}
