package ch.scs.jumpstart.bankkata.exercise;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

  private static final LocalDate FIRST_DATE = LocalDate.now();
  private static final int DEPOSIT_AMOUNT = 1;
  private static final int BALANCE_AFTER_DEPOSIT = 1;
  private static final int WITHDRAW_AMOUNT = 1;
  private static final int SECOND_BALANCE = 0;

  private ConsoleAccountPrinter consoleAccountPrinter;
  private Account account;
  private Clock clock;

  @BeforeEach
  void setup() {
    consoleAccountPrinter = mock(ConsoleAccountPrinter.class);
    clock = mock(Clock.class);
    account = new Account(clock, consoleAccountPrinter);
  }

  @Test
  void print_empty_statement_list_when_no_transactions_in_account() {}

  @Test
  void print_the_transactions_and_sum_up_the_balance() {}
}
