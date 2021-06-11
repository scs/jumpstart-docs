package ch.scs.jumpstart.trailerdetector.solution.controller;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import ch.scs.jumpstart.trailerdetector.solution.domain.ByOccurrenceTrailerMatcher;
import ch.scs.jumpstart.trailerdetector.solution.domain.SolutionTrailerService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionTrailerControllerTest {

  private static final List<String> UNKNOWN_MAC_ADDRESSES = List.of("84:c5:a6:35:f3:20");
  private static final String MAC_1 = "84:c5:a6:35:f3:61";
  private static final String MAC_2 = "84:c5:a6:35:f3:62";
  private static final String MAC_3 = "84:c5:a6:35:f3:63";

  private static final int TRAILER_NUMBER_1 = 1;
  private static final int TRAILER_NUMBER_2 = 2;

  private static final Trailer TRAILER_1 = TrailerBuilder.create(TRAILER_NUMBER_1).build();
  private static final Trailer TRAILER_2 =
      TrailerBuilder.create(TRAILER_NUMBER_2).withMacAddress(MAC_1).build();

  private SolutionTrailerController solutionTrailerController;
  private TrailerRepository trailerRepository;

  @BeforeEach
  public void setup() {
    trailerRepository = mock(TrailerRepository.class);
    var solutionTrailerService =
        new SolutionTrailerService(trailerRepository, new ByOccurrenceTrailerMatcher());
    solutionTrailerController = new SolutionTrailerController(solutionTrailerService);
  }

  @Test
  public void return_400_and_no_body_if_macAddresses_empty() {
    assertThat(
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(Collections.emptyList()),
        is(Pair.of(400, empty())));
  }

  @Test
  public void return_404_and_no_body_if_macAddress_not_found() {
    assertThat(
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(UNKNOWN_MAC_ADDRESSES),
        is(Pair.of(404, empty())));
  }

  @Test
  public void return_404_and_no_body_if_macAddress_belongs_to_unknown_trailer() {
    when(trailerRepository.getByNetworkDeviceMac(notNull())).thenReturn(of(new Trailer()));

    assertThat(
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1)),
        is(Pair.of(404, empty())));
  }

  @Test
  public void return_matched_trailer_number_if_found() {
    when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(Optional.of(TRAILER_2));
    when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(Optional.of(TRAILER_1));
    when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

    assertThat(
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3)),
        is(Pair.of(200, Optional.of(TRAILER_NUMBER_1))));
  }
}
