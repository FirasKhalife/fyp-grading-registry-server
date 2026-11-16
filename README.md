# registry-server

The Eureka service registry of **Assay**. Every service registers here, and calls between services go through
load-balanced names like `http://admin-service`.

- Self-preservation is off, so a stopped demo service drops out of the registry quickly.
- The registry is on the internal network only.
- Its actuator exposes only health, info and prometheus.

```bash
mvn spring-boot:run   # http://localhost:8761
mvn test
```

Built on Spring Boot 4.0 and Spring Cloud 2025.1, Java 25, with a layered, non-root image.
