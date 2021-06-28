package ch.scs.jumpstart.trailerdetector.exercise.controller;

import static java.util.function.Predicate.not;

import ch.scs.jumpstart.trailerdetector.exercise.domain.NetworkDevice;
import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrailerController {
  public static final String PATH = "/trailersByMac";
  private final TrailerRepository trailerRepository;

  @Autowired
  public TrailerController(TrailerRepository trailerRepository) {
    this.trailerRepository = trailerRepository;
  }

  @PostMapping(PATH)
  public ResponseEntity<Integer> getAndUpdateTrailersByMacAddresses(
      @RequestBody List<String> macAddresses) {
    var trailerAndOccurrenceMap =
        macAddresses.stream()
            .map(trailerRepository::findByNetworkDevices_MacAddress)
            .flatMap(Optional::stream)
            .collect(Collectors.toMap(Function.identity(), __ -> 1, Integer::sum));
    var networkDevices =
        trailerAndOccurrenceMap.keySet().stream()
            .map(Trailer::getNetworkDevices)
            .flatMap(Collection::stream)
            .filter(networkDevice -> macAddresses.contains(networkDevice.getMacAddress()))
            .collect(Collectors.toSet());
    var foundTrailerOptional =
        trailerAndOccurrenceMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey);
    if (foundTrailerOptional.isPresent()) {
      var foundTrailer = foundTrailerOptional.get();
      trailerAndOccurrenceMap.keySet().stream()
          .filter(not(foundTrailer::equals))
          .forEach(trailer -> trailer.removeNetworkDevices(networkDevices));
      macAddresses.stream().map(NetworkDevice::new).forEach(foundTrailer::addNetworkDevice);
      trailerRepository.saveAll(trailerAndOccurrenceMap.keySet());
    }
    return foundTrailerOptional
        .map(Trailer::getTrailerNumber)
        .map(Optional::of)
        .map(ResponseEntity::of)
        .orElse(ResponseEntity.notFound().build());
  }
}
