package ch.scs.jumpstart.passwordvalidator.solution.validators;

import ch.scs.jumpstart.passwordvalidator.solution.SolutionStringValidator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SolutionLowerAndUpperCaseValidator implements SolutionStringValidator {

  @Override
  public List<String> validate(String string) {
    boolean validationFailed = !StringUtils.isMixedCase(string);
    return validationFailed
        ? List.of("must contain a lowercase and a uppercase character.")
        : List.of();
  }
}
