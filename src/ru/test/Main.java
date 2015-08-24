package ru.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Square of the figure formed by the union of the rectangles.
 * The algorithm - Okulov "Programming in algorithms".
 * @author Ilya Shatskikh (Ilya.Shackih@ocrv.ru)
 *
 */
public class Main {

    private final static String FILE_TYPE = ".txt";
    private final static int ARGS_COUNT = 2;
    private final static int LINE_MAX = 100;
    private final static int ABSOLUTE_MAX = 10000;
    private final static int COORDINATE_COUNT = 4;
    private final static int X1 = 0;
    private final static int Y1 = 1;
    private final static int X2 = 2;
    private final static int Y2 = 3;

    public static void main(String[] args) {

        if (args.length != ARGS_COUNT) {
            System.out.println("Please, use two parameters: 1) input text file; 2) output text file.");
            System.exit(1);
        }

        if (!args[0].endsWith(FILE_TYPE) || !args[1].endsWith(FILE_TYPE)) {
            System.out.println("Use only txt file format");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        List<Integer[]> rectangleList = new LinkedList<Integer[]>();
        SortedSet<Integer> abscissaSet = new TreeSet<Integer>();
        SortedSet<Integer> ordinateSet = new TreeSet<Integer>();

        try (BufferedReader out = new BufferedReader(new FileReader(inputFile))) {
            String line = null;
            int lineCount = 0;
            while ((line = out.readLine()) != null) {
                lineCount += 1;
                if (lineCount > LINE_MAX) {
                    throw new IllegalArgumentException("The number of lines should be no more than 100");
                }

                String[] strCoordinates = line.split("\\s+");

                if (strCoordinates.length > COORDINATE_COUNT) {
                    throw new IllegalArgumentException("Use only 4 numbers");
                }

                Integer[] rectangle = new Integer[COORDINATE_COUNT];
                rectangle[X1] = Integer.parseInt(strCoordinates[X1]);
                rectangle[Y1] = Integer.parseInt(strCoordinates[Y1]);
                rectangle[X2] = Integer.parseInt(strCoordinates[X2]);
                rectangle[Y2] = Integer.parseInt(strCoordinates[Y2]);

                if (Math.abs(rectangle[X1]) > ABSOLUTE_MAX || Math.abs(rectangle[Y1]) > ABSOLUTE_MAX
                                || Math.abs(rectangle[X2]) > ABSOLUTE_MAX || Math.abs(rectangle[Y2]) > ABSOLUTE_MAX) {
                    throw new IllegalArgumentException("Numbers must be not greater than 10000 by absolute value");
                }

                if (rectangle[X1] > rectangle[X2]) {
                    swap(rectangle, X1, X2);
                }

                if (rectangle[Y1] > rectangle[Y2]) {
                    swap(rectangle, Y1, Y2);
                }

                abscissaSet.add(rectangle[X1]);
                abscissaSet.add(rectangle[X2]);
                ordinateSet.add(rectangle[Y1]);
                ordinateSet.add(rectangle[Y2]);
                rectangleList.add(rectangle);
            }

        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Integer totalArea = 0;

        for (int i = abscissaSet.first(); i < abscissaSet.last(); i++) {
            for (int j = ordinateSet.first(); j < ordinateSet.last(); j++) {
                Integer[] curRect = new Integer[COORDINATE_COUNT];
                curRect[X1] = i;
                curRect[Y1] = j;
                curRect[X2] = i + 1;
                curRect[Y2] = j + 1;
                for (Integer[] rect: rectangleList) {
                    if (isInclude(rect, curRect)) {
                        totalArea += square(curRect);
                        break;
                    }
                }
            }
        }

        try (FileWriter fw = new FileWriter(new File(outputFile))) {
            fw.write(totalArea.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static int square(Integer[] value) {

        return (value[X2] - value[X1]) * (value[Y2] - value[Y1]);
    }

    private static void swap(Integer[] value, int i, int j) {

        Integer x = value[i];
        value[i] = value[j];
        value[j] = x;
    }

    private static boolean isInclude(Integer[] outer, Integer[] inner) {

        return isBeetween(inner[X1], outer[X1], outer[X2]) && isBeetween(inner[X2], outer[X1], outer[X2]) &&
                        isBeetween(inner[Y1], outer[Y1], outer[Y2]) && isBeetween(inner[Y2], outer[Y1], outer[Y2]);
    }

    private static boolean isBeetween(Integer value, Integer left, Integer right) {

        return left <= value && value <= right;
    }
}
