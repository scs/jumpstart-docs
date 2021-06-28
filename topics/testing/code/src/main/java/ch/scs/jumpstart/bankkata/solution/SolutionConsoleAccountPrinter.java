package ch.scs.jumpstart.bankkata.solution;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SolutionConsoleAccountPrinter {
  private final SolutionConsole console;

  public SolutionConsoleAccountPrinter(SolutionConsole console) {
    this.console = console;
  }

  public void print(List<SolutionStatementLine> statementLines) {
    console.printLine("Date       || Amount || Balance");
    statementLines.stream()
        .sorted(Comparator.comparing(SolutionStatementLine::getDate).reversed())
        .forEach(statementLine -> console.printLine(createLine(statementLine)));
  }

  private String createLine(SolutionStatementLine statementLine) {
    return String.join(
        " ||",
        List.of(
            formatDate(statementLine.getDate()),
            formatAmount(statementLine.getAmount(), 7),
            formatAmount(statementLine.getBalance(), 8)));
  }

  private String formatAmount(int amount, int padLeft) {
    return StringUtils.leftPad(String.valueOf(amount), padLeft);
  }

  private String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
