package ch.scs.jumpstart.trailerdetector.solution.domain;

import static java.util.function.Predicate.not;

import ch.scs.jumpstart.trailerdetector.exercise.domain.NetworkDevice;
import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class SolutionTrailerService {
  private final TrailerRepository trailerRepository;
  private final TrailerMatcher trailerMatcher;

  public SolutionTrailerService(
      TrailerRepository trailerRepository, TrailerMatcher trailerMatcher) {
    this.trailerRepository = trailerRepository;
    this.trailerMatcher = trailerMatcher;
  }

  public Optional<Trailer> findAndUpdateTrailersByMacAddresses(List<String> macAddresses) {
    var macTrailerMap = getMacTrailerMap(macAddresses);
    var networkDevices =
        macTrailerMap.values().stream()
            .map(Trailer::getNetworkDevices)
            .flatMap(Collection::stream)
            .filter(networkDevice -> macAddresses.contains(networkDevice.getMacAddress()))
            .collect(Collectors.toSet());
    if (macTrailerMap.isEmpty()) {
      return Optional.empty();
    }
    var matchedTrailer = trailerMatcher.match(macTrailerMap);
    try {
      macTrailerMap.values().stream()
          .filter(not(matchedTrailer::equals))
          .forEach(trailer -> trailer.removeNetworkDevices(networkDevices));
      macAddresses.stream().map(NetworkDevice::new).forEach(matchedTrailer::addNetworkDevice);
      trailerRepository.saveAll(Set.copyOf(macTrailerMap.values()));
    } catch (Exception ignored) {

    }
    return Optional.of(matchedTrailer);
  }

  private Map<String, Trailer> getMacTrailerMap(List<String> macAddresses) {
    return macAddresses.stream()
        .map(s -> Pair.of(s, trailerRepository.findByNetworkDevices_MacAddress(s)))
        .filter(stringOptionalPair -> stringOptionalPair.getRight().isPresent())
        .map(
            stringOptionalPair ->
                Pair.of(stringOptionalPair.getLeft(), stringOptionalPair.getRight().get()))
        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
  }
}
