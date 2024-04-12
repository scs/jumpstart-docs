package ch.scs.jumpstart.passwordvalidator.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PasswordValidatorTest {
  @ParameterizedTest
  @CsvSource(
      value = {"'';false"},
      delimiter = ';')
  void validates_password(String password, boolean expected) {
    assertThat(new PasswordValidator().validate(password)).isEqualTo(expected);
  }
}
