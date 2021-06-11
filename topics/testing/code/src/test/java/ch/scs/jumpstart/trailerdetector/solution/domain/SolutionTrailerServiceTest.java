package ch.scs.jumpstart.trailerdetector.solution.domain;

import static ch.scs.jumpstart.trailerdetector.exercise.util.MapBuilder.aLinkedHashMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

import ch.scs.jumpstart.trailerdetector.exercise.domain.Trailer;
import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import ch.scs.jumpstart.trailerdetector.solution.controller.TrailerBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

class SolutionTrailerServiceTest {
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

  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  @Nested
  @DisplayName("with ByOccurrenceTrailerMatcher")
  class WithByOccurrenceTrailerMatcher {

    private TrailerRepository trailerRepository;
    private SolutionTrailerService solutionTrailerService;

    @BeforeEach
    public void setup() {
      trailerRepository = mock(TrailerRepository.class);
      solutionTrailerService =
          new SolutionTrailerService(trailerRepository, new ByOccurrenceTrailerMatcher());
    }

    @Test
    public void return_empty_if_macAddress_not_found() {
      assertThat(
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(UNKNOWN_MAC_ADDRESSES),
          is(empty()));
    }

    @Test
    public void return_empty_if_macAddress_belongs_to_unknown_trailer() {
      when(trailerRepository.getByNetworkDeviceMac(notNull())).thenReturn(of(new Trailer()));

      assertThat(
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1)),
          is(of(TrailerBuilder.create().withMacAddress(MAC_1).build())));
    }

    @Test
    public void throw_exception_if_finding_trailers_throws_exception() {
      when(trailerRepository.getByNetworkDeviceMac(notNull())).thenThrow(RuntimeException.class);

      assertThrows(
          RuntimeException.class,
          () -> solutionTrailerService.findAndUpdateTrailersByMacAddresses(UNKNOWN_MAC_ADDRESSES));
    }

    @Test
    public void return_trailer_number_if_saving_trailers_failed() {
      when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(empty());
      when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(Optional.of(TRAILER_1));
      when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));
      doThrow(RuntimeException.class).when(trailerRepository).saveAll(ArgumentMatchers.notNull());

      var updateResult =
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
      assertThat(updateResult, is(of(TRAILER_1)));
    }

    @ParameterizedTest
    @MethodSource("argumentsForGetTrailerNumber")
    public void return_correct_trailer_number(
        Map<String, Optional<Trailer>> macAddressTrailerMap, Trailer result) {
      macAddressTrailerMap.forEach(
          (s, trailer) -> when(trailerRepository.getByNetworkDeviceMac(s)).thenReturn(trailer));

      assertThat(
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(
              List.copyOf(macAddressTrailerMap.keySet())),
          is(ofNullable(result)));
    }

    @SuppressWarnings("unused")
    public Stream<Arguments> argumentsForGetTrailerNumber() {
      return Stream.of(
          Arguments.of(Map.of(MAC_2, of(TRAILER_1)), TRAILER_1),
          Arguments.of(
              Map.of(MAC_1, empty(), MAC_2, of(TRAILER_1), MAC_3, of(TRAILER_1)), TRAILER_1),
          Arguments.of(
              Map.of(MAC_1, of(TRAILER_2), MAC_2, of(TRAILER_1), MAC_3, of(TRAILER_1)), TRAILER_1),
          Arguments.of(
              aLinkedHashMap()
                  .with(MAC_1, of(TRAILER_1))
                  .with(MAC_2, of(TRAILER_2))
                  .with(MAC_3, of(TRAILER_3))
                  .build(),
              TRAILER_1));
    }

    @Test
    public void add_new_mac_addresses_to_trailer() {
      when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(empty());
      when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(empty());
      when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

      var updateResult =
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
      assertThat(updateResult, is(of(TRAILER_1)));

      verify(trailerRepository).saveAll(Set.of(TRAILER_1_WITH_ALL_MACS));

      when(trailerRepository.getByNetworkDeviceMac(
              argThat(arg -> List.of(MAC_1, MAC_2).contains(arg))))
          .thenReturn(of(TRAILER_1));

      updateResult =
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2));
      assertThat(updateResult, is(of(TRAILER_1_WITH_ALL_MACS)));
    }

    @Test
    public void move_mac_addresses_from_previous_trailer_to_current() {
      when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(Optional.of(TRAILER_2));
      when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(Optional.of(TRAILER_1));
      when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

      var updateResult =
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
      assertThat(updateResult, is(of(TRAILER_1)));

      verify(trailerRepository).saveAll(Set.of(TRAILER_1_WITH_ALL_MACS, TRAILER_2_WITHOUT_MACS));

      when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(of(TRAILER_1_WITH_ALL_MACS));

      updateResult = solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1));
      assertThat(updateResult, is(of(TRAILER_1_WITH_ALL_MACS)));
    }
  }

  @Nested
  @DisplayName("with ByOccurrenceTrailerMatcher")
  class WithSpecialMacTrailerMatcher {
    private static final String SPECIAL_MAC_1 = MAC_1;

    private TrailerRepository trailerRepository;
    private SolutionTrailerService solutionTrailerService;

    @BeforeEach
    public void setup() {
      trailerRepository = mock(TrailerRepository.class);
      var fallbackMatcher = new ByOccurrenceTrailerMatcher();
      var specialMacTrailerMatcher = new SpecialMacTrailerMatcher(SPECIAL_MAC_1, fallbackMatcher);
      solutionTrailerService =
          new SolutionTrailerService(trailerRepository, specialMacTrailerMatcher);
    }

    @Test
    public void matches_special_mac() {
      when(trailerRepository.getByNetworkDeviceMac(MAC_1)).thenReturn(Optional.of(TRAILER_2));
      when(trailerRepository.getByNetworkDeviceMac(MAC_2)).thenReturn(Optional.of(TRAILER_1));
      when(trailerRepository.getByNetworkDeviceMac(MAC_3)).thenReturn(Optional.of(TRAILER_1));

      var updateResult =
          solutionTrailerService.findAndUpdateTrailersByMacAddresses(List.of(MAC_1, MAC_2, MAC_3));
      assertThat(updateResult, is(of(TRAILER_2)));
    }
  }
}
