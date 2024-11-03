FROM openjdk:17-jre-slim
COPY build/libs/blog-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]