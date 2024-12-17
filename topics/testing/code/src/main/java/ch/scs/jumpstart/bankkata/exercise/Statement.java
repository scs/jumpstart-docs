package ch.scs.jumpstart.bankkata.exercise;

import java.time.LocalDate;

public record Statement(int amount, LocalDate date) {

  public static Statement of(int amount, LocalDate date) {
    return new Statement(amount, date);
  }
}
