package ch.scs.jumpstart.passwordvalidator.solution.validators;

import ch.scs.jumpstart.passwordvalidator.solution.SolutionStringValidator;
import java.util.List;

public class SolutionLengthValidator implements SolutionStringValidator {

  public List<String> validate(String string) {
    boolean validationFailed = string.length() < 12;
    return validationFailed ? List.of("must have at least 12 characters.") : List.of();
  }
}
