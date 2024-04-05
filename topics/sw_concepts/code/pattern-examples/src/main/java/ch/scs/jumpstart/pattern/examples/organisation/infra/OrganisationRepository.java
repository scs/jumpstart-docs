package ch.scs.jumpstart.pattern.examples.organisation.infra;

import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Organisation;

public interface OrganisationRepository {
  Organisation getById(long id);
}
