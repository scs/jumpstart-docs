package ch.scs.jumpstart.bankkata.solution;

import java.util.Comparator;
import java.util.List;

public record SolutionConsoleAccountPrinter(SolutionConsole console) {

  public void print(List<SolutionStatementLine> statementLines) {
    console.printLine("Date || Amount || Balance");
    statementLines.stream()
        .sorted(Comparator.comparing(SolutionStatementLine::date).reversed())
        .forEach(statementLine -> console.printLine(createLine(statementLine)));
  }

  private String createLine(SolutionStatementLine statementLine) {
    return "%s || %s || %s"
        .formatted(statementLine.date(), statementLine.amount(), statementLine.balance());
  }
}
