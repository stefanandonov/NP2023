package mk.ukim.finki.exercise6.wednesday;

import java.util.*;
import java.util.stream.Collectors;

interface NumberProcessor<T extends Number, R> {
    R compute(ArrayList<T> numbers);
}

class Numbers<T extends Number> {

    ArrayList<T> elements;

    public Numbers(ArrayList<T> elements) {
        this.elements = elements;
    }

    void process(NumberProcessor<T, ?> processor) {
        System.out.println(processor.compute(elements));
    }
}

public class NumberProcessorTest {

    public static void main(String[] args) {

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Double> doubleArrayList = new ArrayList<>();

        int countOfIntegers;
        Scanner sc = new Scanner(System.in);
        countOfIntegers = sc.nextInt();
        while (countOfIntegers > 0) {
            integerArrayList.add(sc.nextInt());
            --countOfIntegers;
        }

        int countOfDoubles;
        countOfDoubles = sc.nextInt();
        while (countOfDoubles > 0) {
            doubleArrayList.add(sc.nextDouble());
            --countOfDoubles;
        }

        Numbers<Integer> integerNumbers = new Numbers<>(integerArrayList);

        //TODO first processor
        NumberProcessor<Integer, Long>firstProcessor = list -> list.stream().filter(i -> i<0).count();
        System.out.println("RESULTS FROM THE FIRST NUMBER PROCESSOR");
        integerNumbers.process(firstProcessor);

        //TODO second processor
        NumberProcessor<Integer, String >secondProcessor = numbers -> {
            IntSummaryStatistics iss = numbers.stream().mapToInt(i -> i).summaryStatistics();
            return String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f", iss.getCount(), (float) iss.getMin(), iss.getAverage(), (float) iss.getMax());
        };
        System.out.println("RESULTS FROM THE SECOND NUMBER PROCESSOR");
        integerNumbers.process(secondProcessor);

        Numbers<Double> doubleNumbers = new Numbers<>(doubleArrayList);

        //TODO third processor
        NumberProcessor<Double, ArrayList<Double>>thirdProcessor = list -> list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toCollection(ArrayList::new));

        System.out.println("RESULTS FROM THE THIRD NUMBER PROCESSOR");
        doubleNumbers.process(thirdProcessor);

        //TODO fourth processor
        NumberProcessor<Double, Double> fourthProcessor = list -> {
            list = thirdProcessor.compute(list);
            if (list.size()%2==0){
                return (list.get(list.size()/2-1) + list.get(list.size()/2))/2.0;
            } else {
                return list.get(list.size()/2);
            }
        };
        System.out.println("RESULTS FROM THE FOURTH NUMBER PROCESSOR");
        doubleNumbers.process(fourthProcessor);

    }

}

