FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Set JAVA_OPTS for memory management
ENV JAVA_OPTS="-Xmx256m -Xms256m"

EXPOSE 3081

ENTRYPOINT sh -c "java ${JAVA_OPTS} -jar /app/app.jar"