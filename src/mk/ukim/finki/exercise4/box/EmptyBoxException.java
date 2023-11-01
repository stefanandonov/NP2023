package mk.ukim.finki.exercise4.box;

public class EmptyBoxException extends Exception {

    public EmptyBoxException() {
        super("The box is empty.");
    }
}
