package ch.scs.jumpstart.trailerdetector.exercise.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailerRepository extends JpaRepository<Trailer, Integer> {
  Optional<Trailer> findByNetworkDevices_MacAddress(String macAddress);
}
