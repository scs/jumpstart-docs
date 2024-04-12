package ch.scs.jumpstart.passwordvalidator.solution.validators;

import ch.scs.jumpstart.passwordvalidator.solution.SolutionStringValidator;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SolutionContainsSpecialCharacter implements SolutionStringValidator {

  public List<String> validate(String string) {
    boolean validationFailed =
        Arrays.stream(string.split(""))
            .filter(StringUtils::isNotEmpty)
            .anyMatch(character -> !Character.isLetterOrDigit(character.charAt(0)));
    return !validationFailed ? List.of("must contain special character.") : List.of();
  }
}
