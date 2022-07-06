package ch.scs.jumpstart.movierental.solution.refactor.controller;

import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatementMovie;
import org.springframework.stereotype.Component;

@Component
public class SolutionRentalStatementTextFormatter {
  public String format(RentalStatement rentalStatement) {
    String result = "Rental Record for " + rentalStatement.getCustomerName() + "\n";

    for (RentalStatementMovie rentalStatementMovie : rentalStatement.getRentalStatementMovies()) {
      //noinspection StringConcatenationInLoop
      result +=
          "\t" + rentalStatementMovie.getTitle() + "\t" + rentalStatementMovie.getAmount() + "\n";
    }
    result += "Amount owed is " + rentalStatement.getAmountOwed() + "\n";

    return result;
  }
}
