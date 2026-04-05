FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar auth-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
RUN mkdir -p /app/logs