package mk.ukim.finki.exercise7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenfordLaw {

    private static String asterisks (int n){
        return IntStream.range(0,n)
                .mapToObj(i -> "*")
                .collect(Collectors.joining(""));
    }

    public static void testLaw(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<Integer> firstDigits = br.lines()
                .map(number -> String.valueOf(number.charAt(0)))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

//        List<Integer> counter = new ArrayList<>();
//        IntStream.range(0,10).forEach(i -> counter.add(0));

        int counter[] = new int[10];
        firstDigits.forEach(firstDigit -> counter[firstDigit]++);

        for (int i=1;i<10;i++){
            double frequency = (100.0*counter[i])/firstDigits.size();
            System.out.println(String.format("%d -> %s (%.2f%%)", i, asterisks((int)frequency), frequency));
        }

    }

    public static void main(String[] args) {
        try {
            BenfordLaw.testLaw(new FileInputStream("src/mk/ukim/finki/exercise7/librarybook.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
