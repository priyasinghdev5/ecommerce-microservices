FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
RUN mkdir -p /app/logs