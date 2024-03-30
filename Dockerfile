
FROM eclipse-temurin as deps
# STAGE 1 - Download dependencies
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies

# STAGE 2 - Build
FROM deps as build
COPY src src
RUN ./gradlew build

#STAGE 3 - Deploy
FROM build as deploy
RUN ./gradlew bootJar
RUN ls build/libs/
RUN mv $(ls build/libs/*.jar | head -n 1) app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#STAGE 4 - Add a volume
FROM deploy as addVolume
VOLUME /shared-volume
RUN mkdir /shared-data
RUN cp gradlew /shared-data
RUN cp app.jar /shared-data



