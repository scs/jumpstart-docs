package ch.scs.jumpstart.bankkata.solution;

import java.time.LocalDate;
import java.util.Objects;

public class SolutionStatement {
  private final int amount;
  private final LocalDate date;

  public static SolutionStatement of(int amount, LocalDate date) {
    return new SolutionStatement(amount, date);
  }

  private SolutionStatement(int amount, LocalDate date) {

    this.amount = amount;
    this.date = date;
  }

  public int getAmount() {
    return amount;
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SolutionStatement statement = (SolutionStatement) o;
    return amount == statement.amount && date.equals(statement.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, date);
  }
}
