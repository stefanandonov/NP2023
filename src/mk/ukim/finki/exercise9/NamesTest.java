package mk.ukim.finki.exercise9;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


class Names {

    Map<String, Integer> boys;
    Map<String, Integer> girls;

    public Names() {
    }

    private Map<String, Integer> readNamesCollectors (InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.toMap(
                line -> line.split("\\s+")[0],
                line -> Integer.parseInt(line.split("\\s+")[1]),
                Integer::sum,
                TreeMap::new
        ));
    }

    private Map<String, Integer> readNames(InputStream is) {
        Scanner sc = new Scanner(is);
        Map<String, Integer> result = new HashMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String name = parts[0];
            int frequency = Integer.parseInt(parts[1]);
            result.put(name, frequency);
        }

        return result;
    }


    void readGirlNames(InputStream is) {
        girls = readNames(is);
    }

    void readBoyNames(InputStream is) {
        boys = readNames(is);
    }

    void unisexNames () {
        Set<String> allNames = new HashSet<>();
        Map<String,Integer> unisex = new HashMap<>();

        allNames.addAll(boys.keySet());
        allNames.addAll(girls.keySet());

        allNames.stream()
                .filter(name -> boys.containsKey(name) && girls.containsKey(name))
                .forEach(name -> unisex.put(name, boys.get(name)+girls.get(name)));
//                .sorted(Comparator.comparing(name -> boys.get(name)+girls.get(name)).reversed())
//                .forEach(name -> System.out.println(String.format("%s: %d", name, boys.get(name)+girls.get(name))));

        unisex.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s: %d", entry.getKey(), entry.getValue())));

    }
}

public class NamesTest {


    public static void main(String[] args) {

        Names names = new Names();

        try {
            names.readBoyNames(new FileInputStream("src/mk/ukim/finki/exercise9/data/boynames.txt"));
            names.readGirlNames(new FileInputStream("src/mk/ukim/finki/exercise9/data/girlnames.txt"));
            names.unisexNames();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Integer,String> freq = new HashMap<>();
        freq.put(20, "Stefan");
        freq.put(20, "Ana");

        System.out.println(freq);


    }
}
