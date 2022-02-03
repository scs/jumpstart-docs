package ch.scs.jumpstart.bankkata.exercise;

import java.util.ArrayList;
import java.util.List;

public class Account {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Clock clock;

  @SuppressWarnings("PMD.UnusedPrivateField")
  private final ConsoleAccountPrinter consoleAccountPrinter;

  @SuppressWarnings("PMD.UnusedPrivateField")
  private final List<Statement> statementList = new ArrayList<>();

  public Account(Clock clock, ConsoleAccountPrinter consoleAccountPrinter) {
    this.clock = clock;
    this.consoleAccountPrinter = consoleAccountPrinter;
  }

  @SuppressWarnings("PMD")
  public void deposit(int statement) {}

  @SuppressWarnings("PMD")
  public void withdraw(int statement) {}

  public void print() {}
}
