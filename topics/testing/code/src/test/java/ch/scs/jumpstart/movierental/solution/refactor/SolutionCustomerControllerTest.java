package ch.scs.jumpstart.movierental.solution.refactor;

import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import ch.scs.jumpstart.movierental.exercise.refactor.controller.RentalStatementBuilder;
import ch.scs.jumpstart.movierental.solution.refactor.controller.SolutionRentalStatementTextFormatter;
import ch.scs.jumpstart.movierental.solution.refactor.rentalstatement.SolutionRentalStatementFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionCustomerControllerTest {
  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);
  private static final Movie MOVIE_2 = new Movie("2", PriceCode.NEW_RELEASE);
  private static final Movie MOVIE_3 = new Movie("3", PriceCode.REGULAR);

  private static final String CUSTOMER_NAME_1 = "1";
  private static final Customer CUSTOMER_1 =
      CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 1).build();
  private static final Customer CUSTOMER_2 = new Customer("2");

  private SolutionCustomerController controller;
  private CustomerRepository customerRepository;
  private MovieRepository movieRepository;

  @BeforeEach
  public void setup() {
    customerRepository = mock(CustomerRepository.class);
    movieRepository = mock(MovieRepository.class);
    var solutionRentalStatementFactory = new SolutionRentalStatementFactory();
    var solutionRentalStatementTextFormatter = new SolutionRentalStatementTextFormatter();
    controller =
        new SolutionCustomerController(
            customerRepository,
            movieRepository,
            solutionRentalStatementFactory,
            solutionRentalStatementTextFormatter);
  }

  @Test
  public void return_added_customer_when_add_customer() {
    assertThat(controller.addCustomer(CUSTOMER_1), is(ok(CUSTOMER_1)));
    verify(customerRepository).save(CUSTOMER_1);
  }

  @Test
  public void return_empty_list_of_customers_when_no_customers_in_db() {
    when(customerRepository.findAll()).thenReturn(Collections.emptyList());

    assertThat(controller.getCustomers(), is(ok(Collections.emptyList())));
  }

  @Test
  public void return_customers_retrieved_from_repository() {
    var customers = List.of(CUSTOMER_1, CUSTOMER_2);
    when(customerRepository.findAll()).thenReturn(customers);

    assertThat(controller.getCustomers(), is(ok(customers)));
  }

  @Test
  public void return_not_found_if_customer_for_addRental_cannot_be_found() {
    when(customerRepository.findById(notNull())).thenReturn(Optional.empty());

    assertThat(controller.addRental("", new AddRental()), is(notFound().build()));
  }

  @Test
  public void return_not_found_if_movie_for_addRental_cannot_be_found() {
    when(customerRepository.findById(CUSTOMER_1.getName())).thenReturn(Optional.of(CUSTOMER_1));
    when(movieRepository.findById(notNull())).thenReturn(Optional.empty());

    assertThat(
        controller.addRental(CUSTOMER_1.getName(), new AddRental("", 1)), is(notFound().build()));
  }

  @Test
  public void return_customer_where_rental_was_added_after_successful_addRental() {
    when(customerRepository.findById(CUSTOMER_1.getName())).thenReturn(Optional.of(CUSTOMER_1));
    when(movieRepository.findById(MOVIE_2.getTitle())).thenReturn(Optional.of(MOVIE_2));

    assertThat(
        controller.addRental(CUSTOMER_1.getName(), new AddRental(MOVIE_2.getTitle(), 1)),
        is(ok(CUSTOMER_1)));
    verify(customerRepository).save(CUSTOMER_1);
  }

  @Test
  public void return_not_found_if_customer_for_getInvoice_cannot_be_found() {
    when(customerRepository.findById(notNull())).thenReturn(Optional.empty());

    assertThat(controller.getInvoice(""), is(notFound().build()));
  }

  @Test
  public void return_correct_invoice_for_children_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 0).build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        controller.getInvoice(CUSTOMER_NAME_1),
        is(ok("Rental Record for 1\n" + "\t1\t1.5\n" + "Amount owed is 1.5\n")));
  }

  @Test
  public void return_correct_invoice_for_new_release_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_2, 10).build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        controller.getInvoice(CUSTOMER_NAME_1),
        is(ok("Rental Record for 1\n" + "\t2\t30.0\n" + "Amount owed is 30.0\n")));
  }

  @Test
  public void return_correct_invoice_for_new_regular_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_3, 4).build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        controller.getInvoice(CUSTOMER_NAME_1),
        is(ok("Rental Record for 1\n" + "\t3\t5.0\n" + "Amount owed is 5.0\n")));
  }

  @Test
  public void return_correct_invoice_for_multiple_movies() {
    var customer =
        CustomerBuilder.builder(CUSTOMER_NAME_1)
            .withRental(MOVIE_2, 1)
            .withRental(MOVIE_1, 1)
            .withRental(MOVIE_3, 1)
            .build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        controller.getInvoice(CUSTOMER_NAME_1),
        is(
            ok(
                "Rental Record for 1\n"
                    + "\t2\t3.0\n"
                    + "\t1\t1.5\n"
                    + "\t3\t2.0\n"
                    + "Amount owed is 6.5\n")));
  }

  @Test
  public void return_not_found_if_customer_for_getJsonInvoice_cannot_be_found() {
    when(customerRepository.findById(notNull())).thenReturn(Optional.empty());

    assertThat(controller.getJsonInvoice(""), is(notFound().build()));
  }

  @Test
  public void return_json_invoice() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_3, 4).build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        controller.getJsonInvoice(CUSTOMER_NAME_1),
        is(
            ok(
                RentalStatementBuilder.builder(CUSTOMER_NAME_1)
                    .withStatementMovie(MOVIE_3.getTitle(), 5f)
                    .build())));
  }
}
