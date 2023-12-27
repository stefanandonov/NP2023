package mk.ukim.finki.exercise10.first.task1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class QuizEntryCanNotBeProcessedException extends Exception {

    public QuizEntryCanNotBeProcessedException() {
        super("A quiz must have same number of correct and selected answers");
    }
}

class Quiz {
    private final String studentId;
    private final List<String> correctAnswers;
    private final List<String> studentAnswers;

    private double studentPoints;

    public Quiz(String studentId, List<String> correctAnswers, List<String> studentAnswers) throws QuizEntryCanNotBeProcessedException {
        if (correctAnswers.size() != studentAnswers.size())
            throw new QuizEntryCanNotBeProcessedException();

        this.studentId = studentId;
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
        calculateStudentPoints(correctAnswers, studentAnswers);
    }

    private void calculateStudentPoints(List<String> correctAnswers, List<String> studentAnswers) {
        IntStream.range(0, correctAnswers.size())
                .forEach(i -> {
                    if (correctAnswers.get(i).equals(studentAnswers.get(i))) studentPoints++;
                    else studentPoints -= 0.25;
                });
    }

    public static Quiz createQuiz(String line) throws QuizEntryCanNotBeProcessedException {
        String[] parts = line.split(";");
        List<String> correctAnswers = new ArrayList<>(Arrays.asList(parts[1].split(",")));
        List<String> studentAnswers = new ArrayList<>(Arrays.asList(parts[2].split(",")));

        return new Quiz(parts[0], correctAnswers, studentAnswers);
    }

    public String getStudentId() {
        return studentId;
    }

    public double getStudentPoints() {
        return studentPoints;
    }
}

class QuizProcessor {
    public static Map<String, Double> processAnswers(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        return bufferedReader.lines()
                .map(line -> {
                    try {
                        return Quiz.createQuiz(line);
                    } catch (QuizEntryCanNotBeProcessedException exception) {
                        System.out.println(exception.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Quiz::getStudentId, Quiz::getStudentPoints, Double::sum, TreeMap::new));

    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}