package ch.scs.jumpstart.movierental.solution.integrationtest.controller;

import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
public class SolutionSpringCustomerController {
  public static final String PATH = "/customers";

  private final SolutionCustomerController customerController;

  public SolutionSpringCustomerController(SolutionCustomerController customerController) {
    this.customerController = customerController;
  }

  @PostMapping(PATH)
  public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
    return customerController.addCustomer(customer);
  }

  @GetMapping(PATH)
  public ResponseEntity<List<Customer>> getCustomers() {
    return customerController.getCustomers();
  }

  @PostMapping(PATH + "/{customerName}/addRental")
  public ResponseEntity<Customer> addRental(
      @PathVariable String customerName, @RequestBody AddRental addRental) {
    return customerController.addRental(customerName, addRental);
  }

  @GetMapping(PATH + "/{customerName}/getInvoice")
  public ResponseEntity<String> getInvoice(@PathVariable String customerName) {
    return customerController.getInvoice(customerName);
  }

  @GetMapping(PATH + "/{customerName}/getJsonInvoice")
  public ResponseEntity<RentalStatement> getJsonInvoice(@PathVariable String customerName) {
    return customerController.getJsonInvoice(customerName);
  }
}
