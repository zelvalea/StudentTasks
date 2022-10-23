package zelvalea.tasks.lab7;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixSumDefSerial {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) throw new IllegalArgumentException();
        String input1 = args[0], input2 = args[1], output = args[2];

        try (
             ObjectInputStream oi1 = new ObjectInputStream(new FileInputStream(input1));
             ObjectInputStream oi2 = new ObjectInputStream(new FileInputStream(input2));
             ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(output))
        ) {
            double[][] matrix1 = (double[][]) oi1.readObject();
            double[][] matrix2 = (double[][]) oi2.readObject();

            int minX = Math.min(matrix1.length, matrix2.length);
            int minY = Math.min(matrix1[0].length, matrix2[0].length);

            double[][] dataOutput = new double[minX][minY];
            for (int x = 0; x < minX; ++x) {
                for (int y = 0; y < minY; ++y) {
                    dataOutput[x][y] = matrix1[x][y] + matrix2[x][y];
                }
            }
            oo.writeObject(dataOutput);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static double[][] readMatrix(Stream<String> lines) {
        return lines
                .map(x -> {
                    String[] strings = x.split(" ");
                    int n = strings.length;
                    double[] data = new double[n];
                    for (int i = 0; i < n; ++i) {
                        data[i] = Double.parseDouble(strings[i]);
                    }
                    return data;
                }).toArray(double[][]::new);
    }
}
