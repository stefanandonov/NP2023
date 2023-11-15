package mk.ukim.finki.exercise6.monday;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

abstract class Question implements Comparable<Question> {
    String text;
    int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points, o.points);
    }

    abstract float answer(String studentAnswer);
}

class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}


class QuestionFactory {

    static List<String> ALLOWED_ANSWERS = Arrays.asList("A", "B", "C", "D", "E");


    static Question create(String line) throws InvalidOperationException {
        //MC;Question1;3;E
        //TF;Question3;2;false
        String[] parts = line.split(";");
        String type = parts[0];
        String text = parts[1];
        int points = Integer.parseInt(parts[2]);
        String answer = parts[3];
        if (type.equals("MC")) {
            if (!ALLOWED_ANSWERS.contains(answer)) {
                throw new InvalidOperationException(String.format("%s is not allowed option for this question", answer));
            }
            return new MultipleChoiceQuestion(text, points, answer);
        } else { //TF
            return new TrueFalseQuestion(text, points, Boolean.parseBoolean(answer));
        }
    }
}


class TrueFalseQuestion extends Question {
    boolean answer;

    public TrueFalseQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //True/False Question: Question3 Points: 2 Answer: false
        return String.format("True/False Question: %s Points: %d Answer: %s", text, points, answer);
    }

    @Override
    float answer(String studentAnswer) {
        return (answer == Boolean.parseBoolean(studentAnswer)) ? points : 0.0f;
    }
}

class MultipleChoiceQuestion extends Question {
    String answer;

    public MultipleChoiceQuestion(String text, int points, String answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //Multiple Choice Question: Question1 Points 3 Answer: E
        return String.format("Multiple Choice Question: %s Points %d Answer: %s", text, points, answer);
    }

    @Override
    float answer(String studentAnswer) {
        return answer.equals(studentAnswer) ? points : (points * -0.2f);
    }
}

class Quiz {

    List<Question> questions;

    Quiz() {
        questions = new ArrayList<>();
    }


    void addQuestion(String questionData) throws InvalidOperationException {

        questions.add(QuestionFactory.create(questionData));

    }

    public void printQuiz(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        questions.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(q -> pw.println(q));

        pw.flush();

    }

    public void answerQuiz(List<String> answers, OutputStream out) throws InvalidOperationException {

        if (questions.size()!=answers.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        PrintWriter pw = new PrintWriter(out);

        float sum = 0;
        for (int i=0;i<answers.size();i++){
            float points = questions.get(i).answer(answers.get(i));
            pw.println(String.format("%d. %.2f", i+1,  points));
            sum+=points;
        }
        pw.println(String.format("Total points: %.2f", sum));


        pw.flush();
    }
}

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < questions; i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < answersCount; i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) {
            quiz.printQuiz(System.out);
        } else if (testCase == 2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
