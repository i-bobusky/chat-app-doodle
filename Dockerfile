FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/chat-app-doodle-*.jar /usr/app/chat-app-doodle.jar
WORKDIR /usr/app
CMD ["java", "-jar", "chat-app-doodle.jar"]