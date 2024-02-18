package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootConfiguration
@TestConfiguration(proxyBeanMethods = false)
class ServiceTestConfiguration {

    @Bean
    @ConditionalOnProperty(name = "test.local", havingValue = "true")
    GenericContainer<?> localContainer() {
        return new GenericContainer<>("test-example/service:latest")
                .withExposedPorts(8080)
                .waitingFor(Wait.forHttp("/actuator/health"));
    }

    @Bean
    @ConditionalOnBean(GenericContainer.class)
    ServiceClient localClient(GenericContainer<?> local) {
        return new ServiceClient("http://localhost:%s".formatted(local.getMappedPort(8080)));
    }

    @Bean
    @ConditionalOnMissingBean(ServiceClient.class)
    ServiceClient remoteClient(@Value("${url.to.test}") String url) {
        return new ServiceClient(url);
    }
}