package mk.ukim.finki.exercise9;

import java.util.*;
import java.util.stream.Collectors;

class Participant {
    String city;
    String code;
    String name;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }
}

class Audition {

    List<Participant> participants = new ArrayList<>();

    Map<String, HashSet<String>> idsByCity = new HashMap<>();

    public void addParticpant(String city, String code, String name, int age) {
        idsByCity.putIfAbsent(city, new HashSet<>());

        if (idsByCity.get(city).contains(code)) {
            return;
        }

        participants.add(new Participant(city, code, name, age));


        idsByCity.get(city).add(code);

    }

    /*
    Stefan 1000
    Petko 200
    Riste 300
    Stefan 500
    Riste 150

    =

    Stefan 1500
    Petko 200
    Riste 450


    Stefan 2
    Petko 1
    Riste 2
    * */

    public void listByCity(String city) {

//        Map<String, Long> map = participants.stream().collect(Collectors.groupingBy(
//                Participant::getCity,
//                Collectors.counting()
//        ));

        Map<String, Double> map = participants.stream().collect(Collectors.groupingBy(
                Participant::getCity,
                Collectors.averagingInt(p -> p.age)
        ));

        Map<String, Integer> map1 = participants.stream().collect(Collectors.toMap(
                Participant::getCity,
                p -> 1,
                (v1, v2) -> v1 + v2
        ));
//

        Map<String, TreeSet<Participant>> participantsByCity = participants.stream()
                .collect(Collectors.groupingBy(
                        Participant::getCity,
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode)))
                ));

        for (Participant participant : participantsByCity.get(city)) {
            System.out.println(participant);
        }

        System.out.println(map);

    }
}

public class AuditionTest2 {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}