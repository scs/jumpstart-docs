package ch.scs.jumpstart.pattern.examples.organisation;

import ch.scs.jumpstart.pattern.examples.organisation.infra.Configuration;
import ch.scs.jumpstart.pattern.examples.organisation.infra.LocationRepository;
import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Location;
import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Organisation;

@SuppressWarnings("unused")
public class LocationOrganisationProvider implements OrganisationSupplier {

  private final Configuration config;
  private final LocationRepository locationRepository;

  public LocationOrganisationProvider(Configuration config, LocationRepository locationRepository) {
    this.config = config;
    this.locationRepository = locationRepository;
  }

  @Override
  public Organisation getOrganisation() {
    return config
        .getOrganisationId()
        .map(locationRepository::getById)
        .map(Location::organisation)
        .orElseThrow(() -> new RuntimeException("Location not found"));
  }
}
