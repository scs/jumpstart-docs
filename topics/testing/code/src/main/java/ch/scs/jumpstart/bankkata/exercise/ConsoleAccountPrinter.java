package ch.scs.jumpstart.bankkata.exercise;

import java.util.List;

public record ConsoleAccountPrinter(Console console) {
  @SuppressWarnings("PMD")
  public void print(List<StatementLine> statementLines) {}
}
