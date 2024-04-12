package ch.scs.jumpstart.passwordvalidator.solution.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SolutionNumbersMustAddTo25Test {
  @ParameterizedTest
  @CsvSource({
    "'', false",
    "test, false",
    "55555, true",
    "asdfa5asdf5aqwvo i5aföö5faopsdifj5cöajhpiu, true",
    "997, true",
  })
  void numbers_must_add_up_to_25(String string, boolean expected) {
    assertThat(new SolutionNumbersMustAddTo25Validator().validate(string))
        .hasSize(expected ? 0 : 1);
  }
}
