FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY gradle gradle
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY src src
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Set JAVA_OPTS for memory management
ENV JAVA_OPTS="-Xmx256m -Xms256m"

EXPOSE 3081

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]