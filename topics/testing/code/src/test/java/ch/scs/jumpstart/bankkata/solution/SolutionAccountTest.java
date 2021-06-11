package ch.scs.jumpstart.bankkata.solution;

import static java.util.List.of;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolutionAccountTest {

  private static final LocalDate FIRST_DATE = LocalDate.now();
  private static final int DEPOSIT_AMOUNT = 1;
  private static final int BALANCE_AFTER_DEPOSIT = 1;
  private static final int WITHDRAW_AMOUNT = 1;
  private static final int SECOND_BALANCE = 0;

  private SolutionConsoleAccountPrinter consoleAccountPrinter;
  private SolutionAccount account;
  private SolutionClock clock;

  @BeforeEach
  public void setup() {
    consoleAccountPrinter = mock(SolutionConsoleAccountPrinter.class);
    clock = mock(SolutionClock.class);
    account = new SolutionAccount(clock, consoleAccountPrinter);
  }

  @Test
  public void print_empty_statement_list_when_no_transactions_in_account() {
    account.print();

    verify(consoleAccountPrinter).print(Collections.emptyList());
  }

  @Test
  public void print_the_transactions_and_sum_up_the_balance() {
    when(clock.getLocalDate()).thenReturn(FIRST_DATE);
    account.deposit(DEPOSIT_AMOUNT);
    account.withdraw(WITHDRAW_AMOUNT);

    account.print();

    verify(consoleAccountPrinter)
        .print(
            of(
                SolutionStatementLine.of(DEPOSIT_AMOUNT, FIRST_DATE, BALANCE_AFTER_DEPOSIT),
                SolutionStatementLine.of(-WITHDRAW_AMOUNT, FIRST_DATE, SECOND_BALANCE)));
  }
}
