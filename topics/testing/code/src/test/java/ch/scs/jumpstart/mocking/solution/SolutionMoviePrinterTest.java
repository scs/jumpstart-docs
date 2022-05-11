package ch.scs.jumpstart.mocking.solution;

import static org.mockito.Mockito.*;

import ch.scs.jumpstart.mocking.exercise.MovieRepository;
import ch.scs.jumpstart.mocking.exercise.Printer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionMoviePrinterTest {
  private static final int MOVIE_1_ID = 1;
  private static final String MOVIE_1 = "movie1";
  private static final String MOVIE_2 = "movie2";
  private SolutionMoviePrinter solutionMoviePrinter;
  private MovieRepository movieRepository;
  private Printer printer;

  @BeforeEach
  void setup() {
    movieRepository = mock(MovieRepository.class);
    printer = mock(Printer.class);
    solutionMoviePrinter = new SolutionMoviePrinter(movieRepository, printer);
  }

  @Test
  public void do_nothing_for_print_all_movies_when_repo_empty() {
    when(movieRepository.getAllMovies()).thenReturn(List.of());

    solutionMoviePrinter.printAllMovies();

    verifyNoInteractions(printer);
  }

  @Test
  void prints_all_movies_in_correct_order() {
    when(movieRepository.getAllMovies()).thenReturn(List.of(MOVIE_1, MOVIE_2));

    solutionMoviePrinter.printAllMovies();

    var inOrder = inOrder(printer);
    inOrder.verify(printer).print(MOVIE_1);
    inOrder.verify(printer).print(MOVIE_2);
  }

  @Test
  public void do_nothing_in_printMovie_if_movie_does_not_exist() {
    solutionMoviePrinter.printMovie(42);

    verify(printer, never()).print(any());
  }

  @Test
  public void print_movie_in_printMovie() {
    when(movieRepository.getById(MOVIE_1_ID)).thenReturn(MOVIE_1);

    solutionMoviePrinter.printMovie(MOVIE_1_ID);

    verify(movieRepository).getById(MOVIE_1_ID);
    verify(printer).print(MOVIE_1);
    verifyNoMoreInteractions(movieRepository, printer);
  }
}
