FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# gradle 빌드
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
COPY product/build.gradle product/
COPY product/src product/src
COPY common/build.gradle common/
COPY common/src common/src

RUN chmod +x gradlew
RUN ./gradlew :product:bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/app/product/build/libs/*.jar app.jar

RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
