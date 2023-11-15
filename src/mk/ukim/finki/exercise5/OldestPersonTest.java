package mk.ukim.finki.exercise5;

import java.io.*;
import java.util.Comparator;

class Person implements Comparable<Person> {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person (String line){
        String [] parts = line.split("\\s+");
        this.name = parts[0];
        this.age = Integer.parseInt(parts[1]);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.age);
    }
}

class OldestPerson {
    public static Person find (InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines()
                .map(line -> new Person(line))
                .max(Comparator.naturalOrder())
//                .get();
                .orElse(new Person("Stefan", 26));
    }
}

public class OldestPersonTest {
    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream("src/mk/ukim/finki/exercise5/files/people.txt");
            System.out.println(OldestPerson.find(is));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
