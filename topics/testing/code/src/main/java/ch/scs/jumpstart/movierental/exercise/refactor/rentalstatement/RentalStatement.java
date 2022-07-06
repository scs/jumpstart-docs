package ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class RentalStatement {
  private String customerName;
  private float amountOwed;
  private List<RentalStatementMovie> rentalStatementMovies;

  public RentalStatement() {}

  public RentalStatement(
      String customerName, float amountOwed, List<RentalStatementMovie> rentalStatementMovies) {
    this.customerName = customerName;
    this.amountOwed = amountOwed;
    this.rentalStatementMovies = rentalStatementMovies;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public float getAmountOwed() {
    return amountOwed;
  }

  public void setAmountOwed(float amountOwed) {
    this.amountOwed = amountOwed;
  }

  public List<RentalStatementMovie> getRentalStatementMovies() {
    return rentalStatementMovies;
  }

  public void setRentalStatementMovies(List<RentalStatementMovie> rentalStatementMovies) {
    this.rentalStatementMovies = rentalStatementMovies;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RentalStatement that = (RentalStatement) o;
    return Float.compare(that.amountOwed, amountOwed) == 0
        && customerName.equals(that.customerName)
        && rentalStatementMovies.equals(that.rentalStatementMovies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerName, amountOwed, rentalStatementMovies);
  }
}
