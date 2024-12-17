package ch.scs.jumpstart.passwordvalidator.solution.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolutionLowerAndUpperCaseValidatorTest {
  @ParameterizedTest
  @CsvSource({
    "a, false",
    "B, false",
    "cD, true",
    "Ef, true",
  })
  void validates_lower_and_upper_case_characters(String string, boolean expected) {
    assertThat(new SolutionLowerAndUpperCaseValidator().validate(string)).hasSize(expected ? 0 : 1);
  }
}
