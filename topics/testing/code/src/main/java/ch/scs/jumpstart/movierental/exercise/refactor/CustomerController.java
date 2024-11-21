package ch.scs.jumpstart.movierental.exercise.refactor;

import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

  private final CustomerRepository customerRepository;
  private final MovieRepository movieRepository;

  public CustomerController(
      CustomerRepository customerRepository, MovieRepository movieRepository) {
    this.customerRepository = customerRepository;
    this.movieRepository = movieRepository;
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
    var movie = movieRepository.findById(addRental.movieTitle());
    if (movie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var rental = new Rental(movie.get(), addRental.daysRented());
    var customer = customerOptional.get();
    customer.addRental(rental);
    customerRepository.save(customer);

    return ResponseEntity.ok(customer);
  }

  public ResponseEntity<String> getInvoice(String customerName) {
    var customerOptional = customerRepository.findById(customerName);
    if (customerOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var customer = customerOptional.get();
    return ResponseEntity.ok(customer.statement());
  }

  public ResponseEntity<RentalStatement> getJsonInvoice(String customerName) {
    // TODO
    return ResponseEntity.internalServerError().build();
  }
}
