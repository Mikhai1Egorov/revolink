FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/transaction-service.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]