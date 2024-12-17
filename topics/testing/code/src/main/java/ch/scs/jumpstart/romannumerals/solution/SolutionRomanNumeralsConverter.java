package ch.scs.jumpstart.romannumerals.solution;

import java.util.ArrayList;

public class SolutionRomanNumeralsConverter {
  private enum RomanNumeralValues {
    _1000(1000, "M"),
    __900(900, "CM"),
    __500(500, "D"),
    __400(400, "CD"),
    __100(100, "C"),
    ___90(90, "XC"),
    ___50(50, "L"),
    ___40(40, "XL"),
    ___10(10, "X"),
    ____9(9, "IX"),
    ____5(5, "V"),
    ____4(4, "IV"),
    ____1(1, "I");

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
