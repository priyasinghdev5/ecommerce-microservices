FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar payment-service.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "payment-service.jar"]
RUN mkdir -p /app/logs