package ch.scs.jumpstart.trailerdetector.solution.domain;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import java.util.Map;

public interface TrailerMatcher {
  Trailer match(Map<String, Trailer> macTrailerMap);
}
