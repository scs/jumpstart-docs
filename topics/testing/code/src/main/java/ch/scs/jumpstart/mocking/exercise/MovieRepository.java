package ch.scs.jumpstart.mocking.exercise;

import java.util.List;

public interface MovieRepository {
  List<String> getAllMovies();

  String getById(int id);
}
