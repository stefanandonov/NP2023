package mk.ukim.finki.exercise3.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Bank {

    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public double totalAssets() {
        double sum = 0;

        for (Account account : accounts)
            sum += account.getBalance();
        return sum;

        /*
          Alternative solutions:
                      return accounts.stream().mapToDouble(account -> account.getBalance()).sum();
                      return accounts.stream().mapToDouble(Account::getBalance).sum();
         */
    }

    public void addInterest() {
        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.INTEREST)
                ((InterestBearingAccount) account).addInterest();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Account account : accounts)
            stringBuilder.append(account.toString()).append("\n");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numAccounts = Integer.parseInt(scanner.nextLine());

        Random random = new Random();
        Bank bank = new Bank();

        for (int i = 0; i < numAccounts; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\s++");

            int type = random.nextInt(3);
            if (type == 0)
                bank.addAccount(new InterestCheckingAccount(parts[0], Double.parseDouble(parts[1])));
            else if (type == 1)
                bank.addAccount(new PlatinumCheckingAccount(parts[0], Double.parseDouble(parts[1])));
            else
                bank.addAccount(new NonInterestCheckingAccount(parts[0], Double.parseDouble(parts[1])));
        }

        System.out.println("--------- TEST BANK AND TOTAL ASSETS ---------");
        System.out.println(bank);
        System.out.println(bank.totalAssets());

        System.out.println("--------- TEST ADD INTEREST ---------");
        bank.addInterest();
        System.out.println(bank.totalAssets());
    }
}
