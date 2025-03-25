FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# gradle 빌드
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
COPY config/build.gradle config/
COPY config/src config/src

RUN chmod +x gradlew
RUN ./gradlew :config:bootJar

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /workspace/app/config/build/libs/*.jar app.jar

RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


