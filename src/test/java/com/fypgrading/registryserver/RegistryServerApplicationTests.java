package com.fypgrading.registryserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistryServerApplicationTests {

    @LocalServerPort
    int port;

    @Test
    void servesTheRegistryAndKeepsActuatorClosed() {
        RestClient http = RestClient.create("http://localhost:" + port);
        Integer apps = http.get().uri("/eureka/apps").header("Accept", "application/json")
                .exchange((req, res) -> res.getStatusCode().value());
        assertThat(apps).isEqualTo(200);
        assertThat(status(http, "/actuator/health")).isEqualTo(200);
        assertThat(status(http, "/actuator/env")).isEqualTo(404);
        assertThat(status(http, "/actuator/shutdown")).isEqualTo(404);
    }

    private static int status(RestClient http, String path) {
        Integer code = http.get().uri(path).exchange((req, res) -> res.getStatusCode().value());
        return code == null ? -1 : code;
    }
}
