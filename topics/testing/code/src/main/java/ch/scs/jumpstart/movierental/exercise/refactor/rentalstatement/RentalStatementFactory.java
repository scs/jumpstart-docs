package ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import ch.scs.jumpstart.movierental.exercise.refactor.controller.RentalStatementTextFormatter;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class RentalStatementFactory {

  private final RentalStatementTextFormatter rentalStatementTextFormatter;

  public RentalStatementFactory(RentalStatementTextFormatter rentalStatementTextFormatter) {
    this.rentalStatementTextFormatter = rentalStatementTextFormatter;
  }

  public String createStatement(Customer customer) {
    // TODO
    return null;
  }

  /*
   * The methods named step_* are here to show the solutions for the different steps.
   * If you get stuck, look here for inspiration.
   * Maybe they don't compile anymore after you e.g. removed the method Customer::statement.
   * Just delete the methods after you finished the corresponding step.
   */

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private String step_2(Customer customer) {
    return customer.statement();
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private String step_3(Customer customer) {
    double totalAmount = 0;
    String result = "Rental Record for %s\n".formatted(customer.getName());

    for (Rental each : customer.getRentals()) {
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
      result += "\t%s\t%s\n".formatted(each.getMovie().getTitle(), thisAmount);
      totalAmount += thisAmount;
    }

    // add footer lines
    result += "Amount owed is %s\n".formatted(totalAmount);

    return result;
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private String step_4(Customer customer) {
    // create data structure
    var rentalStatementMovies = new ArrayList<RentalStatementMovie>();
    for (Rental rental : customer.getRentals()) {
      var title = rental.getMovie().getTitle();

      switch (rental.getMovie().getPriceCode()) {
        case REGULAR:
          {
            float movieAmount = 2;
            if (rental.getDaysRented() > 2) {
              movieAmount += (rental.getDaysRented() - 2) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case NEW_RELEASE:
          {
            float movieAmount = rental.getDaysRented() * 3;
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case CHILDREN:
          {
            float movieAmount = 1.5f;
            if (rental.getDaysRented() > 3) {
              movieAmount += (rental.getDaysRented() - 3) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
      }
    }

    float sum = 0;
    for (RentalStatementMovie rentalStatementMovie : rentalStatementMovies) {
      sum += rentalStatementMovie.getAmount();
    }

    // create string from data structure
    String result = "Rental Record for %s\n".formatted(customer.getName());
    for (RentalStatementMovie rentalStatementMovie : rentalStatementMovies) {
      //noinspection StringConcatenationInLoop
      result +=
          "\t%s\t%s\n".formatted(rentalStatementMovie.getTitle(), rentalStatementMovie.getAmount());
    }
    result += "Amount owed is " + sum + "\n";

    return result;
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private String step_5(Customer customer) {
    // create data structure
    var rentalStatementMovies = new ArrayList<RentalStatementMovie>();
    for (Rental rental : customer.getRentals()) {
      var title = rental.getMovie().getTitle();

      switch (rental.getMovie().getPriceCode()) {
        case REGULAR:
          {
            float movieAmount = 2;
            if (rental.getDaysRented() > 2) {
              movieAmount += (rental.getDaysRented() - 2) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case NEW_RELEASE:
          {
            float movieAmount = rental.getDaysRented() * 3;
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case CHILDREN:
          {
            float movieAmount = 1.5f;
            if (rental.getDaysRented() > 3) {
              movieAmount += (rental.getDaysRented() - 3) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
      }
    }

    float sum = 0;
    for (RentalStatementMovie rentalStatementMovie : rentalStatementMovies) {
      sum += rentalStatementMovie.getAmount();
    }

    return rentalStatementTextFormatter.format(
        new RentalStatement(customer.getName(), sum, rentalStatementMovies));
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private RentalStatement step_6(Customer customer) {
    var customerName = customer.getName();

    var rentalStatementMovies = new ArrayList<RentalStatementMovie>();
    for (Rental rental : customer.getRentals()) {
      var title = rental.getMovie().getTitle();

      switch (rental.getMovie().getPriceCode()) {
        case REGULAR:
          {
            float movieAmount = 2;
            if (rental.getDaysRented() > 2) {
              movieAmount += (rental.getDaysRented() - 2) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case NEW_RELEASE:
          {
            float movieAmount = rental.getDaysRented() * 3;
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
        case CHILDREN:
          {
            float movieAmount = 1.5f;
            if (rental.getDaysRented() > 3) {
              movieAmount += (rental.getDaysRented() - 3) * 1.5;
            }
            rentalStatementMovies.add(new RentalStatementMovie(title, movieAmount));
            break;
          }
      }
    }

    float sum = 0;
    for (RentalStatementMovie rentalStatementMovie : rentalStatementMovies) {
      sum += rentalStatementMovie.getAmount();
    }

    return new RentalStatement(customerName, sum, rentalStatementMovies);
  }
}
