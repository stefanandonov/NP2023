package mk.ukim.finki.exercise6.wednesday;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Square {
    int size;

    public Square(int size) {
        this.size = size;
    }

    public int perimeter() {
        return 4 * size;
    }
}

class Canvas implements Comparable<Canvas> {
    String id;
    List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }

//    public Canvas (String line) {
//
//    }

    public static Canvas create(String line) {
        //0        1  2  3  4  5
        //364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
        String[] parts = line.split("\\s+");
        String id = parts[0];

        List<Square> squares = new ArrayList<>();

//        for (int i=1;i<parts.length;i++){
//            squares.add(new Square(Integer.parseInt(parts[i])));
//        }

        squares = Arrays.stream(parts)
                .skip(1)
                .map(sizeStr -> new Square(Integer.parseInt(sizeStr)))
                .collect(Collectors.toList());

        return new Canvas(id, squares);

    }

    int totalSquares() {
        return squares.size();
    }

    int totalPerimeter() {
        return squares.stream()
                .mapToInt(square -> square.perimeter())
                .sum();
    }

    @Override
    public int compareTo(Canvas otherCanvas) {
        return Integer.compare(this.totalPerimeter(), otherCanvas.totalPerimeter());
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, totalSquares(), totalPerimeter());
    }
}

class ShapesApplication {

    List<Canvas> canvases;


    ShapesApplication() {
        canvases = new ArrayList<>();
    }

    public int readCanvases(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        canvases = br.lines()
                .map(line -> Canvas.create(line))
                .collect(Collectors.toList());

        br.close();

        return canvases.stream()
                .mapToInt(canvas -> canvas.totalSquares())
                .sum();


    }

    public void printLargestCanvasTo(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        Canvas max = canvases.stream()
                .max(Comparator.naturalOrder())
                .get();

        pw.println(max);

        pw.flush();

    }
}


public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        try {
            System.out.println(shapesApplication.readCanvases(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
