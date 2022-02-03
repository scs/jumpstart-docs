package ch.scs.jumpstart.bankkata.exercise;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleAccountPrinterTest {

  private static final LocalDate D_2021_06_15 = LocalDate.parse("2021-06-15");
  private static final LocalDate D_2021_06_16 = LocalDate.parse("2021-06-16");
  private Console console;
  private ConsoleAccountPrinter consoleAccountPrinter;

  @BeforeEach
  public void setup() {
    console = mock(Console.class);
    consoleAccountPrinter = new ConsoleAccountPrinter(console);
  }

  @Test
  public void print_header_if_no_statement_line() {}

  @Test
  public void print_statements_in_descending_date_order() {}
}
