package ch.scs.jumpstart.movierental.exercise.common;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OpenApiDocIT {

  @SuppressWarnings("unused")
  @LocalServerPort
  private int port;

  private WebTestClient webTestClient;

  @BeforeEach
  void setup() {
    webTestClient =
        WebTestClient.bindToServer()
            .responseTimeout(Duration.ofMinutes(1))
            .baseUrl("http://localhost:%d".formatted(port))
            .build();
  }

  @Test
  void swagger_ui_returns_200() {
    webTestClient
        .get()
        .uri("/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config")
        .exchange()
        .expectStatus()
        .isOk();
  }
}
