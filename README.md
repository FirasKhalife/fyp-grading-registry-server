# registry-server

The Eureka service registry of **Assay**. Every service registers here, and calls between services go through
load-balanced names like `http://admin-service`.

- Self-preservation is off, so a stopped demo service drops out of the registry quickly.
- The registry is on the internal network only.
- Its actuator exposes only health, info and prometheus.

```bash
./mvnw spring-boot:run   # http://localhost:8761
./mvnw test
```

Built on Spring Boot 4.0 and Spring Cloud 2025.1, Java 25, with a layered, non-root image.

## Lint and CI

- **Formatting:** Spotless with palantir-java-format. `./mvnw spotless:apply` fixes it.
- **Lint:** Checkstyle (`checkstyle.xml`) catches likely bugs and sloppy code.
- The build checks both in the `validate` phase, so `./mvnw verify` runs the lint, the build and every test.
- **GitHub Actions** (`.github/workflows/ci.yml`) runs on every pull request and on pushes to `master`:
  - the lint
  - `verify`, including the Testcontainers tests
  - a build of the Docker image
