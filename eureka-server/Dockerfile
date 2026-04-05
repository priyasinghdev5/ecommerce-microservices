FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar eureka-service.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
RUN mkdir -p /app/logs