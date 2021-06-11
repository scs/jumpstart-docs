package ch.scs.jumpstart.trailerdetector.solution.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.util.MapBuilder;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByOccurrenceTrailerMatcherTest {
  private static final String MAC_1 = "84:c5:a6:35:f3:61";
  private static final String MAC_2 = "84:c5:a6:35:f3:62";
  private static final String MAC_3 = "84:c5:a6:35:f3:63";

  private static final Trailer TRAILER_1 = new Trailer();
  private static final Trailer TRAILER_2 = new Trailer();
  private static final Trailer TRAILER_3 = new Trailer();
  private TrailerMatcher matcher;

  @BeforeEach
  public void setup() {
    matcher = new ByOccurrenceTrailerMatcher();
  }

  @Test
  public void return_trailer_if_only_one_trailer() {
    assertThat(matcher.match(Map.of(MAC_1, TRAILER_1)), is(TRAILER_1));
  }

  @Test
  public void return_most_occurring_trailer() {
    assertThat(
        matcher.match(Map.of(MAC_1, TRAILER_1, MAC_2, TRAILER_2, MAC_3, TRAILER_2)), is(TRAILER_2));
  }

  @Test
  public void return_first_trailer_if_all_different() {
    assertThat(
        matcher.match(
            MapBuilder.<String, Trailer>aLinkedHashMap()
                .with(MAC_1, TRAILER_1)
                .with(MAC_2, TRAILER_2)
                .with(MAC_3, TRAILER_3)
                .build()),
        is(TRAILER_1));
  }
}
