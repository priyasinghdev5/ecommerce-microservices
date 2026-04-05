FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar inventory-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
RUN mkdir -p /app/logs