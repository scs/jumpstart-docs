package ch.scs.jumpstart.bankkata.solution;

import java.time.LocalDate;

public record SolutionStatement(int amount, LocalDate date) {
  public static SolutionStatement of(int amount, LocalDate date) {
    return new SolutionStatement(amount, date);
  }
}
