package ch.scs.jumpstart.movierental.solution.integrationtest.controller;

import static ch.scs.jumpstart.movierental.solution.integrationtest.controller.SolutionSpringCustomerController.PATH;
import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;
import static org.assertj.core.api.Assertions.assertThat;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
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
class SolutionSpringCustomerControllerIT {
  private static final ParameterizedTypeReference<List<Customer>> CUSTOMER_LIST =
      new ParameterizedTypeReference<>() {};

  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);
  private static final String CUSTOMER_NAME_1 = "customer1";
  private static final String CUSTOMER_NAME_2 = "customer2";

  @SuppressWarnings("unused")
  @Autowired
  private MovieRepository movieRepository;

  @SuppressWarnings("unused")
  @Autowired
  private CustomerRepository customerRepository;

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

    customerRepository.deleteAll();

    movieRepository.deleteAll();
    movieRepository.save(MOVIE_1);
  }

  @Test
  void return_found_customers() {
    var customerList =
        List.of(
            CustomerBuilder.builder(CUSTOMER_NAME_1).build(),
            CustomerBuilder.builder(CUSTOMER_NAME_2).build());
    customerRepository.saveAll(customerList);

    var customers =
        webTestClient
            .get()
            .uri(PATH)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CUSTOMER_LIST)
            .returnResult()
            .getResponseBody();

    assertThat(customers).isNotNull();
    assertThat(customers).isEqualTo(customerList);
  }

  @Test
  void add_customer_and_return_added_customer() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).build();

    var insertedCustomer =
        webTestClient
            .post()
            .uri(PATH)
            .body(BodyInserters.fromValue(customer))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Customer.class)
            .returnResult()
            .getResponseBody();

    assertThat(insertedCustomer).isEqualTo(customer);

    var customers =
        webTestClient
            .get()
            .uri(PATH)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CUSTOMER_LIST)
            .returnResult()
            .getResponseBody();

    assertThat(customers).isNotNull();
    assertThat(customers).isEqualTo(List.of(customer));
  }

  @Test
  void add_rental_and_return_added_rental() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).build();
    customerRepository.save(customer);

    var customerWithRental =
        webTestClient
            .post()
            .uri(
                uriBuilder ->
                    uriBuilder.path(PATH).pathSegment(CUSTOMER_NAME_1, "addRental").build())
            .body(BodyInserters.fromValue(new AddRental(MOVIE_1.getTitle(), 1)))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Customer.class)
            .returnResult()
            .getResponseBody();

    assertThat(customerWithRental).isNotNull();
    assertThat(customerWithRental.getRentals()).hasSize(1);
  }

  @Test
  void return_empty_text_invoice_of_customer() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).build();
    customerRepository.save(customer);

    var invoice =
        webTestClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.path(PATH).pathSegment(CUSTOMER_NAME_1, "getInvoice").build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

    assertThat(invoice).isNotNull();
  }

  @Test
  void return_text_invoice_of_customer() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 1).build();
    customerRepository.save(customer);

    var invoice =
        webTestClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.path(PATH).pathSegment(CUSTOMER_NAME_1, "getInvoice").build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

    assertThat(invoice).isNotNull();
  }

  @Test
  void return_json_invoice_of_customer() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 1).build();
    customerRepository.save(customer);

    var invoice =
        webTestClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.path(PATH).pathSegment(CUSTOMER_NAME_1, "getJsonInvoice").build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(RentalStatement.class)
            .returnResult()
            .getResponseBody();

    assertThat(invoice).isNotNull();
  }
}
