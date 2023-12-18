//package mk.ukim.finki.exercise9;
//
//import java.util.*;
//
//class Participant {
//    String code;
//    String name;
//    int age;
//
//    public Participant(String code, String name, int age) {
//        this.code = code;
//        this.name = name;
//        this.age = age;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %s %d", code, name, age);
//    }
//}
//
//class Audition {
//
//    Map<String, TreeSet<Participant>> participantsByCity = new HashMap<>();
//
//    Map<String, HashSet<String>> idsByCity = new HashMap<>();
//
//    public void addParticpant(String city, String code, String name, int age) {
//        Participant participant = new Participant(code, name, age);
//
//        idsByCity.putIfAbsent(city, new HashSet<>());
//        participantsByCity.putIfAbsent(city, new TreeSet<>(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode)));
//
//        if (idsByCity.get(city).contains(code)){
//            return ;
//        }
//
//        idsByCity.get(city).add(code);
//        participantsByCity.get(city).add(participant);
//
//    }
//
//    public void listByCity(String city) {
//        participantsByCity.get(city).forEach(p -> System.out.println(p));
//    }
//}
//
//public class AuditionTest {
//    public static void main(String[] args) {
//        Audition audition = new Audition();
//        List<String> cities = new ArrayList<String>();
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            if (parts.length > 1) {
//                audition.addParticpant(parts[0], parts[1], parts[2],
//                        Integer.parseInt(parts[3]));
//            } else {
//                cities.add(line);
//            }
//        }
//        for (String city : cities) {
//            System.out.printf("+++++ %s +++++\n", city);
//            audition.listByCity(city);
//        }
//        scanner.close();
//    }
//}