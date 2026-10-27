# sit-stats-api
## Build and deploy

`docker build .` runs the tests (`gradle test`) and produces the image; CI (`.github/workflows/ci.yml`, the shared `DevinMB/workflows` pipeline) adds a healthcheck smoke test and a Trivy scan, and pushes `ghcr.io/devinmb/sit-stats-api:sha-<commit>` from `main`. Health: `GET /actuator/health`. Kafka: `SPRING_KAFKA_BOOTSTRAP_SERVERS` (default `192.168.1.250:9092`). Dependabot keeps Gradle, the base images and the actions current; patch and minor updates merge on green.
