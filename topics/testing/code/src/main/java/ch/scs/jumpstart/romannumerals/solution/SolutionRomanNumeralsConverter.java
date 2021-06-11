package ch.scs.jumpstart.romannumerals.solution;

import java.util.ArrayList;

public class SolutionRomanNumeralsConverter {
  private enum RomanNumeralValues {
    THOUSAND(1000, "M"),
    NINE_HUNDRED(900, "CM"),
    FIVE_HUNDRED(500, "D"),
    FOUR_HUNDRED(400, "CD"),
    HUNDRED(100, "C"),
    NINETY(90, "XC"),
    FIFTY(50, "L"),
    FORTY(40, "XL"),
    TEN(10, "X"),
    NINE(9, "IX"),
    FIVE(5, "V"),
    FOUR(4, "IV"),
    ONE(1, "I");

    private final int arabicValue;
    private final String romanCypher;

    RomanNumeralValues(int base10Value, String romanCypher) {
      this.arabicValue = base10Value;
      this.romanCypher = romanCypher;
    }

    public String getRomanCypher() {
      return romanCypher;
    }

    public int getArabicValue() {
      return arabicValue;
    }
  }

  public static String convert(int arabic) {
    if (arabic == 0) {
      return null;
    }
    var result = new ArrayList<String>();
    for (RomanNumeralValues romanNumeralValue : RomanNumeralValues.values()) {
      while (arabic >= romanNumeralValue.getArabicValue()) {
        arabic -= romanNumeralValue.getArabicValue();
        result.add(romanNumeralValue.getRomanCypher());
      }
    }
    return String.join("", result);
  }
}
