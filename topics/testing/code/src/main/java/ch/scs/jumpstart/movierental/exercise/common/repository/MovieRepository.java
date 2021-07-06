package ch.scs.jumpstart.movierental.exercise.common.repository;

import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {}
