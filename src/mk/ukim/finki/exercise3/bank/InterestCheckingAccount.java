package mk.ukim.finki.exercise3.bank;

public class InterestCheckingAccount extends Account implements InterestBearingAccount {

    public static final double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }

    @Override
    public void addInterest() {
        super.deposit(super.getBalance() * INTEREST_RATE);
    }

    @Override
    public String toString() {
        return super.toString() + " " + getAccountType();
    }
}
