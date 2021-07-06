package ch.scs.jumpstart.movierental.solution.refactor;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import ch.scs.jumpstart.movierental.solution.refactor.controller.SolutionRentalStatementTextFormatter;
import ch.scs.jumpstart.movierental.solution.refactor.rentalstatement.SolutionRentalStatementFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SolutionCustomerController {
  private final CustomerRepository customerRepository;
  private final MovieRepository movieRepository;
  private final SolutionRentalStatementFactory solutionRentalStatementFactory;
  private final SolutionRentalStatementTextFormatter solutionRentalStatementTextFormatter;

  public SolutionCustomerController(
      CustomerRepository customerRepository,
      MovieRepository movieRepository,
      SolutionRentalStatementFactory solutionRentalStatementFactory,
      SolutionRentalStatementTextFormatter solutionRentalStatementTextFormatter) {
    this.customerRepository = customerRepository;
    this.movieRepository = movieRepository;
    this.solutionRentalStatementFactory = solutionRentalStatementFactory;
    this.solutionRentalStatementTextFormatter = solutionRentalStatementTextFormatter;
  }

  public ResponseEntity<Customer> addCustomer(Customer customer) {
    customerRepository.save(customer);
    return ResponseEntity.ok(customer);
  }

  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok(customerRepository.findAll());
  }

  public ResponseEntity<Customer> addRental(String customerName, AddRental addRental) {
    var customerOptional = customerRepository.findById(customerName);
    if (customerOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var movie = movieRepository.findById(addRental.getMovieTitle());
    if (movie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var rental = new Rental(movie.get(), addRental.getDaysRented());
    var customer = customerOptional.get();
    customer.addRental(rental);
    customerRepository.save(customer);

    return ResponseEntity.ok(customer);
  }

  public ResponseEntity<String> getInvoice(String customerName) {
    var rentalStatementOptional = createRentalStatement(customerName);
    if (rentalStatementOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var formattedText = solutionRentalStatementTextFormatter.format(rentalStatementOptional.get());
    return ResponseEntity.ok(formattedText);
  }

  public ResponseEntity<RentalStatement> getJsonInvoice(String customerName) {
    var rentalStatementOptional = createRentalStatement(customerName);
    if (rentalStatementOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(rentalStatementOptional.get());
  }

  private Optional<RentalStatement> createRentalStatement(String customerName) {
    var customerOptional = customerRepository.findById(customerName);
    if (customerOptional.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(solutionRentalStatementFactory.createStatement(customerOptional.get()));
  }

  public static class AddRental {
    private String movieTitle;
    private int daysRented;

    public AddRental() {}

    public AddRental(String movieTitle, int daysRented) {
      this.movieTitle = movieTitle;
      this.daysRented = daysRented;
    }

    public String getMovieTitle() {
      return movieTitle;
    }

    public int getDaysRented() {
      return daysRented;
    }
  }
}
