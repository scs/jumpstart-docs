package ch.scs.jumpstart.trailerdetector.solution.domain;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ByOccurrenceTrailerMatcher implements TrailerMatcher {
  @Override
  public Trailer match(Map<String, Trailer> macTrailerMap) {
    var occurrenceMap =
        macTrailerMap.values().stream()
            .collect(Collectors.toMap(Function.identity(), __ -> 1, Integer::sum));
    return occurrenceMap.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElseThrow();
  }
}
