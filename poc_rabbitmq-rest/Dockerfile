## ====== Build Image ====== ##
FROM maven:3.8.5-openjdk-17 AS build_image

WORKDIR /app

COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src
RUN mvn package -DskipTests

## ====== Deploy Image ====== ##
FROM openjdk:17.0 as release_image

# Workdir creation
WORKDIR /app

COPY --from=build_image /app/target/poc_rabbitmq_rest.jar .

# Container entrypoint
CMD [ "java", "-jar", "./poc_rabbitmq_rest.jar" ]
