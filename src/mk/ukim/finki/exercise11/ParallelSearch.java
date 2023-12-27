package mk.ukim.finki.exercise11;

import java.util.*;


class StatsThread extends Thread {
    int start;
    int end;
    IntSummaryStatistics is = new IntSummaryStatistics();

    public StatsThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            is.accept(ParallelSearch.ARRAY[i]);
        }
    }

    @Override
    public String toString() {
        return "StatsThread{" +
                "start=" + start +
                ", end=" + end +
                ", summary_statistics=" + is +
                '}';
    }
}

public class ParallelSearch {

    static int NUMBER_OF_ELEMENTS = 100000000;

    static int NUMBER_OF_THREADS = 10;
    static int[] ARRAY;

    static Random RANDOM = new Random();

    public static void main(String[] args) {

        ARRAY = RANDOM.ints(
                NUMBER_OF_ELEMENTS,
                1,
                11
        ).toArray();

//        System.out.println(Arrays.stream(ARRAY).summaryStatistics());


        List<StatsThread> threads = new ArrayList<>();
        int subarrayLength = NUMBER_OF_ELEMENTS/NUMBER_OF_THREADS;
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int start = i * subarrayLength;
            int end = (i + 1) * subarrayLength;
            threads.add(new StatsThread(start,end));
//            System.out.println(start + " , " + end);
        }

        ARRAY[NUMBER_OF_ELEMENTS/2]=-5;
        ARRAY[NUMBER_OF_ELEMENTS/3]=15;

        for (StatsThread thread : threads) {
            thread.start();
        }

        for (StatsThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        threads.forEach(System.out::println);

        int min = threads.stream().mapToInt(t -> t.is.getMin()).min().getAsInt();
        int max = threads.stream().mapToInt(t -> t.is.getMax()).max().getAsInt();

        System.out.println(min);
        System.out.println(max);

    }
}
