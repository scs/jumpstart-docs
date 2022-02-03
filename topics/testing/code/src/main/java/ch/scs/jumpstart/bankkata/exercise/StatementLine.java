package ch.scs.jumpstart.bankkata.exercise;

import java.time.LocalDate;
import java.util.Objects;

public final class StatementLine {
  private final int amount;
  private final LocalDate date;
  private final int balance;

  public static StatementLine of(int amount, LocalDate date, int balance) {
    return new StatementLine(amount, date, balance);
  }

  private StatementLine(int amount, LocalDate date, int balance) {

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
    StatementLine that = (StatementLine) o;
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
