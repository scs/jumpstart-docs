package ch.scs.jumpstart.trailerdetector.solution.controller;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.solution.domain.SolutionTrailerService;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public class SolutionTrailerController {

  private final SolutionTrailerService trailerService;

  public SolutionTrailerController(SolutionTrailerService trailerService) {
    this.trailerService = trailerService;
  }

  public Pair<Integer, Optional<Integer>> getAndUpdateTrailersByMacAddresses(
      List<String> macAddresses) {
    if (macAddresses.isEmpty()) {
      return Pair.of(400, Optional.empty());
    }
    return trailerService
        .findAndUpdateTrailersByMacAddresses(macAddresses)
        .map(Trailer::getTrailerNumber)
        .map(Optional::of)
        .map(trailerNumber -> Pair.of(200, trailerNumber))
        .orElse(Pair.of(404, Optional.empty()));
  }
}
