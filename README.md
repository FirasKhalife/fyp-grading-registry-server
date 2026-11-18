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
  - the Docker image: built, then scanned with Trivy (the report goes to GitHub code scanning; a HIGH or CRITICAL
    finding with a fix fails the build). From `master` it is pushed to `ghcr.io/firaskhalife/fyp-grading-registry-server`
    (tags `latest` and the short commit SHA) with an SBOM and build provenance, and signed with cosign
- **Dependabot** proposes weekly updates for Maven, the Dockerfile's base images and the GitHub Actions, all pinned
  (base images by digest, actions by commit SHA).

The `Dockerfile` (the same in every service repo) builds a small Java 25 runtime with `jlink` on Alpine, runs as
an unprivileged user, and ships an AOT cache made by a training run at build time, so the service starts in
about half the time. Accepted unfixable scan findings, if any, are listed in `.trivyignore` with a reason.
