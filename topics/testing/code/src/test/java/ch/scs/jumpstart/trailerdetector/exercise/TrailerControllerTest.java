package ch.scs.jumpstart.trailerdetector.exercise;

import static ch.scs.jumpstart.trailerdetector.exercise.util.MapBuilder.aLinkedHashMap;
import static java.util.Optional.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

import ch.scs.jumpstart.trailerdetector.exercise.controller.TrailerController;
import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import ch.scs.jumpstart.trailerdetector.solution.controller.TrailerBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TrailerControllerTest {

  private static final List<String> UNKNOWN_MAC_ADDRESSES = List.of("84:c5:a6:35:f3:20");
  private static final String MAC_1 = "84:c5:a6:35:f3:61";
  private static final String MAC_2 = "84:c5:a6:35:f3:62";
  private static final String MAC_3 = "84:c5:a6:35:f3:63";

  private static final int TRAILER_NUMBER_1 = 1;
  private static final int TRAILER_NUMBER_2 = 2;

  private static final Trailer TRAILER_1 = TrailerBuilder.create(TRAILER_NUMBER_1).build();
  private static final Trailer TRAILER_2 =
      TrailerBuilder.create(TRAILER_NUMBER_2).withMacAddress(MAC_1).build();
  private static final Trailer TRAILER_3 = TrailerBuilder.create(3).build();

  private static final Trailer TRAILER_1_WITH_ALL_MACS =
      TrailerBuilder.create(TRAILER_NUMBER_1)
          .withMacAddress(MAC_1)
          .withMacAddress(MAC_2)
          .withMacAddress(MAC_3)
          .build();
  private static final Trailer TRAILER_2_WITHOUT_MACS =
      TrailerBuilder.create(TRAILER_NUMBER_2).build();

  private TrailerController solutionTrailerController;
  private TrailerRepository trailerRepository;

  @BeforeEach
  public void setup() {
    trailerRepository = mock(TrailerRepository.class);
    solutionTrailerController = new TrailerController(trailerRepository);
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

  @ParameterizedTest
  @MethodSource("argumentsForGetTrailerNumber")
  public void return_correct_trailer_number(
      Map<String, Optional<Trailer>> macAddressTrailerMap, Integer result) {
    macAddressTrailerMap.forEach(
        (s, trailer) -> when(trailerRepository.getByNetworkDeviceMac(s)).thenReturn(trailer));

    assertThat(
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(
            List.copyOf(macAddressTrailerMap.keySet())),
        is(Pair.of(200, ofNullable(result))));
  }

  @SuppressWarnings("unused")
  public static Stream<Arguments> argumentsForGetTrailerNumber() {
    return Stream.of(
        Arguments.of(Map.of(MAC_2, of(TRAILER_1)), TRAILER_NUMBER_1),
        Arguments.of(
            Map.of(MAC_1, empty(), MAC_2, of(TRAILER_1), MAC_3, of(TRAILER_1)), TRAILER_NUMBER_1),
        Arguments.of(
            Map.of(MAC_1, of(TRAILER_2), MAC_2, of(TRAILER_1), MAC_3, of(TRAILER_1)),
            TRAILER_NUMBER_1),
        Arguments.of(
            aLinkedHashMap()
                .with(MAC_1, of(TRAILER_1))
                .with(MAC_2, of(TRAILER_2))
                .with(MAC_3, of(TRAILER_3))
                .build(),
            TRAILER_NUMBER_1));
  }

  @Test
  public void add_new_mac_addresses_to_trailer() {
    when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(empty());
    when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(empty());
    when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

    var updateResult =
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
    assertThat(updateResult, is(Pair.of(200, of(TRAILER_NUMBER_1))));

    verify(trailerRepository).saveAll(Set.of(TRAILER_1_WITH_ALL_MACS));

    when(trailerRepository.getByNetworkDeviceMac(
            argThat(arg -> List.of(MAC_1, MAC_2).contains(arg))))
        .thenReturn(of(TRAILER_1));

    updateResult =
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2));
    assertThat(updateResult, is(Pair.of(200, of(TRAILER_NUMBER_1))));
  }

  @Test
  public void move_mac_addresses_from_previous_trailer_to_current() {
    when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(Optional.of(TRAILER_2));
    when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(Optional.of(TRAILER_1));
    when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

    var updateResult =
        solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
    assertThat(updateResult, is(Pair.of(200, of(TRAILER_NUMBER_1))));

    verify(trailerRepository).saveAll(Set.of(TRAILER_1_WITH_ALL_MACS, TRAILER_2_WITHOUT_MACS));

    when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(of(TRAILER_1));

    updateResult = solutionTrailerController.getAndUpdateTrailersByMacAddresses(List.of(MAC_1));
    assertThat(updateResult, is(Pair.of(200, of(TRAILER_NUMBER_1))));
  }
}
