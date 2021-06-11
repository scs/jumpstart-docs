package ch.scs.jumpstart.romannumerals.solution;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class SolutionRomanNumeralsConverterTest {

  @Test
  public void return_roman_number_for_arabic_number() {
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(0), nullValue());
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(1), is("I"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(2), is("II"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(3), is("III"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(5), is("V"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(10), is("X"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(50), is("L"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(100), is("C"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(500), is("D"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(1000), is("M"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(2687), is("MMDCLXXXVII"));

    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(4), is("IV"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(9), is("IX"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(40), is("XL"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(90), is("XC"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(400), is("CD"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(900), is("CM"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(949), is("CMXLIX"));
    MatcherAssert.assertThat(SolutionRomanNumeralsConverter.convert(3999), is("MMMCMXCIX"));
  }
}
