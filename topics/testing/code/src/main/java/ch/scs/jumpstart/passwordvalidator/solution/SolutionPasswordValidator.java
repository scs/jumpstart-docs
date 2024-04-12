package ch.scs.jumpstart.passwordvalidator.solution;

import java.util.List;

public class SolutionPasswordValidator {
  private final List<SolutionStringValidator> validators;

  public SolutionPasswordValidator(List<SolutionStringValidator> validators) {
    this.validators = validators;
  }

  public List<String> validate(String password) {
    return validators.stream().flatMap(validator -> validator.validate(password).stream()).toList();
  }
}
