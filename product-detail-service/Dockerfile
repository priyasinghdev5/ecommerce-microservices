FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar product-detail-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "product-detail-service.jar"]
RUN mkdir -p /app/logs