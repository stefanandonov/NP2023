package mk.ukim.finki.exercise6.tuesday;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

abstract class Bill {

    String id;
    int minutes;
    int sms;
    double data;

    public Bill(String id, int minutes, int sms, double data) throws InvalidIdException {
        isValidBillId(id);
        this.id = id;
        this.minutes = minutes;
        this.sms = sms;
        this.data = data;
    }

    private void isValidBillId(String id) throws InvalidIdException {
        if (id.length() != 7) {
            throw new InvalidIdException(String.format("%s is not a valid user ID", id));
        }

        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidIdException(String.format("%s is not a valid user ID", id));
            }
        }
    }

    abstract double price();

    abstract double commission();
}

class MPacketBill extends Bill {


    public MPacketBill(String id, int minutes, int sms, double data) throws InvalidIdException {
        super(id, minutes, sms, data);
    }

    @Override
    public String toString() {
        return "MPacketBill{" +
                "id='" + id + '\'' +
                ", minutes=" + minutes +
                ", sms=" + sms +
                ", data=" + data +
                '}';
    }

    @Override
    double price() {
        double result = 750;
        if (minutes > 150) {
            result += (minutes - 150) * 4;
        }
        if (sms > 60) {
            result += (sms - 60) * 4;
        }
        if (data > 10.0) {
            result += (data - 10.0) * 20;
        }
        return result;
    }

    @Override
    double commission() {
        return price()*0.04;
    }
}

class SPacketBill extends Bill {


    public SPacketBill(String id, int minutes, int sms, double data) throws InvalidIdException {
        super(id, minutes, sms, data);
    }

    @Override
    double price() {
        double result = 500;
        if (minutes > 100) {
            result += (minutes - 100) * 5;
        }
        if (sms > 50) {
            result += (sms - 50) * 6;
        }
        if (data > 5.0) {
            result += (data - 5.0) * 25;
        }
        return result;
    }

    @Override
    double commission() {
        return price() * 0.07;
    }

    @Override
    public String toString() {
        return "SPacketBill{" +
                "id='" + id + '\'' +
                ", minutes=" + minutes +
                ", sms=" + sms +
                ", data=" + data +
                '}';
    }
}

class InvalidIdException extends Exception {
    public InvalidIdException(String message) {
        super(message);
    }
}

class SalesRep implements Comparable<SalesRep> {
    //475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
    String id;
    List<Bill> bills;

    public SalesRep(String id, List<Bill> bills) {
        this.id = id;
        this.bills = bills;
    }

    private static void isValidBillId(String id) throws InvalidIdException {
        if (id.length() != 3) {
            throw new InvalidIdException(String.format("%s is not a valid sales rep ID", id));
        }

        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidIdException(String.format("%s is not a valid user ID", id));
            }
        }
    }

    public static SalesRep create(String input) throws InvalidIdException {
        //0   1       2 3   4  5     6       7 8   9  10    11     12 13  14  15
        //475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
        String[] parts = input.split("\\s+");
        String salesRepId = parts[0];

        isValidBillId(salesRepId);

        List<Bill> bills = new ArrayList<>();

        for (int i = 1; i < parts.length; i += 5) {
            String customerId = parts[i];
            String type = parts[i + 1];
            int minutes = Integer.parseInt(parts[i + 2]);
            int sms = Integer.parseInt(parts[i + 3]);
            double data = Double.parseDouble(parts[i + 4]);

            try {
                if (type.equals("M")) {
                    bills.add(new MPacketBill(customerId, minutes, sms, data));
                } else { //S
                    bills.add(new SPacketBill(customerId, minutes, sms, data));
                }
            } catch (InvalidIdException e) {
                System.out.println(e.getMessage());
            }


        }

        return new SalesRep(salesRepId, bills);

    }

    public double commission ()
    {
        return bills.stream().mapToDouble(bill -> bill.commission()).sum();
    }
    @Override
    public String toString() {
        DoubleSummaryStatistics dss = bills.stream().mapToDouble(bill -> bill.price()).summaryStatistics();
        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f", id, bills.size(), dss.getMin(), dss.getAverage(), dss.getMax(), commission());
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.commission(), o.commission());
    }
}

class MobileOperator {

    List<SalesRep> salesReps;

    public void readSalesRepData(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        salesReps = br.lines()
                .map(line -> {
                    try {
                        return SalesRep.create(line);
                    } catch (InvalidIdException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void printSalesReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        salesReps.stream().sorted(Comparator.reverseOrder()).forEach(salesRep -> pw.println(salesRep));

//        pw.println(salesReps);
        pw.flush();
    }
}

public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}
