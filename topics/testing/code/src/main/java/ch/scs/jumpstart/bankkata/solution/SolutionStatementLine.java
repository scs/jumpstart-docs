package ch.scs.jumpstart.bankkata.solution;

import java.time.LocalDate;

public record SolutionStatementLine(int amount, LocalDate date, int balance) {

  public static SolutionStatementLine of(int amount, LocalDate date, int balance) {
    return new SolutionStatementLine(amount, date, balance);
  }
}
