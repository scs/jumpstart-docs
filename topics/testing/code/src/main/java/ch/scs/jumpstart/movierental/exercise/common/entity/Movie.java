package ch.scs.jumpstart.movierental.exercise.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {
  @Id private String title;
  private PriceCode priceCode;

  @SuppressWarnings("unused")
  public Movie() {}

  public Movie(String title, PriceCode priceCode) {
    this.title = title;
    this.priceCode = priceCode;
  }

  public PriceCode getPriceCode() {
    return priceCode;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Movie movie = (Movie) o;
    return Objects.equals(title, movie.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }
}
