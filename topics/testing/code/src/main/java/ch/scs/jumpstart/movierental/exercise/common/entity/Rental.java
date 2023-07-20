package ch.scs.jumpstart.movierental.exercise.common.entity;

import jakarta.persistence.*;

@Entity
public class Rental {
  @SuppressWarnings("unused")
  @Id
  @GeneratedValue
  private int id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Movie movie;

  private int daysRented;

  @SuppressWarnings("unused")
  public Rental() {}

  public Rental(Movie movie, int daysRented) {
    this.movie = movie;
    this.daysRented = daysRented;
  }

  public int getDaysRented() {
    return daysRented;
  }

  public Movie getMovie() {
    return movie;
  }
}
