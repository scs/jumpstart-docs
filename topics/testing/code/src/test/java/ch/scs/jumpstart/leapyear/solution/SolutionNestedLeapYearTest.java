package ch.scs.jumpstart.leapyear.solution;

import static ch.scs.jumpstart.leapyear.solution.SolutionLeapYear.isLeapYear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("A year")
class SolutionNestedLeapYearTest {
  @Nested
  @DisplayName("is a leap year")
  class IsALeapYear {

    @ParameterizedTest
    @ValueSource(ints = {4, 2004, 2024})
    void if_divisible_by_4_but_not_by_100(int year) {
      assertThat(isLeapYear(year)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 2000, 2400})
    void if_divisible_by_400(int year) {
      assertThat(isLeapYear(year)).isTrue();
    }
  }

  @Nested
  @DisplayName("is not a leap year")
  class IsNotALeapYear {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 2001, 2002, 2003, 2005})
    void if_not_divisible_by_4(int year) {
      assertThat(isLeapYear(year)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 1900, 2100})
    void if_divisible_by_100_but_not_by_400(int year) {
      assertThat(isLeapYear(year)).isFalse();
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, Integer.MAX_VALUE})
  void greater_one_is_supported(int year) {
    //noinspection ResultOfMethodCallIgnored
    assertAll(() -> isLeapYear(year));
  }
}
