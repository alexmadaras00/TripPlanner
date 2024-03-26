# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/

# Want to help us make this template better? Share your feedback here: https://forms.gle/ybq9Krt8jtBL3iCk7

################################################################################

# Create a stage for resolving and downloading dependencies.
FROM amazoncorretto:17 as deps

WORKDIR /build

# Copy the gradlew wrapper with executable permissions.
COPY --chmod=0755 gradlew gradlew
COPY gradle/ gradle/
COPY ./settings.gradle settings.gradle

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.m2 so that subsequent builds don't have to
# re-download packages.
RUN --mount=type=bind,source=build.gradle,target=build.gradle \
    ./gradlew dependencies

################################################################################

# Create a stage for building the application based on the stage with downloaded dependencies.
# This Dockerfile is optimized for Java applications that output an uber jar, which includes
# all the dependencies needed to run your app inside a JVM. If your app doesn't output an uber
# jar and instead relies on an application server like Apache Tomcat, you'll need to update this
# stage with the correct filename of your package and update the base image of the "final" stage
# use the relevant app server, e.g., using tomcat (https://hub.docker.com/_/tomcat/) as a base image.
FROM deps as package

WORKDIR /build

COPY ./src src/
CMD ["./gradlew", "bootJar", "--stacktrace"]
CMD ["mv", "./build/libs/*", "./app.jar"]
RUN ["echo", "$(ls)"]
ENTRYPOINT ["java","-jar","./build/libs/TripPlanner-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "./app.jar"]

#RUN --mount=type=bind,source=build.gradle,target=build.gradle \
#    --mount=type=cache,target=/root/.m2 \
#    ./gradlew package -DskipTests && \
#    mv build/$(./gradlew help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./gradlew help:evaluate -Dexpression=project.version -q -DforceStdout).jar build/app.jar
