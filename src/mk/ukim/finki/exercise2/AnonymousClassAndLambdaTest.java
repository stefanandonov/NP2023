package mk.ukim.finki.exercise2;

interface Printable {
    void print(int a);
}

class A implements Printable {

    @Override
    public void print(int a) {
        System.out.printf("This is normal class A implementing Printable, printing number %d.\n", a);
    }
}

public class AnonymousClassAndLambdaTest {

    public static void main(String[] args) {
        A a = new A();
        a.print(3);

        Printable B = new Printable() {
            @Override
            public void print(int a) {
                System.out.printf("This is anonymous class B implementing Printable, printing number %d.\n", a);
            }
        };
        B.print(5);

        Printable C = (i) ->
                System.out.printf("This is C implementing Printable, printing number %d.\n", i);
        C.print(4);
    }
}
