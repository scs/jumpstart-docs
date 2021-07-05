package ch.scs.jumpstart.bankkata.solution;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionConsoleAccountPrinterTest {

  private static final LocalDate D_2021_06_15 = LocalDate.parse("2021-06-15");
  private static final LocalDate D_2021_06_16 = LocalDate.parse("2021-06-16");
  private SolutionConsole console;
  private SolutionConsoleAccountPrinter consoleAccountPrinter;

  @BeforeEach
  public void setup() {
    console = mock(SolutionConsole.class);
    consoleAccountPrinter = new SolutionConsoleAccountPrinter(console);
  }

  @Test
  public void print_header_if_no_statement_line() {
    consoleAccountPrinter.print(Collections.emptyList());

    verify(console).printLine("Date || Amount || Balance");
  }

  @Test
  public void print_statements_in_descending_date_order() {
    consoleAccountPrinter.print(
        List.of(
            SolutionStatementLine.of(1, D_2021_06_15, 1),
            SolutionStatementLine.of(20, D_2021_06_16, 20)));

    var inOrder = inOrder(console);
    inOrder.verify(console).printLine("Date || Amount || Balance");
    inOrder.verify(console).printLine(D_2021_06_16 + " || 20 || 20");
    inOrder.verify(console).printLine(D_2021_06_15 + " || 1 || 1");
  }
}
