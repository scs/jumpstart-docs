package ch.scs.jumpstart.bankkata.solution;

import java.util.ArrayList;
import java.util.List;

public class SolutionAccount {
  private final SolutionClock clock;
  private final SolutionConsoleAccountPrinter consoleAccountPrinter;
  private final List<SolutionStatement> statementList = new ArrayList<>();

  public SolutionAccount(SolutionClock clock, SolutionConsoleAccountPrinter consoleAccountPrinter) {
    this.clock = clock;
    this.consoleAccountPrinter = consoleAccountPrinter;
  }

  public void deposit(int amount) {
    statementList.add(SolutionStatement.of(amount, clock.getLocalDate()));
  }

  public void withdraw(int amount) {
    statementList.add(SolutionStatement.of(-amount, clock.getLocalDate()));
  }

  public void print() {
    int balance = 0;
    var statementLines = new ArrayList<SolutionStatementLine>();
    for (SolutionStatement statement : statementList) {
      balance += statement.amount();
      statementLines.add(SolutionStatementLine.of(statement.amount(), statement.date(), balance));
    }
    consoleAccountPrinter.print(statementLines);
  }
}
