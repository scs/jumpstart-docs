package ch.scs.jumpstart.passwordvalidator.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

  @Nested
  class JavaHelp {
    @Test
    void string_length() {
      assertThat("password".length()).isEqualTo(8);
    }

    @Test
    void mixed_case() {
      assertThat(StringUtils.isMixedCase("aA")).isTrue();
    }

    @Test
    void is_numeric() {
      assertThat(StringUtils.isNumeric("1")).isTrue();
      assertThat(StringUtils.isNumeric("1A")).isFalse();
    }

    @Test
    void contains_special_char() {
      assertThat(containsSpecialChar("+")).isTrue();
    }

    @Test
    void concat_lists() {
      assertThat(
              Stream.of(List.of(1), List.of(2, 3, 4), List.of(5, 6))
                  .flatMap(Collection::stream)
                  .toList())
          .isEqualTo(List.of(1, 2, 3, 4, 5, 6));
    }

    private static Boolean containsSpecialChar(
        @SuppressWarnings("SameParameterValue") String input) {
      for (String s : input.split("")) {
        if (!Character.isLetterOrDigit(s.charAt(0))) {
          return true;
        }
      }
      return false;
    }
  }
}
