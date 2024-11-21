package ch.scs.jumpstart.movierental.solution.refactor.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
  void setup() {
    solutionRentalStatementTextFormatter = new SolutionRentalStatementTextFormatter();
  }

  @Test
  void format_empty_statement() {
    var result = solutionRentalStatementTextFormatter.format(EMPTY_STATEMENT);
    assertThat(result)
        .isEqualTo(
            """
            Rental Record for customer1
            Amount owed is 0.0
            """);
  }

  @Test
  void format_lines() {
    var result = solutionRentalStatementTextFormatter.format(STATEMENT_WITH_LINES);
    assertThat(result)
        .isEqualTo(
            """
            Rental Record for customer1
            \tmovie1\t1.1
            \tmovie2\t100.2
            Amount owed is 101.299995
            """);
  }
}
