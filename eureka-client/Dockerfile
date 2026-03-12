FROM gradle:8.1.1 AS build
ENV APP_HOME=/app
COPY . $APP_HOME
WORKDIR $APP_HOME

# Даем права на выполнение gradlew
RUN chmod +x ./gradlew

# Собираем с использованием враппера и чистим кэш
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:17-jdk
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/*.jar app.jar
ENV EUREKACLIENT_HOST=localhost
ENV EUREKACLIENT_PORT=8089
ENTRYPOINT ["java", "-jar", "app.jar"]