package mk.ukim.finki.exercise8.reverseList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListReverse {

    public static <T> void printReversedList(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        Collections.reverse(list);
        System.out.println(list);
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(4);
        integers.add(5);
        printReversedList(integers);
    }
}
