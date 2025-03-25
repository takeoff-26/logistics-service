FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# gradle 빌드
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
COPY auth/build.gradle auth/
COPY auth/src auth/src
COPY common/build.gradle common/
COPY common/src common/src

RUN chmod +x gradlew
RUN ./gradlew :auth:bootJar -x test

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /workspace/app/auth/build/libs/*.jar app.jar

RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
