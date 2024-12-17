package ch.scs.jumpstart.mocking.exercise;

@SuppressWarnings("unused")
public record MoviePrinter(MovieRepository movieRepository, Printer printer) {

  @SuppressWarnings({"unused", "PMD"})
  public void printAllMovies() {}

  @SuppressWarnings({"unused", "PMD"})
  public void printMovie(int id) {}
}
