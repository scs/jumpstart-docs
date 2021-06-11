package ch.scs.jumpstart.bankkata.solution;

import java.time.LocalDate;
import java.util.Objects;

public final class SolutionStatementLine {
  private final int amount;
  private final LocalDate date;
  private final int balance;

  public static SolutionStatementLine of(int amount, LocalDate date, int balance) {
    return new SolutionStatementLine(amount, date, balance);
  }

  private SolutionStatementLine(int amount, LocalDate date, int balance) {

    this.amount = amount;
    this.date = date;
    this.balance = balance;
  }

  public int getAmount() {
    return amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getBalance() {
    return balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SolutionStatementLine that = (SolutionStatementLine) o;
    return amount == that.amount && balance == that.balance && date.equals(that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, date, balance);
  }

  @Override
  public String toString() {
    return "StatementLine{" + "amount=" + amount + ", date=" + date + ", balance=" + balance + '}';
  }
}
