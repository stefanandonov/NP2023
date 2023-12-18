package mk.ukim.finki.exercise7;

import java.util.Random;

public class RandomIntro {
    public static void main(String[] args) {
        Random random = new Random(); //

//        random.nextInt(1,3);//1,2
        random.nextDouble(); //[0.00,1.00)
        int head = 0, tail = 0;
        int experiments = 10000000;
        head = random.ints(experiments, 0, 2).sum();
        tail = experiments - head;
        System.out.println(head*100.0/experiments + " " + tail*100.0/experiments);


//        for (int i = 0; i < 100000000; i++) {
//            if (new Random().nextInt(2) == 0) {
//                head++;
//            } else {
//                tail++;
//            }
//        }
//
//        System.out.println(head / 1000000.0 + " " + tail / 1000000.0);
    }
}
