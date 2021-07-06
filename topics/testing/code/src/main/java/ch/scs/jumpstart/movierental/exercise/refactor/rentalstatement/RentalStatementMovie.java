package ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement;

import java.util.Objects;

@SuppressWarnings("unused")
public class RentalStatementMovie {
  private String title;
  private float amount;

  @SuppressWarnings("unused")
  public RentalStatementMovie() {}

  public RentalStatementMovie(String title, float amount) {
    this.title = title;
    this.amount = amount;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public float getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RentalStatementMovie that = (RentalStatementMovie) o;
    return Float.compare(that.amount, amount) == 0 && title.equals(that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, amount);
  }
}
