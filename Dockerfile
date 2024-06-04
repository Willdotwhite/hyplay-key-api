FROM gradle:8-jdk21-alpine

ENV HYPLAY_API_KEY=$HYPLAY_API_KEY

WORKDIR /opt/code/

COPY build.gradle.kts settings.gradle.kts gradle.properties* ./
RUN gradle dependencies

COPY src/ ./src
RUN gradle clean build -x test -x check

EXPOSE 8080

# Build a bootable JAR of the application to be run in isolation
CMD gradle run
