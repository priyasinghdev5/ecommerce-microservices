FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar notification-service.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
RUN mkdir -p /app/logs