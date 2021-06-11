package ch.scs.jumpstart.leapyear.solution;

import static ch.scs.jumpstart.leapyear.solution.SolutionLeapYear.isLeapYear;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class SolutionLeapYearTest {
  @Test
  public void is_a_leap_year_when_divisible_by_4() {
    assertThat(isLeapYear(1), is(false));
    assertThat(isLeapYear(4), is(true));
    assertThat(isLeapYear(2020), is(true));
  }

  @Test
  public void is_not_a_leap_year_when_divisible_by_100() {
    assertThat(isLeapYear(1900), is(false));
    assertThat(isLeapYear(2100), is(false));
  }

  @Test
  public void is_a_leap_year_if_divisible_by_400() {
    assertThat(isLeapYear(2000), is(true));
  }
}
