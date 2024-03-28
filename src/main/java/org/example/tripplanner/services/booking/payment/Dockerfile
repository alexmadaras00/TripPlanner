FROM eclipse-temurin as deps
# STAGE 1 - Download dependencies
WORKDIR /app
COPY ../../../../gradle gradle
COPY ../../../../gradlew .
COPY ../../../../build.gradle .
COPY ../../../../settings.gradle .
COPY ../../../../src src
RUN ./gradlew dependencies

FROM deps as build
COPY . .
RUN ./gradlew build

FROM build as deploy
RUN ./gradlew bootJar
RUN ls build/libs/
RUN mv $(ls build/libs/*.jar | head -n 1) app.jar

ENTRYPOINT ["java","-jar","./build/libs/payment-service.jar"]