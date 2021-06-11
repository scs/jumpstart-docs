package ch.scs.jumpstart.trailerdetector.solution.controller;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrailerBuilder {
  public static TrailerBuilder create() {
    return create(null);
  }

  public static TrailerBuilder create(Integer number) {
    return new TrailerBuilder(number, Collections.emptyList());
  }

  private final Integer trailerNumber;
  private final List<String> macAddresses;

  private TrailerBuilder(Integer trailerNumber, List<String> macAddresses) {
    this.trailerNumber = trailerNumber;
    this.macAddresses = macAddresses;
  }

  public TrailerBuilder withMacAddress(String macAddress) {
    var newList = new ArrayList<>(macAddresses);
    newList.add(macAddress);
    return new TrailerBuilder(trailerNumber, newList);
  }

  public Trailer build() {
    var trailer = new Trailer();
    trailer.setTrailerNumber(trailerNumber);
    macAddresses.forEach(trailer::addNetworkDevice);
    return trailer;
  }
}
