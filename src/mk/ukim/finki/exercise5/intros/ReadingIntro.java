package mk.ukim.finki.exercise5.intros;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadingIntro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
//        while (sc.hasNextLine()){
//            String line = sc.nextLine();
//            lines.add(line);
//        }

//        List<Integer> numbers = new ArrayList<>();
//        int n;
//        n = sc.nextInt();
//        for (int i=0;i<n;i++){
//            numbers.add(sc.nextInt());
//        }
//
//        System.out.println(numbers);


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        lines = br.lines().collect(Collectors.toList());

        System.out.println(lines);

    }
}
