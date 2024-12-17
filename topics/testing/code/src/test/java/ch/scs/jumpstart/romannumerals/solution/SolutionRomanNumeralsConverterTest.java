package ch.scs.jumpstart.romannumerals.solution;

import static ch.scs.jumpstart.romannumerals.solution.SolutionRomanNumeralsConverter.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SolutionRomanNumeralsConverterTest {

  @Test
  void return_roman_number_for_arabic_number() {
    assertThat(convert(0)).isNull();
    assertThat(convert(1)).isEqualTo("I");
    assertThat(convert(2)).isEqualTo("II");
    assertThat(convert(3)).isEqualTo("III");
    assertThat(convert(5)).isEqualTo("V");
    assertThat(convert(10)).isEqualTo("X");
    assertThat(convert(50)).isEqualTo("L");
    assertThat(convert(100)).isEqualTo("C");
    assertThat(convert(500)).isEqualTo("D");
    assertThat(convert(1000)).isEqualTo("M");
    assertThat(convert(2687)).isEqualTo("MMDCLXXXVII");

    assertThat(convert(4)).isEqualTo("IV");
    assertThat(convert(9)).isEqualTo("IX");
    assertThat(convert(40)).isEqualTo("XL");
    assertThat(convert(90)).isEqualTo("XC");
    assertThat(convert(400)).isEqualTo("CD");
    assertThat(convert(900)).isEqualTo("CM");
    assertThat(convert(949)).isEqualTo("CMXLIX");
    assertThat(convert(3999)).isEqualTo("MMMCMXCIX");
  }
}
