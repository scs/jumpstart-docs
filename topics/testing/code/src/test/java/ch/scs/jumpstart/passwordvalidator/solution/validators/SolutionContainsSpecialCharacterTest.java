package ch.scs.jumpstart.passwordvalidator.solution.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolutionContainsSpecialCharacterTest {
  @ParameterizedTest
  @CsvSource({
    "'',false",
    "password, false",
    "123, false",
    "password123, false",
    "'*', true",
    "'password#', true",
    "'pass🌖ord', true",
  })
  void detects_special_characters(String string, boolean expected) {
    assertThat(new SolutionContainsSpecialCharacter().validate(string)).hasSize(expected ? 0 : 1);
  }
}
