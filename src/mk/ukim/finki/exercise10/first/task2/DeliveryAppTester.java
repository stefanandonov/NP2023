package mk.ukim.finki.exercise10.first.task2;

import java.util.*;

interface Location {
    int getX();

    int getY();

    default int distance(Location other) {
        int xDiff = Math.abs(getX() - other.getX());
        int yDiff = Math.abs(getY() - other.getY());
        return xDiff + yDiff;
    }
}

class LocationCreator {
    public static Location create(int x, int y) {

        return new Location() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
    }
}

class Address {

    private String name;

    private Location location;

    public Address(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}

class User {

    private String id;
    private String name;

    private Map<String, Address> addresses;

    private List<Float> moneySpent;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        addresses = new HashMap<>();
        moneySpent = new ArrayList<>();
    }

    public double totalMoneySpent() {
        return moneySpent.stream().mapToDouble(i -> i).sum();
    }

    public double averageMoneySpent() {
        if (moneySpent.isEmpty()) return 0;
        return totalMoneySpent() / moneySpent.size();
    }

    public void addAddress(String name, Location location) {
        addresses.put(name, new Address(name, location));
    }

    public void processOrder(float cost) {
        moneySpent.add(cost);
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount spent: %.2f Average amount spent: %.2f",
                id, name, moneySpent.size(), totalMoneySpent(), averageMoneySpent());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Address> getAddresses() {
        return addresses;
    }

    public List<Float> getMoneySpent() {
        return moneySpent;
    }
}

class DeliveryPerson {
    private final String id;
    private final String name;
    private Location currentLocation;

    private List<Float> moneyEarned;

    public DeliveryPerson(String id, String name, Location currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        moneyEarned = new ArrayList<>();
    }

    public double totalMoneyEarned() {
        return moneyEarned.stream().mapToDouble(i -> i).sum();
    }

    public double averageMoneyEarned() {
        if (moneyEarned.isEmpty()) return 0;
        return totalMoneyEarned() / moneyEarned.size();
    }

    public int compareDistanceToRestaurant(DeliveryPerson other, Location restaurantLocation) {
        int currentDistance = currentLocation.distance(restaurantLocation);
        int otherDistance = other.currentLocation.distance(restaurantLocation);
        if (currentDistance == otherDistance) {
            return Integer.compare(this.moneyEarned.size(), other.moneyEarned.size());
        } else return currentDistance - otherDistance;
    }

    public void processOrder(int distance, Location location) {
        currentLocation = location;
        moneyEarned.add((float) (90 + 10 * (distance / 10)));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public List<Float> getMoneyEarned() {
        return moneyEarned;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total deliveries: %d Total delivery fee: %.2f Average delivery fee: %.2f",
                id, name, moneyEarned.size(), totalMoneyEarned(), averageMoneyEarned());
    }
}

class Restaurant {

    private final String id;
    private final String name;
    private final Location location;

    private List<Float> moneyEarned;

    public Restaurant(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        moneyEarned = new ArrayList<>();
    }

    public double totalMoneyEarned() {
        return moneyEarned.stream().mapToDouble(i -> i).sum();
    }

    public double averageMoneyEarned() {
        if (moneyEarned.isEmpty()) return 0;
        return totalMoneyEarned() / moneyEarned.size();
    }

    public void processOrder(float cost) {
        moneyEarned.add(cost);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %d Total amount earned: %.2f Average amount earned: %.2f",
                id, name, moneyEarned.size(), totalMoneyEarned(), averageMoneyEarned());
    }
}


class DeliveryApp {
    private final String appName;
    private final Map<String, User> users;
    private final Map<String, DeliveryPerson> deliveryPeople;
    private final Map<String, Restaurant> restaurants;

    public DeliveryApp(String appName) {
        this.appName = appName;
        users = new HashMap<>();
        deliveryPeople = new HashMap<>();
        restaurants = new HashMap<>();
    }

    public void addUser(String id, String name) {
        users.put(id, new User(id, name));
    }

    public void addRestaurant(String id, String name, Location location) {
        restaurants.put(id, new Restaurant(id, name, location));
    }

    public void registerDeliveryPerson(String id, String name, Location location) {
        deliveryPeople.put(id, new DeliveryPerson(id, name, location));
    }

    public void addAddress(String id, String name, Location location) {
        users.get(id).addAddress(name, location);
    }

    public void orderFood(String userId, String userAddressName, String restaurantId, float cost) {
        User user = users.get(userId);
        Address address = user.getAddresses().get(userAddressName);
        Restaurant restaurant = restaurants.get(restaurantId);

        DeliveryPerson deliveryPerson = deliveryPeople.values().stream()
                .min((l, r) -> l.compareDistanceToRestaurant(r, restaurant.getLocation())).get();

        int distance = deliveryPerson.getCurrentLocation().distance(restaurant.getLocation());
        deliveryPerson.processOrder(distance, address.getLocation());
        user.processOrder(cost);
        restaurant.processOrder(cost);
    }

    public void printUsers() {
        users.values().stream().sorted(Comparator.comparing(User::totalMoneySpent).thenComparing(User::getId).reversed()).forEach(System.out::println);
    }

    public void printRestaurants() {
        restaurants.values().stream().sorted(Comparator.comparing(Restaurant::averageMoneyEarned).thenComparing(Restaurant::getId).reversed()).forEach(System.out::println);
    }

    public void printDeliveryPeople() {
        deliveryPeople.values().stream().sorted(Comparator.comparing(DeliveryPerson::totalMoneyEarned).thenComparing(DeliveryPerson::getId).reversed()).forEach(System.out::println);
    }
}

public class DeliveryAppTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String appName = sc.nextLine();
        DeliveryApp app = new DeliveryApp(appName);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("addUser")) {
                String id = parts[1];
                String name = parts[2];
                app.addUser(id, name);
            } else if (parts[0].equals("registerDeliveryPerson")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addRestaurant")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addRestaurant(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addAddress")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addAddress(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("orderFood")) {
                String userId = parts[1];
                String userAddressName = parts[2];
                String restaurantId = parts[3];
                float cost = Float.parseFloat(parts[4]);
                app.orderFood(userId, userAddressName, restaurantId, cost);
            } else if (parts[0].equals("printUsers")) {
                app.printUsers();
            } else if (parts[0].equals("printRestaurants")) {
                app.printRestaurants();
            } else {
                app.printDeliveryPeople();
            }

        }
    }
}

