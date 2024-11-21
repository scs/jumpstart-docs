package ch.scs.jumpstart.movierental.exercise.refactor.controller;

import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalStatementTextFormatterTest {

  private static final String CUSTOMER_NAME = "customer1";
  private static final RentalStatement EMPTY_STATEMENT =
      RentalStatementBuilder.builder(CUSTOMER_NAME).build();

  private static final RentalStatement STATEMENT_WITH_LINES =
      RentalStatementBuilder.builder(CUSTOMER_NAME)
          .withStatementMovie("movie1", 1.1f)
          .withStatementMovie("movie2", 100.2f)
          .build();
  private RentalStatementTextFormatter rentalStatementTextFormatter;

  @BeforeEach
  void setup() {
    rentalStatementTextFormatter = new RentalStatementTextFormatter();
  }

  @Test
  void format_empty_statement() {}

  @Test
  void format_lines() {}
}
