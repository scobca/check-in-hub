FROM eclipse-temurin:21-jre-alpine
ARG VERSION

COPY build/libs/task-tracker-${VERSION}.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]