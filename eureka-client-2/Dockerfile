FROM gradle:8.14-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app

# Копируем JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Копируем ресурсы (на всякий случай)
COPY --from=build /app/src/main/resources /app/resources

EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]