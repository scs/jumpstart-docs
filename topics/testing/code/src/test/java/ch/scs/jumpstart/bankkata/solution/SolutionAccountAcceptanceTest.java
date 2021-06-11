package ch.scs.jumpstart.bankkata.solution;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SolutionAccountAcceptanceTest {
  @Test
  public void print_the_ledger_of_the_account() {
    SolutionConsole console = mock(SolutionConsole.class);
    var consoleAccountPrinter = new SolutionConsoleAccountPrinter(console);
    var clock = mock(SolutionClock.class);
    when(clock.getLocalDate())
        .thenReturn(
            LocalDate.parse("2021-01-01"),
            LocalDate.parse("2021-06-12"),
            LocalDate.parse("2021-06-15"),
            LocalDate.parse("2021-06-16"));
    var account = new SolutionAccount(clock, consoleAccountPrinter);

    account.deposit(100);
    account.withdraw(75);
    account.deposit(1500);
    account.withdraw(250);
    account.print();

    var inOrder = inOrder(console);
    inOrder.verify(console).printLine("Date       || Amount || Balance");
    inOrder.verify(console).printLine("16/06/2021 ||   -250 ||    1275");
    inOrder.verify(console).printLine("15/06/2021 ||   1500 ||    1525");
    inOrder.verify(console).printLine("12/06/2021 ||    -75 ||      25");
    inOrder.verify(console).printLine("01/01/2021 ||    100 ||     100");
    inOrder.verifyNoMoreInteractions();
  }
}
