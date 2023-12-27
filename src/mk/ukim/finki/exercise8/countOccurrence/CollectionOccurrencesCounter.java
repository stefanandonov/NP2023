package mk.ukim.finki.exercise8.countOccurrence;

import java.util.Collection;

public class CollectionOccurrencesCounter {
    public static int count(Collection<Collection<String>> collection, String string) {
        int count = 0;

        for (Collection<String> subCollection : collection) {
            for (String s : subCollection) {
                if (s.equals(string))
                    count++;
            }
        }
        return count;
    }

    public static long countFunctional(Collection<Collection<String>> collection, String string) {
        return collection.stream()
                .mapToLong(subCollection -> subCollection.stream()
                        .filter(s -> s.equals(string))
                        .count())
                .sum();
    }
}
