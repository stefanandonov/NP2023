package mk.ukim.finki.ex1;

import java.util.Objects;

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

public class PersonTest {
    public static void main(String[] args) {
        Person p1 = new Person("Stefan", 25);
        Person p2 = new Person("StEfan", 28);

        Person p3 = p1;
        System.out.println(p1);
        System.out.println(p2);

        System.out.println(p1 == p3);
    }
}
