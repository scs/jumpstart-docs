package ch.scs.jumpstart.romannumerals.solution;

import static ch.scs.jumpstart.romannumerals.solution.SolutionRomanNumeralsConverter.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

class SolutionRomanNumeralsConverterTest {

  @Test
  public void return_roman_number_for_arabic_number() {
    assertThat(convert(0), nullValue());
    assertThat(convert(1), is("I"));
    assertThat(convert(2), is("II"));
    assertThat(convert(3), is("III"));
    assertThat(convert(5), is("V"));
    assertThat(convert(10), is("X"));
    assertThat(convert(50), is("L"));
    assertThat(convert(100), is("C"));
    assertThat(convert(500), is("D"));
    assertThat(convert(1000), is("M"));
    assertThat(convert(2687), is("MMDCLXXXVII"));

    assertThat(convert(4), is("IV"));
    assertThat(convert(9), is("IX"));
    assertThat(convert(40), is("XL"));
    assertThat(convert(90), is("XC"));
    assertThat(convert(400), is("CD"));
    assertThat(convert(900), is("CM"));
    assertThat(convert(949), is("CMXLIX"));
    assertThat(convert(3999), is("MMMCMXCIX"));
  }
}
