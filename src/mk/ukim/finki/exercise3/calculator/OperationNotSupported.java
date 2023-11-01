package mk.ukim.finki.exercise3.calculator;

public class OperationNotSupported extends Exception {

    public OperationNotSupported(char operator) {
        super(String.format("The operator %c is not supported.\n", operator));
    }
}
