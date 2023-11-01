package mk.ukim.finki.exercise2;

abstract class Animal {

    abstract void sound();
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("DOG SOUND");
    }
}

class Mouse extends Animal {

    @Override
    void sound() {
        System.out.println("MOUSE SOUND");
    }
}

public class InheritanceTest {

    public static void main(String[] args) {
        Animal[] animals = new Animal[3];
        animals[0] = new Dog();
        animals[1] = new Dog();
        animals[2] = new Mouse();

        for (Animal a : animals)
            a.sound();
    }
}
