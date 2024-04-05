package ch.scs.jumpstart.pattern.examples.organisation.infra;

import ch.scs.jumpstart.pattern.examples.organisation.infra.domain.Location;

public interface LocationRepository {
  Location getById(long id);
}
