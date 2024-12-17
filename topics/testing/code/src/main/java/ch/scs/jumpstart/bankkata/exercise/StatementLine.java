package ch.scs.jumpstart.bankkata.exercise;

import java.time.LocalDate;

public record StatementLine(int amount, LocalDate date, int balance) {

  public static StatementLine of(int amount, LocalDate date, int balance) {
    return new StatementLine(amount, date, balance);
  }
}
