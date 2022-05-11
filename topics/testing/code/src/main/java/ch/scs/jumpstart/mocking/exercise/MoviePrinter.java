package ch.scs.jumpstart.mocking.exercise;

@SuppressWarnings("unused")
public class MoviePrinter {
  @SuppressWarnings({"FieldCanBeLocal", "PMD.UnusedPrivateField"})
  private final MovieRepository movieRepository;

  @SuppressWarnings({"FieldCanBeLocal", "PMD.UnusedPrivateField"})
  private final Printer printer;

  @SuppressWarnings("unused")
  public MoviePrinter(MovieRepository movieRepository, Printer printer) {
    this.movieRepository = movieRepository;
    this.printer = printer;
  }

  @SuppressWarnings({"unused", "PMD"})
  public void printAllMovies() {}

  @SuppressWarnings({"unused", "PMD"})
  public void printMovie(int id) {}
}
