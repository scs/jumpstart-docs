package ch.scs.jumpstart.passwordvalidator.solution.validators;

import ch.scs.jumpstart.passwordvalidator.solution.SolutionStringValidator;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SolutionNumbersMustAddTo25Validator implements SolutionStringValidator {

  @Override
  public List<String> validate(String string) {
    int sum =
        Arrays.stream(string.split(""))
            .filter(StringUtils::isNumeric)
            .mapToInt(Integer::parseInt)
            .sum();
    return sum != 25 ? List.of("digits must sum up to 25.") : List.of();
  }
}
