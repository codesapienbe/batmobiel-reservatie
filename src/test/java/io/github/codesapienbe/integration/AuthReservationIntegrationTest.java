package io.github.codesapienbe.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("resource")
class AuthReservationIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry reg) {
        reg.add("spring.datasource.url", postgres::getJdbcUrl);
        reg.add("spring.datasource.username", postgres::getUsername);
        reg.add("spring.datasource.password", postgres::getPassword);
        // allow Hibernate to create/update schema in test container
        reg.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        reg.add("jwt.secret", () -> "dev-secret-key-dev-secret-key-dev");
    }

    @Autowired
    private TestRestTemplate rest;

    @Test
    void fullFlow_registerLoginCreateListDelete() {
        // register
        var regReq = Map.<String, String>of("username","int-user","password","password123");
        var regResp = rest.postForEntity("/auth/register", regReq, Map.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        var token = Objects.requireNonNull((String)((Map<?,?>)regResp.getBody()).get("token"));
        assertThat(token).isNotBlank();

        // create reservation
        var createReq = Map.<String, String>of("start", ZonedDateTime.now().toString(), "end", ZonedDateTime.now().plusHours(1).toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(createReq, headers);
        var createResp = rest.postForEntity("/reservations", entity, Map.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var id = Objects.requireNonNull((String)((Map<?,?>)createResp.getBody()).get("id"));

        // list active
        var listResp = rest.exchange("/reservations/active", HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        assertThat(listResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        // delete
        var delResp = rest.exchange("/reservations/" + id, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}


