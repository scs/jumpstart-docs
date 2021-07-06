package ch.scs.jumpstart.movierental.exercise.refactor.controller;

import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatementMovie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RentalStatementBuilder {

  public static RentalStatementBuilder builder(String customerName) {
    return new RentalStatementBuilder(customerName, Collections.emptyList());
  }

  private final String customerName;
  private final List<RentalStatementMovie> rentalStatementMovies;

  public RentalStatementBuilder(
      String customerName, List<RentalStatementMovie> rentalStatementMovies) {
    this.customerName = customerName;
    this.rentalStatementMovies = rentalStatementMovies;
  }

  public RentalStatementBuilder withStatementMovie(String title, float amount) {
    var listCopy = new ArrayList<>(this.rentalStatementMovies);
    listCopy.add(new RentalStatementMovie(title, amount));
    return new RentalStatementBuilder(customerName, listCopy);
  }

  public RentalStatement build() {
    float sum = 0;
    for (RentalStatementMovie movie : rentalStatementMovies) {
      sum += movie.getAmount();
    }
    return new RentalStatement(customerName, sum, rentalStatementMovies);
  }
}
