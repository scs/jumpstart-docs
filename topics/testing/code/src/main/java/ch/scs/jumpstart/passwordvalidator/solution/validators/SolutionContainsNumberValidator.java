package ch.scs.jumpstart.passwordvalidator.solution.validators;

import ch.scs.jumpstart.passwordvalidator.solution.SolutionStringValidator;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SolutionContainsNumberValidator implements SolutionStringValidator {

  public List<String> validate(String string) {
    boolean validationFailed = Arrays.stream(string.split("")).noneMatch(StringUtils::isNumeric);
    return validationFailed ? List.of("must contain a number.") : List.of();
  }
}
