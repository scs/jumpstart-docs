package ch.scs.jumpstart.leapyear.solution;

import static ch.scs.jumpstart.leapyear.solution.SolutionLeapYear.isLeapYear;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("A year")
class SolutionNestedLeapYearTest {
  @Nested
  @DisplayName("is a leap year")
  public class IsALeapYear {

    @ParameterizedTest
    @ValueSource(ints = {4, 2004, 2024})
    public void if_divisible_by_4_but_not_by_100(int year) {
      assertThat(isLeapYear(year), is(true));
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 2000, 2400})
    public void if_divisible_by_400(int year) {
      assertThat(isLeapYear(year), is(true));
    }
  }

  @Nested
  @DisplayName("is not a leap year")
  public class IsNotALeapYear {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 2001, 2002, 2003, 2005})
    public void if_not_divisible_by_4(int year) {
      assertThat(isLeapYear(year), is(false));
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 1900, 2100})
    public void if_divisible_by_100_but_not_by_400(int year) {
      assertThat(isLeapYear(year), is(false));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, Integer.MAX_VALUE})
  public void greater_one_is_supported(int year) {
    //noinspection ResultOfMethodCallIgnored
    assertAll(() -> isLeapYear(year));
  }
}
