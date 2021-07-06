package ch.scs.jumpstart.movierental.solution.refactor.rentalstatement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.refactor.controller.RentalStatementBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionRentalStatementFactoryTest {
  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);
  private static final Movie MOVIE_2 = new Movie("2", PriceCode.NEW_RELEASE);
  private static final Movie MOVIE_3 = new Movie("3", PriceCode.REGULAR);

  private static final String CUSTOMER_NAME_1 = "1";

  private SolutionRentalStatementFactory solutionRentalStatementFactory;

  @BeforeEach
  public void setup() {
    solutionRentalStatementFactory = new SolutionRentalStatementFactory();
  }

  @Test
  public void return_correct_invoice_for_children_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 0).build();

    assertThat(
        solutionRentalStatementFactory.createStatement(customer),
        is(
            RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                .withStatementMovie(MOVIE_1.getTitle(), 1.5f)
                .build()));
  }

  @Test
  public void return_correct_invoice_for_children_movie_which_was_rented_for_more_than_3_days() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 4).build();

    assertThat(
        solutionRentalStatementFactory.createStatement(customer),
        is(
            RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                .withStatementMovie(MOVIE_1.getTitle(), 3f)
                .build()));
  }

  @Test
  public void return_correct_invoice_for_new_release_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_2, 10).build();

    assertThat(
        solutionRentalStatementFactory.createStatement(customer),
        is(
            RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                .withStatementMovie(MOVIE_2.getTitle(), 30f)
                .build()));
  }

  @Test
  public void return_correct_invoice_for_regular_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_3, 4).build();

    assertThat(
        solutionRentalStatementFactory.createStatement(customer),
        is(
            RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                .withStatementMovie(MOVIE_3.getTitle(), 5.0f)
                .build()));
  }

  @Test
  public void return_correct_invoice_for_multiple_movies() {
    var customer =
        CustomerBuilder.builder(CUSTOMER_NAME_1)
            .withRental(MOVIE_2, 1)
            .withRental(MOVIE_1, 1)
            .withRental(MOVIE_3, 1)
            .build();

    assertThat(
        solutionRentalStatementFactory.createStatement(customer),
        is(
            RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                .withStatementMovie(MOVIE_2.getTitle(), 3.0f)
                .withStatementMovie(MOVIE_1.getTitle(), 1.5f)
                .withStatementMovie(MOVIE_3.getTitle(), 2.0f)
                .build()));
  }
}
