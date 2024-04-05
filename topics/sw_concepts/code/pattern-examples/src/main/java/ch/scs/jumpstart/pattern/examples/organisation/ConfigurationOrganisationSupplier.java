package ch.scs.jumpstart.pattern.examples.organisation;

import ch.scs.jumpstart.pattern.examples.organisation.infra.Configuration;
import ch.scs.jumpstart.pattern.examples.organisation.infra.OrganisationRepository;
import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Organisation;

@SuppressWarnings("unused")
public class ConfigurationOrganisationSupplier implements OrganisationSupplier {

  private final Configuration config;
  private final OrganisationRepository organisationRepository;

  public ConfigurationOrganisationSupplier(
      Configuration config, OrganisationRepository organisationRepository) {
    this.config = config;
    this.organisationRepository = organisationRepository;
  }

  @Override
  public Organisation getOrganisation() {
    return config
        .getOrganisationId()
        .map(organisationRepository::getById)
        .orElseThrow(() -> new RuntimeException("Organisation ID not found"));
  }
}
