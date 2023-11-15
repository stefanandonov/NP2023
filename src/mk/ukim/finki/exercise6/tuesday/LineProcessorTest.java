package mk.ukim.finki.exercise6.tuesday;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Line implements Comparable<Line> {
    String line;
    char c;

    public Line(String line, char c) {
        this.line = line;
        this.c = c;
    }

    @Override
    public int compareTo(Line other) {
        return this.countOccurrences() - other.countOccurrences();
    }

    private int countOccurrences() {
        return (int) line.toLowerCase().chars()
                .mapToObj(ascii -> (char) ascii)
                .filter(character -> character == c)
                .count();


//        int counter = 0;
//        for (char character : string.toLowerCase().toCharArray()) {
//            if (character == Character.toLowerCase(c)) {
//                ++counter;
//            }
//        }
//        return counter;
    }
}

class LineProcessor {

    private int countOccurrences(String string, char c) {
        return (int) string.toLowerCase().chars()
                .mapToObj(ascii -> (char) ascii)
                .filter(character -> character == c)
                .count();


//        int counter = 0;
//        for (char character : string.toLowerCase().toCharArray()) {
//            if (character == Character.toLowerCase(c)) {
//                ++counter;
//            }
//        }
//        return counter;
    }


    public void readLines(InputStream in, OutputStream out, char character) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        List<Line> lines = br.lines().map(line -> new Line(line, character)).collect(Collectors.toList());

        String maxLine = lines.stream()
                .max(Comparator.naturalOrder())
                .get().line;


//        maxLine = lines.get(0);
//        int max = 0;
//        for (String line : lines) {
//            int occ = countOccurrences(line, character);
//            if (occ >= max) {
//                maxLine = line;
//                max = occ;
//            }
//        }

        br.close();

        PrintWriter pw = new PrintWriter(out);

        pw.println(maxLine);

        pw.flush();


    }
}

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        try {
            lineProcessor.readLines(System.in, System.out, 'a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

