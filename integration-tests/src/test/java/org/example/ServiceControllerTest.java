package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@Import(ServiceTestConfiguration.class)
public class ServiceControllerTest {
    @Autowired
    ServiceClient client;

    @BeforeEach
    void checkHealth() {
        var resp = client.getHealth();
        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void simpleHelloTest() {
        var resp = client.getHello();

        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNull();
    }

    @Test
    void simpleSizeTest() {
        var resp = client.getSize();

        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void sizeTest() {
        var resp = client.getSize();

        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo(0);
    }

    @Test
    void fullPostTest() {
        var resp = client.postHello("Bonjour");

        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo("Bonjour");

        var sizeResp = client.getSize();

        assertThat(sizeResp).isNotNull();
        assertThat(sizeResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sizeResp.getBody()).isEqualTo(1);

        resp = client.getHello();

        assertThat(resp).isNotNull();
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo("Bonjour");
    }

    @Test
    void badPostTest() {
        assertThrows(HttpClientErrorException.BadRequest.class, () -> client.postHello(null));
    }
}
