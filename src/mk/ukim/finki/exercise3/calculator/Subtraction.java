package mk.ukim.finki.exercise3.calculator;

public class Subtraction implements Strategy{
    @Override
    public double execute(double num1, double num2) {
        return num1 - num2;
    }
}
