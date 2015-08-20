/**
 * 
 */
package ru.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Shatskikh (Ilya.Shackih@ocrv.ru)
 *
 */
public class Main {

    public static void main(String[] args) {

        final int parCount = 2;
        String fileType = ".txt";

        if (args.length != parCount) {
            System.out.println("Please, use two parameters: 1) input text file; 2) output text file.");
        }

        if (!args[0].endsWith(fileType) || !args[1].endsWith(fileType)) {
            System.out.println("Use only txt file format");
        }

        String inputFile = args[0];

        List<Integer[]> rect = new ArrayList<Integer[]>();

        try (BufferedReader out = new BufferedReader(new FileReader(inputFile))) {
            String line = null;
            int lineCount = 0;
            while ((line = out.readLine()) != null) {

                lineCount += 1;
                if (lineCount > 100) {
                    throw new IllegalArgumentException("The number of lines should be no more than 100");
                }

                String[] strCoordinates = line.split("\\s+");

                if (strCoordinates.length > 4) {
                    throw new IllegalArgumentException("Use only 4 numbers");
                }

                int x1 = Integer.parseInt(strCoordinates[0]);
                int y1 = Integer.parseInt(strCoordinates[1]);
                int x2 = Integer.parseInt(strCoordinates[2]);
                int y2 = Integer.parseInt(strCoordinates[3]);

                if (Math.abs(x1) > 10000 || Math.abs(y1) > 10000 || Math.abs(x2) > 10000 || Math.abs(y2) > 10000) {
                    throw new IllegalArgumentException("Numbers must be not greater than 10000 by absolute value");
                }

                if (x1 > x2) {
                    x1 = x1 + x2;
                    x2 = x1 - x2;
                    x1 = x1 - x2;
                }

                if (y1 > y2) {
                    y1 = y1 + y2;
                    y2 = y1 - y2;
                    y1 = y1 - y2;
                }

            }

        } catch (Exception e) {

        }
    }
}
