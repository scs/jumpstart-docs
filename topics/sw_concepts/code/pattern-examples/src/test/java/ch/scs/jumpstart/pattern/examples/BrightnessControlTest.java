package ch.scs.jumpstart.pattern.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BrightnessControlTest {
  public static final int INPUT_MAX = 4015;
  public static final int OUTPUT_MAX = 1000;
  public static final int OUTPUT_MIN = 0;
  public static final int OUTPUT_CHANGE_MAX = 10;

  @TempDir private Path tempDir;

  private Path input;

  @BeforeEach
  void setup() {
    input = tempDir.resolve("input");
  }

  @Test
  void climbs_to_target_value_in_steps() throws IOException {
    var brightnessControl =
        new BrightnessControl(
            false, INPUT_MAX, input.toString(), OUTPUT_MIN, OUTPUT_MAX, OUTPUT_CHANGE_MAX);
    Files.writeString(input, "0");
    brightnessControl.update();

    Files.writeString(input, "60");

    var outputValues = new ArrayList<>();
    for (int __ : IntStream.range(0, 10).toArray()) {
      int update = brightnessControl.update();
      outputValues.add(update);
    }

    assertThat(outputValues).isEqualTo(List.of(10, 20, 30, 40, 50, 52, 52, 52, 52, 52));
  }
}
