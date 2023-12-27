package mk.ukim.finki.exercise10.second.task2;

import java.util.*;
import java.util.stream.Collectors;

class PersonalInformation {
    private final String country;
    private final Integer age;

    PersonalInformation(String country, Integer age) {
        this.country = country;
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public Integer getAge() {
        return age;
    }
}

class User implements Comparable<User> {

    private static Long ID = 1L;
    private final Long id;
    private final String username;
    private final String password;
    private final String email;

    private final PersonalInformation personalInformation;

    User(String username, String password, String email, String country, Integer age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.personalInformation = new PersonalInformation(country, age);
        this.id = ID++;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    @Override
    public String toString() {
        return String.format("{%d: %s, %s}", id, username, email);
    }

    @Override
    public int compareTo(User other) {
        return Comparator.comparing(User::getEmail).compare(this, other);
    }
}

class Membership {
    private static final List<User> users = new ArrayList<>();

    public static void createUser(String userName, String password, String email, String country, Integer age) {
        users.add(new User(userName, password, email, country, age));
    }

    public static boolean deleteUser(int userId) {
        return users.removeIf(u -> u.getId() == userId);
    }

    public static Map<String, Integer> getNDifferentUsersByEmail() {
        Map<String, Integer> usersByEmail = new TreeMap<>();

        for (User u : users) {
            if (!usersByEmail.containsKey(u.getEmail()))
                usersByEmail.put(u.getEmail(), 1);
            else usersByEmail.put(u.getEmail(), usersByEmail.get(u.getEmail()) + 1);
        }
        return usersByEmail;
    }

    public static List<User> getUsersOrderedByEmail() {
        return users.stream().sorted().collect(Collectors.toList());
    }
}

public class MembershipTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < length; i++) {
            String userName = scanner.nextLine();
            String password = scanner.nextLine();
            String email = scanner.nextLine();
            String country = scanner.nextLine();
            Integer age = scanner.nextInt();
            scanner.nextLine();
            Membership.createUser(userName, password, email, country, age);
        }

        for (int i = 0; i < length; i++) {
            System.out.println(Membership.getNDifferentUsersByEmail());
            System.out.println(Membership.getUsersOrderedByEmail());
            System.out.println(Membership.deleteUser(i + 1));
        }

        System.out.println(Membership.deleteUser(1));
    }
}
