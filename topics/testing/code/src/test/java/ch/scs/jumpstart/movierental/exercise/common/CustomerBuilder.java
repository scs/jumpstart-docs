package ch.scs.jumpstart.movierental.exercise.common;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerBuilder {
  public static CustomerBuilder builder(String name) {
    return new CustomerBuilder(name, Collections.emptyList());
  }

  private final String name;
  private final List<Rental> rentals;

  private CustomerBuilder(String name, List<Rental> rentals) {
    this.name = name;
    this.rentals = rentals;
  }

  public CustomerBuilder withRental(Movie movie, int daysRented) {
    var rentalsCopy = new ArrayList<>(rentals);
    rentalsCopy.add(new Rental(movie, daysRented));
    return new CustomerBuilder(name, rentalsCopy);
  }

  public Customer build() {
    var customer = new Customer(name);
    rentals.forEach(customer::addRental);
    return customer;
  }
}
