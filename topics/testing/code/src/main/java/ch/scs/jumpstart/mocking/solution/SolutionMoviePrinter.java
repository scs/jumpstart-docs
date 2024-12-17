package ch.scs.jumpstart.mocking.solution;

import ch.scs.jumpstart.mocking.exercise.MovieRepository;
import ch.scs.jumpstart.mocking.exercise.Printer;

public record SolutionMoviePrinter(MovieRepository movieRepository, Printer printer) {

  public void printAllMovies() {
    movieRepository.getAllMovies().forEach(printer::print);
  }

  public void printMovie(int id) {
    var movie = movieRepository.getById(id);
    if (movie != null) {
      printer.print(movie);
    }
  }
}
