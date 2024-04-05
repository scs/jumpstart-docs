package ch.scs.jumpstart.pattern.examples.organisation.infra;

import java.util.Optional;

public class Configuration {
  public Optional<Long> getOrganisationId() {
    // as example, this would read it from some kind of configuration.
    return Optional.of(0L);
  }

  public Optional<Long> getLocationId() {
    return Optional.of(0L);
  }
}
