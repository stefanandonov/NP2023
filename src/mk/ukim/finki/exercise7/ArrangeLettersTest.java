package mk.ukim.finki.exercise7;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrangeLettersTest {

    public static String transformWord (String word){
        //pSk -> Skp
        String result = word.chars()
                .sorted()
                .mapToObj(ascii -> String.valueOf((char) ascii))
                .collect(Collectors.joining(""));

        return result.substring(0,1).toUpperCase() + result.substring(1);
    }

    private static String transformSentence (String sentence){
        //kO pSk sO -> Ok Os Skp
        return Arrays.stream(sentence.split("\\s+"))
                .map(word -> transformWord(word))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public static void arrange (InputStream is, OutputStream os) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os);

        List<String> sentences = br.lines().collect(Collectors.toList());

        sentences.stream()
                .map(sentence -> transformSentence(sentence))
                .forEach(sentence -> pw.println(sentence));

        pw.flush();

        br.close();
    }
    public static void main(String[] args) {
        try {
            arrange(System.in, System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
