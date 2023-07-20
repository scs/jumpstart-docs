package ch.scs.jumpstart.movierental.exercise.integrationtest.controller;

import static ch.scs.jumpstart.movierental.solution.integrationtest.controller.SolutionSpringCustomerController.PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
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
public class SpringCustomerControllerIT {
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
  public void setup() {
    webTestClient =
        WebTestClient.bindToServer()
            .responseTimeout(Duration.ofMinutes(1))
            .baseUrl("http://localhost:" + port)
            .build();

    movieRepository.deleteAll();
    movieRepository.save(MOVIE_1);
  }

  @Test
  public void return_found_customers() {
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

    assertThat(customerList, notNullValue());
    assertThat(customers, is(customerList));
  }

  @Test
  public void add_customer_and_return_added_customer() {
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

    assertThat(insertedCustomer, is(customer));

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

    assertThat(customers, notNullValue());
    assertThat(customers, is(List.of(customer)));
  }

  @Test
  public void add_rental_and_return_added_rental() {
    // TODO
  }

  @Test
  public void return_empty_text_invoice_of_customer() {
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

    assertThat(invoice, is(notNullValue()));
  }

  @Test
  public void return_text_invoice_of_customer() {
    // TODO
  }

  @Test
  public void return_json_invoice_of_customer() {
    // TODO
  }
}
