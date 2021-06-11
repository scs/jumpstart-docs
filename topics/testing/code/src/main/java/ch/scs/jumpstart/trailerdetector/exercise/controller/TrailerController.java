package ch.scs.jumpstart.trailerdetector.exercise.controller;

import static java.util.function.Predicate.not;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class TrailerController {
  private final TrailerRepository trailerRepository;

  public TrailerController(TrailerRepository trailerRepository) {
    this.trailerRepository = trailerRepository;
  }

  public Pair<Integer, Optional<Integer>> getAndUpdateTrailersByMacAddresses(
      List<String> macAddresses) {
    var trailerAndOccurrenceMap =
        macAddresses.stream()
            .map(trailerRepository::getByNetworkDeviceMac)
            .flatMap(Optional::stream)
            .collect(Collectors.toMap(Function.identity(), __ -> 1, Integer::sum));
    var foundTrailerOptional =
        trailerAndOccurrenceMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey);
    if (foundTrailerOptional.isPresent()) {
      var foundTrailer = foundTrailerOptional.get();
      trailerAndOccurrenceMap.keySet().stream()
          .filter(not(foundTrailer::equals))
          .forEach(trailer -> trailer.removeNetworkDevices(macAddresses));
      macAddresses.forEach(foundTrailer::addNetworkDevice);
      trailerRepository.saveAll(trailerAndOccurrenceMap.keySet());
    }
    return foundTrailerOptional
        .map(Trailer::getTrailerNumber)
        .map(Optional::of)
        .map(trailerNumber -> Pair.of(200, trailerNumber))
        .orElse(Pair.of(404, Optional.empty()));
  }
}
