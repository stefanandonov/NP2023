package mk.ukim.finki.exercise3.calculator;

public class Calculator {

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private double state;
    private Strategy strategy;

    public Calculator() {
        state = 0.0;
        strategy = new Addition();
    }

    public String execute(char operator, double value) throws OperationNotSupported {
        if (operator == PLUS) {
            strategy = new Addition();
        } else if (operator == MINUS) {
            strategy = new Subtraction();
        } else if (operator == MULTIPLY) {
            strategy = new Multiplication();
        } else if (operator == DIVIDE) {
            strategy = new Division();
        } else {
            throw new OperationNotSupported(operator);
        }

        state = strategy.execute(state, value);
        return String.format("result %c %f = %f", operator, value, state);
    }

    public void init() {
        System.out.println("Calculator is ON.");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("result = %.2f", state);
    }
}
