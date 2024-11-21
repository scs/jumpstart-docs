package ch.scs.jumpstart.passwordvalidator.solution.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolutionContainsNumberValidatorTest {
  @ParameterizedTest
  @CsvSource({
    "'',false",
    "passwordpassword,false",
    "1,true",
    "p9a,true",
    "8pa,true",
    "pa7,true",
    "password2password,true"
  })
  void must_contain_a_number(String string, boolean expected) {
    assertThat(new SolutionContainsNumberValidator().validate(string)).hasSize(expected ? 0 : 1);
  }
}
