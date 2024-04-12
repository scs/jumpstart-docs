package ch.scs.jumpstart.passwordvalidator.solution.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SolutionLengthValidatorTest {
  @ParameterizedTest
  @CsvSource({
    "'',false",
    "a, false",
    "passwordpas, false",
    "passwordpassword, true",
    "passwordpasswordpasswordpasswordpassword, true",
    "aklésdflkvhjklwrepjàaksljdféaKJRQAWEFDASFéLJK, true",
  })
  void must_have_at_least_12_characters(String string, boolean expected) {
    assertThat(new SolutionLengthValidator().validate(string)).hasSize(expected ? 0 : 1);
  }
}
