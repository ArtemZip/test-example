package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class ServiceClient {
    private final RestClient client;

    public ServiceClient(String host) {
        this.client = RestClient.create(host);
    }

    public ResponseEntity<String> getHealth() {
        return client.get()
                .uri("/actuator/health")
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getHello() {
        return client.get()
                .uri("/hello")
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<Integer> getSize() {
        return client.get()
                .uri("/hello/size")
                .retrieve()
                .toEntity(Integer.class);
    }

    public ResponseEntity<String> postHello(String hello) {
        return client.post()
                .uri("/hello" + (hello == null ? "": "?hello=%s".formatted(hello)))
                .retrieve()
                .toEntity(String.class);
    }
}
