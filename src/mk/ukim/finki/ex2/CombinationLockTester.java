package mk.ukim.finki.ex2;

import java.util.Scanner;

class CombinationLock {
    int combination;

    boolean isOpen;

    int attemptsLeft;

    public CombinationLock(){
        this.combination = 111;
        this.attemptsLeft = 3;
        this.isOpen = false;
    }
    public CombinationLock(int combination) {
        this.combination = combination;
        this.attemptsLeft = 3;
        this.isOpen = false;

    }

    public void open (int input){
        if (this.combination == input){
            isOpen = !isOpen;
            if (isOpen){
                System.out.println("Yeey, you opened the lock!!");
            } else {
                System.out.println("Yeey, you locked the lock!!");
            }
            attemptsLeft = 3;

        } else {
            --attemptsLeft;
            System.out.printf("You didn't guess the combination. In %d attempts BOMBAAAA\n", attemptsLeft);
            if (attemptsLeft==0){
                throw new RuntimeException();
            }
        }
    }


}

public class CombinationLockTester {
    public static void main(String[] args) {
        CombinationLock lock = new CombinationLock(456);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int comb = sc.nextInt();
            lock.open(comb);
        }


    }
}
