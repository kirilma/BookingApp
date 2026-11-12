# build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /booking/build

COPY mvnw ./mvnw
COPY .mvn ./.mvn
COPY pom.xml ./pom.xml
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

# run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /booking/app

RUN addgroup -S spring && adduser -S booking -G spring
COPY --from=build --chown=booking:spring /booking/build/target/booking.jar ./app.jar
RUN mkdir -p /booking/data && chown -R booking:spring /booking/data
USER booking:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]