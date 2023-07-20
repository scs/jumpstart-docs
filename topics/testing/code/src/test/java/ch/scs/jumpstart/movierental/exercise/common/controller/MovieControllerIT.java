package ch.scs.jumpstart.movierental.exercise.common.controller;

import static ch.scs.jumpstart.movierental.exercise.common.controller.MovieController.PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MovieControllerIT {
  private static final ParameterizedTypeReference<List<Movie>> MOVIE_LIST =
      new ParameterizedTypeReference<>() {};
  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);

  @SuppressWarnings("unused")
  @LocalServerPort
  private int port;

  @SuppressWarnings("unused")
  @Autowired
  private MovieRepository movieRepository;

  private WebTestClient webTestClient;

  @BeforeEach
  public void setup() {
    webTestClient =
        WebTestClient.bindToServer()
            .responseTimeout(Duration.ofMinutes(1))
            .baseUrl("http://localhost:" + port)
            .build();
  }

  @Test
  public void return_added_movies() {
    var insertedMovie =
        webTestClient
            .post()
            .uri(PATH)
            .body(BodyInserters.fromValue(MOVIE_1))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Movie.class)
            .returnResult()
            .getResponseBody();

    assertThat(insertedMovie, is(MOVIE_1));

    var customers =
        webTestClient
            .get()
            .uri(PATH)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(MOVIE_LIST)
            .returnResult()
            .getResponseBody();

    assertThat(customers, notNullValue());
    assertThat(customers, is(List.of(MOVIE_1)));
  }
}
