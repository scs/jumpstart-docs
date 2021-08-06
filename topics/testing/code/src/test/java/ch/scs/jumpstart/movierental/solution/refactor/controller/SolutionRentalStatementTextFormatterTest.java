package ch.scs.jumpstart.movierental.solution.refactor.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.scs.jumpstart.movierental.exercise.refactor.controller.RentalStatementBuilder;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionRentalStatementTextFormatterTest {

  private static final String CUSTOMER_NAME = "customer1";
  private static final RentalStatement EMPTY_STATEMENT =
      RentalStatementBuilder.builder(CUSTOMER_NAME).build();

  private static final RentalStatement STATEMENT_WITH_LINES =
      RentalStatementBuilder.builder(CUSTOMER_NAME)
          .withStatementMovie("movie1", 1.1f)
          .withStatementMovie("movie2", 100.2f)
          .build();
  private SolutionRentalStatementTextFormatter solutionRentalStatementTextFormatter;

  @BeforeEach
  public void setup() {
    solutionRentalStatementTextFormatter = new SolutionRentalStatementTextFormatter();
  }

  @Test
  public void format_empty_statement() {
    var result = solutionRentalStatementTextFormatter.format(EMPTY_STATEMENT);
    assertThat(result, is("Rental Record for customer1\nAmount owed is 0.0\n"));
  }

  @Test
  public void format_lines() {
    var result = solutionRentalStatementTextFormatter.format(STATEMENT_WITH_LINES);
    assertThat(
        result,
        is(
            "Rental Record for customer1\n"
                + "\tmovie1\t1.1\n"
                + "\tmovie2\t100.2\n"
                + "Amount owed is 101.299995\n"));
  }
}
