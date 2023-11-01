package mk.ukim.finki.exercise3.bank;

public class NonInterestCheckingAccount extends Account {

    public NonInterestCheckingAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NON_INTEREST;
    }

    @Override
    public String toString() {
        return super.toString() + " " + getAccountType();
    }
}
