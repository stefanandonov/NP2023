package mk.ukim.finki.exercise7;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPicker {
    static Random RANDOM = new Random();

    public static void assignPrizes (int participants, int prizes){

        if (prizes>participants){
            throw new RuntimeException();
        }

        List<Integer> availablePeople = IntStream.range(1,participants+1)
                .boxed()
                .collect(Collectors.toList());;

        for (int i=1;i<=prizes;i++){
            System.out.println(String.format("%d prize goes to participant with ID: %d", i, availablePeople.remove(RANDOM.nextInt(availablePeople.size()))));
        }
    }

    public static void main(String[] args) {
        RandomPicker.assignPrizes(300,20);
    }
}
