package ch.scs.jumpstart.pattern.examples.organisation;

import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Organisation;

@SuppressWarnings("unused")
public interface OrganisationSupplier {
  Organisation getOrganisation();
}
