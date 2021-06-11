package ch.scs.jumpstart.trailerdetector.exercise.domain;

import java.util.Optional;

public interface TrailerRepository {
  Optional<Trailer> getByNetworkDeviceMac(String macAddress);

  void saveAll(Iterable<Trailer> trailers);
}
