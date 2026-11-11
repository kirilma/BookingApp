# build
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /booking/build

COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package

# run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /booking/app

RUN addgroup -S spring && adduser -S booking -G spring
USER booking:spring
COPY --from=build /booking/build/target/booking.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]