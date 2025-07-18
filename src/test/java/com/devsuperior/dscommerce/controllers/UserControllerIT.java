package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final String clientId = "myclientid";
    private final String clientSecret = "myclientsecret";

    private final String username = "maria@example.com";
    private final String password = "123456";

    @BeforeEach
    @Transactional
    void setUp() {
        userRepository.deleteAll();

        Role role = Role.builder()
                .authority("ROLE_CLIENT")
                .build();
        entityManager.persist(role);

        User user = User.builder()
                .name("Maria")
                .email(username)
                .password("{noop}" + password)
                .birthDate(Instant.parse("2000-01-01T00:00:00Z"))
                .phone("999999999")
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
    }

    private String getAccessToken() throws URISyntaxException {
        URI uri = new URI("http://localhost:" + port + "/oauth2/token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=password&username=" + username + "&password=" + password;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token");

        return response.getBody().split("\"access_token\":\"")[1].split("\"")[0];
    }

    @Test
    public void getMeShouldReturnLoggedUser() throws Exception {
        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/me", HttpMethod.GET, entity, String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(username, "Maria");
        assertThat(response.getBody()).contains("ROLE_CLIENT");
        assertThat(response.getBody()).doesNotContain("ROLE_ADMIN");
    }
}
