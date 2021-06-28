package ch.scs.jumpstart.trailerdetector.exercise;

import ch.scs.jumpstart.trailerdetector.exercise.domain.TrailerRepository;
import ch.scs.jumpstart.trailerdetector.solution.controller.TrailerBuilder;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class FillDataBase {
  private static final String MAC_1 = "84:c5:a6:35:f3:61";
  private static final String MAC_2 = "84:c5:a6:35:f3:62";
  private static final String MAC_3 = "84:c5:a6:35:f3:63";

  @Autowired private TrailerRepository trailerRepository;

  @Test
  public void fill_database() {
    var trailer1 = TrailerBuilder.create(1).withMacAddress(MAC_1).withMacAddress(MAC_2).build();
    var trailer2 = TrailerBuilder.create(2).withMacAddress(MAC_3).build();
    trailerRepository.saveAll(List.of(trailer1, trailer2));
  }
}
