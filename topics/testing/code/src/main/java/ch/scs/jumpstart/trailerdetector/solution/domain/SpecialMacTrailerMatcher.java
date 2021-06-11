package ch.scs.jumpstart.trailerdetector.solution.domain;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import java.util.Map;

public class SpecialMacTrailerMatcher implements TrailerMatcher {
  private final String specialMac;
  private final TrailerMatcher fallback;

  public SpecialMacTrailerMatcher(String specialMac, TrailerMatcher fallback) {
    this.specialMac = specialMac;
    this.fallback = fallback;
  }

  @Override
  public Trailer match(Map<String, Trailer> macTrailerMap) {
    var trailer = macTrailerMap.get(specialMac);
    if (trailer != null) {
      return trailer;
    }
    return fallback.match(macTrailerMap);
  }
}
