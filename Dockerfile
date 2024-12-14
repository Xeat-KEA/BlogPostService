FROM openjdk:17-slim
COPY build/libs/blog-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
