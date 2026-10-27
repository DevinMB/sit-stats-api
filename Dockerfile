# sit-stats-api: Spring Boot on Java 21. The build stage runs the tests, so an
# image exists only if they pass.
FROM eclipse-temurin:21.0.12_8-jdk-noble AS build
WORKDIR /src
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
# Dependencies in their own layer: a code change does not download them again.
RUN ./gradlew --no-daemon -q dependencies > /dev/null
COPY src src
RUN ./gradlew --no-daemon test bootJar

FROM eclipse-temurin:21.0.12_8-jre-alpine
ARG COMMIT=dev
LABEL org.opencontainers.image.source="https://github.com/DevinMB/sit-stats-api" \
      org.opencontainers.image.revision="${COMMIT}"
RUN apk upgrade --no-cache && addgroup -S app && adduser -S -G app app
COPY --from=build /src/build/libs/sit.stats-*-SNAPSHOT.jar /app/app.jar
USER app
EXPOSE 5001
# Kafka comes from SPRING_KAFKA_BOOTSTRAP_SERVERS (default in application.properties).
HEALTHCHECK --interval=30s --timeout=5s --start-period=90s --retries=3 \
  CMD wget -qO /dev/null http://127.0.0.1:5001/actuator/health || exit 1
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
