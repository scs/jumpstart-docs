package ch.scs.jumpstart.movierental.solution.refactor.rentalstatement;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatementMovie;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class SolutionRentalStatementFactory {

  public SolutionRentalStatementFactory() {}

  public RentalStatement createStatement(Customer customer) {
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
