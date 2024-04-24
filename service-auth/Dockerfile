FROM gradle:8.6.0-jdk21 as build

WORKDIR /app
COPY . .
RUN gradle bootJar

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]


