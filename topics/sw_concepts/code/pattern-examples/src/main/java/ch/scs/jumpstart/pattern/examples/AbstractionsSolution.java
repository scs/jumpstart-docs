package ch.scs.jumpstart.pattern.examples;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class AbstractionsSolution {
  private static final int INPUT_MAX_TSL2550 = 544;
  private static final double OUTPUT_MIN_FACTOR = 0.2;
  private static final double OUTPUT_CHANGE_MAX_FACTOR = 0.005;

  @SuppressWarnings({"PMD.AvoidPrintStackTrace"})
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

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      var brightnessControl = createBrightnessControl(reader, opt3001, inputPath);

      while (true) {
        try {
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(String.valueOf(brightnessControl.update()));
          }
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

  private static BrightnessControl createBrightnessControl(
      BufferedReader reader, boolean opt3001, String inputPath) throws IOException {
    int outputMax = Integer.parseInt(reader.readLine().trim());
    int outputMin = (int) Math.ceil(outputMax * OUTPUT_MIN_FACTOR);
    int outputChangeMax = (int) Math.ceil(outputMax * OUTPUT_CHANGE_MAX_FACTOR);

    return new BrightnessControl(
        opt3001, INPUT_MAX_TSL2550, inputPath, outputMin, outputMax, outputChangeMax);
  }
}

class BrightnessControl {
  private static final int INPUT_MIN = 0;
  private static final int INPUT_THRESHOLD = 11;

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

  private final boolean opt3001;
  private final int inputMax;
  private final String inputPath;

  private final int outputMax;
  private final int outputMin;
  private final int outputChangeMax;

  private int inputLastValue;
  private Integer outputLastValue = null;

  public BrightnessControl(
      boolean opt3001,
      int inputMax,
      String inputPath,
      int outputMin,
      int outputMax,
      int outputChangeMax) {
    this.opt3001 = opt3001;
    this.inputMax = inputMax;
    this.inputPath = inputPath;
    this.outputMax = outputMax;
    this.outputMin = outputMin;
    this.outputChangeMax = outputChangeMax;

    this.inputLastValue = INPUT_MIN - INPUT_THRESHOLD;
  }

  int update() throws IOException {
    var inputValue = getInputValue();
    var outputValue = calculateOutput(inputValue);
    var dimmedValue = getDimmedValue(outputValue);
    logValues(inputValue, outputValue, dimmedValue);
    inputLastValue = inputValue;
    outputLastValue = dimmedValue;
    return dimmedValue;
  }

  private int getInputValue() throws IOException {
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
    return inputValue;
  }

  private int calculateOutput(int inputValue) {
    double a = (inputValue - INPUT_MIN) / (double) (inputMax - INPUT_MIN);
    int value1 = (int) (a * (outputMax - outputMin) + outputMin);
    return Math.min(value1, outputMax);
  }

  private int getDimmedValue(int outputValue) {
    if (outputLastValue == null) {
      return outputValue;
    }
    if (outputValue >= outputLastValue) {
      return Math.min(outputValue, outputLastValue + outputChangeMax);
    } else {
      return Math.max(outputValue, outputLastValue - outputChangeMax);
    }
  }

  @SuppressWarnings({"PMD.SystemPrintln"})
  private void logValues(int inputValue, int outputValue, int dimmedValue) {
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
}
