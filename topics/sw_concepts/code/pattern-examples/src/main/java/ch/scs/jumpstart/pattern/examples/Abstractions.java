package ch.scs.jumpstart.pattern.examples;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Abstractions {

  private static final int INPUT_MIN = 0;
  private static final int INPUT_MAX_TSL2550 = 544;
  private static final int INPUT_THRESHOLD = 11;

  private static final double OUTPUT_MIN_FACTOR = 0.2;
  private static final double OUTPUT_CHANGE_MAX_FACTOR = 0.005;

  private static final int[] SENSOR_VALUE_LUX_APPROX_MAP = {
    0, 1, 2, 3, 4, 5, 6, 7,
    8, 9, 10, 11, 12, 13, 14, 15,
    16, 18, 20, 22, 24, 26, 28, 30,
    32, 34, 36, 38, 40, 42, 44, 46,
    49, 53, 57, 61, 65, 69, 73, 77,
    81, 85, 89, 93, 97, 101, 105, 109,
    115, 123, 131, 139, 147, 155, 163, 171,
    179, 187, 195, 203, 211, 219, 227, 235,
    247, 263, 279, 295, 311, 327, 343, 359,
    375, 391, 407, 423, 439, 455, 471, 487,
    511, 543, 575, 607, 639, 671, 703, 735,
    767, 799, 831, 863, 895, 927, 959, 991,
    1039, 1103, 1167, 1231, 1295, 1359, 1423, 1487,
    1551, 1615, 1679, 1743, 1807, 1871, 1935, 1999,
    2095, 2223, 2351, 2479, 2607, 2735, 2863, 2991,
    3119, 3247, 3375, 3503, 3631, 3759, 3887, 4015
  };

  @SuppressWarnings({
    "PMD.CognitiveComplexity",
    "PMD.CyclomaticComplexity",
    "PMD.SystemPrintln",
    "PMD.AvoidPrintStackTrace"
  })
  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }

    String path = args[0];
    String inputPath = args[1];
    String outputPath = args[2];
    boolean opt3001 =
        Files.isSymbolicLink(Paths.get(inputPath))
            && Files.readSymbolicLink(Paths.get(inputPath))
                .toString()
                .contains("in_illuminance_input");

    int inputMax = INPUT_MAX_TSL2550;
    int inputLastValue = INPUT_MIN - INPUT_THRESHOLD;
    Integer outputLastValue = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      int outputMax = Integer.parseInt(reader.readLine().trim());
      int outputMin = (int) Math.ceil(outputMax * OUTPUT_MIN_FACTOR);
      int outputChangeMax = (int) Math.ceil(outputMax * OUTPUT_CHANGE_MAX_FACTOR);

      while (true) {
        try {
          int inputValue;
          if (opt3001) {
            try (BufferedReader inputReader = new BufferedReader(new FileReader(inputPath))) {
              inputValue = (int) Double.parseDouble(inputReader.readLine().trim());
            } catch (IOException e) {
              inputValue = INPUT_MIN;
            }
          } else {
            try (BufferedReader inputReader = new BufferedReader(new FileReader(inputPath))) {
              inputValue = Integer.parseInt(inputReader.readLine().trim());
            }
            if (0 <= inputValue && inputValue < SENSOR_VALUE_LUX_APPROX_MAP.length) {
              inputValue = SENSOR_VALUE_LUX_APPROX_MAP[inputValue];
            } else {
              inputValue = inputLastValue;
            }
          }

          inputValue = Math.min(inputValue, inputMax);

          if (Math.abs(inputValue - inputLastValue) < INPUT_THRESHOLD) {
            inputValue = inputLastValue;
          }

          double a = (inputValue - INPUT_MIN) / (double) (inputMax - INPUT_MIN);
          int value1 = (int) (a * (outputMax - outputMin) + outputMin);
          int outputValue = Math.min(value1, outputMax);

          if (outputLastValue == null) {
            outputValue = outputValue;
          } else if (outputValue >= outputLastValue) {
            outputValue = Math.min(outputValue, outputLastValue + outputChangeMax);
          } else {
            outputValue = Math.max(outputValue, outputLastValue - outputChangeMax);
          }
          int dimmedValue = outputValue;

          if (!Objects.equals(outputValue, outputLastValue)) {
            System.out.printf(
                "input: %4d (%4.1f%%), output: %4d (%4.1f%%), dimmed: %4d (%4.1f%%)%n",
                inputValue,
                100 * (inputValue - INPUT_MIN) / (double) (inputMax - INPUT_MIN),
                outputValue,
                100 * (outputValue - outputMin) / (double) (outputMax - outputMin),
                dimmedValue,
                100 * (dimmedValue - outputMin) / (double) (outputMax - outputMin));
            System.out.flush();
          }

          try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            outputLastValue = outputValue;
            writer.write(String.valueOf(outputValue));
          }

          inputLastValue = inputValue;
        } catch (IOException e) {
          if (!(e instanceof FileNotFoundException)) {
            throw e;
          }
        }

        TimeUnit.MILLISECONDS.sleep(10);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
