package ch.scs.jumpstart.romannumerals.solution;

import static ch.scs.jumpstart.romannumerals.solution.SolutionRomanNumeralsConverter.convert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolutionParametrizedRomanNumeralsConverterTest {

  @ParameterizedTest
  @CsvSource({
    "1,I",
    "2,II",
    "3,III",
    "5,V",
    "10,X",
    "50,L",
    "100,C",
    "500,D",
    "1000,M",
    "2687,MMDCLXXXVII",
    "4,IV",
    "9,IX",
    "40,XL",
    "90,XC",
    "400,CD",
    "900,CM",
    "949,CMXLIX",
    "3999,MMMCMXCIX"
  })
  public void return_roman_number_for_arabic_number(int arabic, String roman) {
    assertThat(convert(arabic), is(roman));
  }
}
