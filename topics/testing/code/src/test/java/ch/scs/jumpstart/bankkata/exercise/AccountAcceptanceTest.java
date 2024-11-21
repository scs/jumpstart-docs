package ch.scs.jumpstart.bankkata.exercise;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AccountAcceptanceTest {
  private static final LocalDate DATE_1 = LocalDate.parse("2021-01-01");
  private static final LocalDate DATE_2 = LocalDate.parse("2021-06-12");
  private static final LocalDate DATE_3 = LocalDate.parse("2021-06-15");
  private static final LocalDate DATE_4 = LocalDate.parse("2021-06-16");

  private Console console;
  private Clock clock;
  private Account account;

  @BeforeEach
  public void setup() {
    console = mock(Console.class);
    ConsoleAccountPrinter consoleAccountPrinter = new ConsoleAccountPrinter(console);
    clock = mock(Clock.class);
    account = new Account(clock, consoleAccountPrinter);
  }

  @Disabled
  @Test
  public void print_the_ledger_of_the_account() {
    when(clock.getLocalDate()); /*thenReturn(...);*/

    account.deposit(100);
    // add missing lines
    account.print();

    var inOrder = inOrder(console);
    inOrder.verify(console).printLine("Date || Amount || Balance");
    inOrder.verify(console).printLine("%s || 100 || 100".formatted(DATE_1));
    // add missing lines
    inOrder.verifyNoMoreInteractions();
  }
}
