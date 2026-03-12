FROM gradle:8.1.1 AS build
ENV APP_HOME=/app
COPY . $APP_HOME
WORKDIR $APP_HOME
RUN gradle build -x test

FROM eclipse-temurin:17-jdk
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/*.jar app.jar
ENV GATEWAY_HOST=localhost
ENV GATEWAT_PORT=8765
ENTRYPOINT ["java", "-jar", "app.jar"]