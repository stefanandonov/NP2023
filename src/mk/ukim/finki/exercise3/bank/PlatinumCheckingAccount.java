package mk.ukim.finki.exercise3.bank;

public class PlatinumCheckingAccount extends InterestCheckingAccount {

    public PlatinumCheckingAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public void addInterest() {
        super.deposit(super.getBalance() * INTEREST_RATE * 2);
    }

    @Override
    public String toString() {
        return super.toString() + " " + getAccountType();
    }
}