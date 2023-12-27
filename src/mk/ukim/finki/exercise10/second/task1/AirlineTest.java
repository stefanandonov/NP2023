package mk.ukim.finki.exercise10.second.task1;

import java.util.*;

class AirportNotFoundException extends Exception {
    public AirportNotFoundException(String airportCode) {
        super(String.format("Airport %s is not part of the company.", airportCode));
    }
}

class Airport implements Comparable<Airport> {
    private final String airportName;
    private final String airportCode;
    private final Map<String, Long> flights;

    public Airport(String airportName, String airportCode) {
        this.airportName = airportName;
        this.airportCode = airportCode;
        flights = new TreeMap<>();
    }

    public void addFlight(String flightCode, long duration) {
        flights.put(flightCode, duration);
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public int countFlights() {
        return flights.values().size();
    }

    public long getTotalFlightsDuration() {
        return flights.values().stream().mapToLong(i -> i).sum();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(airportName).append(": ").append(airportCode).append("\n");
        flights.forEach((key, value) -> stringBuilder.append(key).append(" : ").append(value).append(" min").append("\n"));
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Airport other) {
        return Comparator.comparing(Airport::countFlights).reversed()
                .thenComparing(Airport::getAirportCode)
                .thenComparing(Airport::getAirportName).compare(this, other);
    }
}

class Airline {
    private final String airlineName;
    private final Map<String, Airport> airports;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
        airports = new HashMap<>();
    }

    public void addAirport(String airportName, String code) {
        airports.put(code, new Airport(airportName, code));
    }

    public void addFlight(String flightCodeFrom, String flightCodeTo, long duration) {
        airports.get(flightCodeFrom).addFlight(flightCodeTo, duration);
    }

    public void search(String airportCode) throws AirportNotFoundException {
        if (!airports.containsKey(airportCode)) throw new AirportNotFoundException(airportCode);
        System.out.println(airports.get(airportCode));
    }

    public long allFlights() {
        return airports.values().stream().mapToLong(Airport::getTotalFlightsDuration).sum();
    }

    public void printAirports() {
        airports.values().stream().sorted().forEach(System.out::println);
    }
}

public class AirlineTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String name = scanner.nextLine();
        Airline airline = new Airline(name);

        int airportsNum = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < airportsNum; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            String airportName = parts[0];
            String code = (parts[1]);
            airline.addAirport(airportName, code);
        }

        int flightsNum = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < flightsNum; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            String code1 = parts[0];
            String code2 = parts[1];
            long duration = Long.parseLong(parts[2]);

            airline.addFlight(code1, code2, duration);
        }

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            String codeSearch = scanner.nextLine();
            System.out.println("SEARCH");
            try {
                airline.search(codeSearch);
            } catch (AirportNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 2) {
            System.out.println("ALL FLIGHTS");
            System.out.printf("%d\n", airline.allFlights());
        } else {
            System.out.println("ALL AIRPORTS");
            airline.printAirports();
        }
        scanner.close();
    }
}