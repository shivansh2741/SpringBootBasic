FROM openjdk:21-jdk-slim
WORKDIR /app
ENV PORT=8080
EXPOSE 8080
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
