#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /payment

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

#
# Package stage
#
FROM amazoncorretto:21-alpine-jdk

WORKDIR /payment

COPY --from=build /payment/target/*.jar ./payment-service.jar

EXPOSE 7075

ENTRYPOINT ["java","-jar","payment-service.jar"]