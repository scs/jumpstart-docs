package ch.scs.jumpstart.bankkata.exercise;

import java.util.List;

public class ConsoleAccountPrinter {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Console console;

  public ConsoleAccountPrinter(Console console) {
    this.console = console;
  }

  @SuppressWarnings("PMD")
  public void print(List<StatementLine> statementLines) {}
}
