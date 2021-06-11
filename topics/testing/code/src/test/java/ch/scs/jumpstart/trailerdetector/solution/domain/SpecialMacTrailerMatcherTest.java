package ch.scs.jumpstart.trailerdetector.solution.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.util.MapBuilder;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpecialMacTrailerMatcherTest {
  private static final String MAC_1 = "84:c5:a6:35:f3:61";
  private static final String MAC_2 = "84:c5:a6:35:f3:62";

  private static final String SPECIAL_MAC = MAC_1;

  private static final Trailer TRAILER_1 = new Trailer();
  private static final Trailer TRAILER_2 = new Trailer();

  private SpecialMacTrailerMatcher matcher;

  @BeforeEach
  public void setup() {
    matcher = new SpecialMacTrailerMatcher(SPECIAL_MAC, new ByOccurrenceTrailerMatcher());
  }

  @Test
  public void use_fallback_if_special_mac_not_present() {
    assertThat(matcher.match(Map.of(MAC_2, TRAILER_2)), is(TRAILER_2));
  }

  @Test
  public void return_trailer_if_special_mac_matches() {
    assertThat(
        matcher.match(
            MapBuilder.<String, Trailer>aLinkedHashMap()
                .with(MAC_2, TRAILER_2)
                .with(SPECIAL_MAC, TRAILER_1)
                .build()),
        is(TRAILER_1));
  }
}
