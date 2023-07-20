package ch.scs.jumpstart.movierental.exercise.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {
  @Id private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JsonProperty("rentals")
  private final List<Rental> rentals = new ArrayList<>();

  @SuppressWarnings("unused")
  public Customer() {}

  public Customer(String name) {
    this.name = name;
  }

  public void addRental(Rental arg) {
    rentals.add(arg);
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public String getName() {
    return name;
  }

  public String statement() {
    /*
    TODO
    here you have calculation and formatting logic
    you need to separate these concerns
     */
    double totalAmount = 0;
    String result = "Rental Record for " + getName() + "\n";

    for (Rental each : rentals) {
      double thisAmount = 0;

      // determine amounts for each line
      switch (each.getMovie().getPriceCode()) {
        case REGULAR:
          thisAmount += 2;
          if (each.getDaysRented() > 2) thisAmount += (each.getDaysRented() - 2) * 1.5;
          break;
        case NEW_RELEASE:
          thisAmount += each.getDaysRented() * 3;
          break;
        case CHILDREN:
          thisAmount += 1.5;
          if (each.getDaysRented() > 3) thisAmount += (each.getDaysRented() - 3) * 1.5;
          break;
      }

      // show figures for this rental
      //noinspection StringConcatenationInLoop
      result += "\t" + each.getMovie().getTitle() + "\t" + thisAmount + "\n";
      totalAmount += thisAmount;
    }

    // add footer lines
    result += "Amount owed is " + totalAmount + "\n";

    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return name.equals(customer.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
