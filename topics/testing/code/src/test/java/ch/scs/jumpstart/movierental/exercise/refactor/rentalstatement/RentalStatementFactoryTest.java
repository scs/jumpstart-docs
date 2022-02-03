package ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.refactor.controller.RentalStatementTextFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RentalStatementFactoryTest {
  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);
  private static final Movie MOVIE_2 = new Movie("2", PriceCode.NEW_RELEASE);
  private static final Movie MOVIE_3 = new Movie("3", PriceCode.REGULAR);

  private static final String CUSTOMER_NAME_1 = "1";

  private RentalStatementFactory rentalStatementFactory;

  @BeforeEach
  public void setup() {
    rentalStatementFactory = new RentalStatementFactory(new RentalStatementTextFormatter());
  }

  @Disabled
  @Test
  public void return_correct_invoice_for_children_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 0).build();

    assertThat(
        rentalStatementFactory.createStatement(customer),
        is("Rental Record for 1\n\t1\t1.5\nAmount owed is 1.5\n"));
  }

  @Test
  public void return_correct_invoice_for_children_movie_which_was_rented_for_more_than_3_days() {}

  @Test
  public void return_correct_invoice_for_new_release_movie() {}

  @Test
  public void return_correct_invoice_for_regular_movie() {}

  @Test
  public void return_correct_invoice_for_multiple_movies() {}
}
