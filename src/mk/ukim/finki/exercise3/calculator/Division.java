package mk.ukim.finki.exercise3.calculator;

public class Division implements Strategy {
    @Override
    public double execute(double num1, double num2) {
        if (num2 == 0)
            throw new ArithmeticException();
        return num1 / num2;
    }
}