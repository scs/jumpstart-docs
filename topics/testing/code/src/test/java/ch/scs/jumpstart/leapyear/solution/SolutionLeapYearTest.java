package ch.scs.jumpstart.leapyear.solution;

import static ch.scs.jumpstart.leapyear.solution.SolutionLeapYear.isLeapYear;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SolutionLeapYearTest {
  @Test
  void is_a_leap_year_when_divisible_by_4() {
    assertThat(isLeapYear(1)).isFalse();
    assertThat(isLeapYear(4)).isTrue();
    assertThat(isLeapYear(2020)).isTrue();
  }

  @Test
  void is_not_a_leap_year_when_divisible_by_100() {
    assertThat(isLeapYear(1900)).isFalse();
    assertThat(isLeapYear(2100)).isFalse();
  }

  @Test
  void is_a_leap_year_if_divisible_by_400() {
    assertThat(isLeapYear(2000)).isTrue();
  }
}
