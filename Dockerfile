FROM openjdk:17
WORKDIR /app
COPY "./target/parcial-0.0.1.jar" "app.jar"
EXPOSE 8022
ENTRYPOINT ["java", "-jar", "app.jar"]