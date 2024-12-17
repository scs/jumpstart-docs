package ch.scs.jumpstart.passwordvalidator.solution;

import static org.assertj.core.api.Assertions.assertThat;

import ch.scs.jumpstart.passwordvalidator.solution.validators.SolutionContainsNumberValidator;
import ch.scs.jumpstart.passwordvalidator.solution.validators.SolutionContainsSpecialCharacter;
import ch.scs.jumpstart.passwordvalidator.solution.validators.SolutionLengthValidator;
import ch.scs.jumpstart.passwordvalidator.solution.validators.SolutionLowerAndUpperCaseValidator;
import ch.scs.jumpstart.passwordvalidator.solution.validators.SolutionNumbersMustAddTo25Validator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolutionPasswordValidatorTest {

  private SolutionPasswordValidator passwordValidator;

  @BeforeEach
  void setup() {
    passwordValidator =
        new SolutionPasswordValidator(
            List.of(
                new SolutionLengthValidator(),
                new SolutionContainsNumberValidator(),
                new SolutionLowerAndUpperCaseValidator(),
                new SolutionContainsSpecialCharacter(),
                new SolutionNumbersMustAddTo25Validator()));
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        "passwordpas;must have at least 12 characters.,must contain a number.,must contain a lowercase and a uppercase character.,must contain special character.,digits must sum up to 25.",
        "passwordpa🌖swordPasswordpaswordpassword55555;''"
      },
      delimiter = ';')
  void validates_password(String password, String expectedMessages) {
    var expected = Arrays.stream(expectedMessages.split(",")).toList();
    if (expectedMessages.isBlank()) {
      expected = List.of();
    }
    assertThat(passwordValidator.validate(password)).isEqualTo(expected);
  }
}
