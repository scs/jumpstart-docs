package ch.scs.jumpstart.bankkata.solution;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionAccountAcceptanceTest {
  private static final LocalDate DATE_1 = LocalDate.parse("2021-01-01");
  private static final LocalDate DATE_2 = LocalDate.parse("2021-06-12");
  private static final LocalDate DATE_3 = LocalDate.parse("2021-06-15");
  private static final LocalDate DATE_4 = LocalDate.parse("2021-06-16");

  private SolutionConsole console;
  private SolutionClock clock;
  private SolutionAccount account;

  @BeforeEach
  void setup() {
    console = mock(SolutionConsole.class);
    SolutionConsoleAccountPrinter consoleAccountPrinter =
        new SolutionConsoleAccountPrinter(console);
    clock = mock(SolutionClock.class);
    account = new SolutionAccount(clock, consoleAccountPrinter);
  }

  @Test
  void print_the_ledger_of_the_account() {
    when(clock.getLocalDate()).thenReturn(DATE_1, DATE_2, DATE_3, DATE_4);

    account.deposit(100);
    account.withdraw(75);
    account.deposit(1500);
    account.withdraw(250);
    account.print();

    var inOrder = inOrder(console);
    inOrder.verify(console).printLine("Date || Amount || Balance");
    inOrder.verify(console).printLine("%s || -250 || 1275".formatted(DATE_4));
    inOrder.verify(console).printLine("%s || 1500 || 1525".formatted(DATE_3));
    inOrder.verify(console).printLine("%s || -75 || 25".formatted(DATE_2));
    inOrder.verify(console).printLine("%s || 100 || 100".formatted(DATE_1));
    inOrder.verifyNoMoreInteractions();
  }
}
