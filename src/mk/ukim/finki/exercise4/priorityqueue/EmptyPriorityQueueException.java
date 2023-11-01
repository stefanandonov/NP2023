package mk.ukim.finki.exercise4.priorityqueue;

public class EmptyPriorityQueueException extends Exception {

    public EmptyPriorityQueueException() {
        super("The queue is empty.");
    }
}
