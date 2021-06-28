package ch.scs.jumpstart.trailerdetector.exercise;

import ch.scs.jumpstart.trailerdetector.exercise.controller.TrailerController;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TrailerControllerIT {
  private static final List<String> UNKNOWN_MAC_ADDRESSES = List.of("84:c5:a6:35:f3:20");

  @LocalServerPort private int port;

  private WebTestClient webTestClient;

  @BeforeEach
  public void setup() {
    webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  public void return_404_for_unknown_mac() {
    webTestClient
        .post()
        .uri(TrailerController.PATH)
        .body(BodyInserters.fromValue(UNKNOWN_MAC_ADDRESSES))
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}
