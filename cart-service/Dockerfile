FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar cart-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "cart-service.jar"]
RUN mkdir -p /app/logs